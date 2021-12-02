package com.example.projetoseriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoseriesmanager.adapter.EpisodioRvAdapter
import com.example.projetoseriesmanager.controller.EpisodioController
import com.example.projetoseriesmanager.databinding.EpisodiosMainActivityBinding
import com.example.projetoseriesmanager.model.Episodio
import com.example.projetoseriesmanager.model.Temporada
import com.google.android.material.snackbar.Snackbar

class EpisodiosMainActivity : AppCompatActivity() {
    companion object Extras {
        const val EXTRA_EPISODIO = "EXTRA_EPISODIO"
        const val EXTRA_TEMPORADA = "EXTRA_TEMPORADA"
        const val EXTRA_EPISODIO_POSITION = "EXTRA_EPISODIO_POSITION"
    }

    private val episodioMainActivityBinding: EpisodiosMainActivityBinding by lazy {
        EpisodiosMainActivityBinding.inflate(layoutInflater)
    }

    private lateinit var episodioActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editEpisodioActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewEpisodioActivityResultLauncher: ActivityResultLauncher<Intent>

    private val episodioController: EpisodioController by lazy { EpisodioController(this) }
    private val episodioList: MutableList<Episodio> by lazy {
        val episodios = episodioController.getAll(temporada.numeroSequencial)
        episodios
    }
    private val episodioRvAdapter: EpisodioRvAdapter by lazy {
        EpisodioRvAdapter(episodioList)
    }
    private val episodiosLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private lateinit var temporada: Temporada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(episodioMainActivityBinding.root)

        temporada = intent.getParcelableExtra(EXTRA_TEMPORADA)!!

        // associar
        episodioMainActivityBinding.episodiosRv.adapter = episodioRvAdapter
        episodioMainActivityBinding.episodiosRv.layoutManager = episodiosLayoutManager

        // Add episodio
        episodioActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
                        episodioController.insert(this)
                        episodioList.add(this)
                        episodioRvAdapter.notifyDataSetChanged()
                    }
                }
            }

        // edit episodio
        editEpisodioActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    val position =
                        res.data?.getIntExtra(EXTRA_EPISODIO_POSITION, -1)

                    res.data?.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
                        if (position != null && position != -1) {
                            episodioController.update(this)
                            episodioList[position] = this
                            episodioRvAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        viewEpisodioActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
                    }
                }
            }

        episodioMainActivityBinding.addEpisodioFb.setOnClickListener {
            val addEpisodioIntent = Intent(this, EpisodioActivity::class.java)
            addEpisodioIntent.putExtra(EXTRA_TEMPORADA, temporada)
            episodioActivityResultLauncher.launch(addEpisodioIntent)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = episodioRvAdapter.contextMenuPosition
        return when (item.itemId) {
            R.id.editEpisodioMi -> {
                val episodio = episodioList[position]
                val editEpisodioIntent = Intent(this, EpisodioActivity::class.java)
                editEpisodioIntent.putExtra(EXTRA_EPISODIO, episodio)
                editEpisodioIntent.putExtra(EXTRA_TEMPORADA, temporada)
                editEpisodioIntent.putExtra(EXTRA_EPISODIO_POSITION, position)
                editEpisodioActivityResultLauncher.launch(editEpisodioIntent)
                true
            }
            R.id.viewEpisodioMi -> {
                val episodio = episodioList[position]
                val viewEpisodioIntent = Intent(this, EpisodioActivity::class.java)
                viewEpisodioIntent.putExtra(EXTRA_EPISODIO, episodio)
                viewEpisodioIntent.putExtra(EXTRA_TEMPORADA, temporada)
                viewEpisodioActivityResultLauncher.launch(viewEpisodioIntent)
                true
            }
            R.id.removeEpisodioMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim") { _, _ ->
                        episodioController.remove(episodioList[position].numeroSequencial)
                        Snackbar.make(
                            episodioMainActivityBinding.root,
                            "Episodio removido",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        episodioList.removeAt(position)
                        episodioRvAdapter.notifyDataSetChanged()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(
                            episodioMainActivityBinding.root,
                            "Remoção cancelada",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    create()
                }.show()
                true
            }
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        if (AuthFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}