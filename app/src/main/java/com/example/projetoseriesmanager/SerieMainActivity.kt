package com.example.projetoseriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoseriesmanager.adapter.SerieRvAdapter
import com.example.projetoseriesmanager.controller.SerieController
import com.example.projetoseriesmanager.databinding.SerieMainActivityBinding
import com.example.projetoseriesmanager.model.Serie
import com.google.android.material.snackbar.Snackbar

class SerieMainActivity : AppCompatActivity(), OnSerieClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editSerieActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewSerieActivityResultLauncher: ActivityResultLauncher<Intent>

    private val serieLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val serieControler: SerieController by lazy { SerieController(this) }
    private val seriesList: MutableList<Serie> by lazy { serieControler.getAll() }
    private val seriesAdapter: SerieRvAdapter by lazy { SerieRvAdapter(this, seriesList) }


    private val activityMainBinding: SerieMainActivityBinding by lazy {
        SerieMainActivityBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // associar
        activityMainBinding.seriesRv.adapter = seriesAdapter
        activityMainBinding.seriesRv.layoutManager = serieLayoutManager

        // Add serie
        serieActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                        serieControler.insert(this)
                        seriesList.add(this)
                        seriesAdapter.notifyDataSetChanged()
                    }
                }
            }

        // edit serie
        editSerieActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    val position = res.data?.getIntExtra(EXTRA_POSITION, -1)

                    res.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                        if (position != null && position != -1) {
                            serieControler.update(this)
                            seriesList[position] = this
                            seriesAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        // view serie
        viewSerieActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    }
                }
            }

        activityMainBinding.addSerieFab.setOnClickListener {
            serieActivityResultLauncher.launch(Intent(this, SerieActivity::class.java))
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = seriesAdapter.contextMenuPosition
        return when (item.itemId) {
            R.id.editSerieMi -> {
                val serie = seriesList[position]
                val editSerieIntent = Intent(this, SerieActivity::class.java)
                editSerieIntent.putExtra(EXTRA_SERIE, serie)
                editSerieIntent.putExtra(EXTRA_POSITION, position)
                editSerieActivityResultLauncher.launch(editSerieIntent)
                true
            }
            R.id.removeSerieMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim") { _, _ ->
                        serieControler.remove(seriesList[position].nome)
                        Snackbar.make(
                            activityMainBinding.root,
                            "Livro removido",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        seriesList.removeAt(position)
                        seriesAdapter.notifyDataSetChanged()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(
                            activityMainBinding.root,
                            "Remoção cancelada",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    create()
                }.show()
                true
            }
            R.id.viewSerieMi -> {
                val serie = seriesList[position]
                val viewSerieIntent = Intent(this, SerieActivity::class.java)
                viewSerieIntent.putExtra(EXTRA_SERIE, serie)
                viewSerieActivityResultLauncher.launch(viewSerieIntent)
                true
            }
            else -> false
        }
    }

    override fun onSerieClick(position: Int) {
        val serie = seriesList[position]
        val viewSerieIntent = Intent(this, TemporadaMainActivity::class.java)
        viewSerieIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(viewSerieIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.refreshMi -> {
            seriesAdapter.notifyDataSetChanged()
            true
        }
        R.id.sairMi -> {
            AuthFirebase.firebaseAuth.signOut()
            finish()
            true
        }
        else -> {
            false
        }
    }

    override fun onStart() {
        super.onStart()
        if (AuthFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}