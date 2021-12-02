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
import com.example.projetoseriesmanager.SerieMainActivity.Extras.EXTRA_SERIE
import com.example.projetoseriesmanager.adapter.TemporadaRvAdapter
import com.example.projetoseriesmanager.controller.TemporadaController
import com.example.projetoseriesmanager.databinding.TemporadaMainActivityBinding
import com.example.projetoseriesmanager.model.Serie
import com.example.projetoseriesmanager.model.Temporada
import com.google.android.material.snackbar.Snackbar

class TemporadaMainActivity : AppCompatActivity(), OnTemporadaClickListener {
    companion object Extras {
        const val EXTRA_TEMPORADA = "EXTRA_TEMPORADA"
        const val EXTRA_TEMPORADA_POSITION = "EXTRA_SERIE_POSICAO"
    }

    private lateinit var temporadaActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editTemporadaActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewTemporadaActivityResultLauncher: ActivityResultLauncher<Intent>


    private val temporadaController: TemporadaController by lazy { TemporadaController(this) }
    private val temporadaList: MutableList<Temporada> by lazy {
        val findAllTemporadas = temporadaController.findAllTemporadas(serie.nome)
        findAllTemporadas
    }
    private val temporadaRvAdapter: TemporadaRvAdapter by lazy {
        TemporadaRvAdapter(
            this,
            temporadaList
        )
    }
    private val temporadaLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val temporadaMainActivityBinding: TemporadaMainActivityBinding by lazy {
        TemporadaMainActivityBinding.inflate(layoutInflater)
    }

    private lateinit var serie: Serie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(temporadaMainActivityBinding.root)

        // dados da serie
        fillActivityWithSerieData()

        temporadaMainActivityBinding.temporadaRv.adapter = temporadaRvAdapter
        temporadaMainActivityBinding.temporadaRv.layoutManager = temporadaLayoutManager

        // Add temporada
        temporadaActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
                if (resultado.resultCode == RESULT_OK) {
                    resultado.data?.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.apply {
                        temporadaController.addTemporada(this)
                        temporadaList.add(this)
                        temporadaRvAdapter.notifyDataSetChanged()
                    }
                }
            }

        // edit temporada
        editTemporadaActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    val position = res.data?.getIntExtra(EXTRA_TEMPORADA_POSITION, -1)

                    res.data?.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.apply {
                        if (position != null && position != -1) {
                            temporadaController.update(this)
                            temporadaList[position] = this
                            temporadaRvAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        viewTemporadaActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.apply {
                    }
                }
            }

        temporadaMainActivityBinding.addTemporadaFab.setOnClickListener {
            val addTemporadaIntent = Intent(this, TemporadaActivity::class.java)
            addTemporadaIntent.putExtra(EXTRA_SERIE, serie)
            temporadaActivityResultLauncher.launch(addTemporadaIntent)
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = temporadaRvAdapter.contextMenuPosition
        return when (item.itemId) {
            R.id.editTemporadaMi -> {
                val temporada = temporadaList[position]
                val editTemporadaIntent = Intent(this, TemporadaActivity::class.java)
                editTemporadaIntent.putExtra(EXTRA_TEMPORADA, temporada)
                editTemporadaIntent.putExtra(EXTRA_TEMPORADA_POSITION, position)
                editTemporadaIntent.putExtra(EXTRA_SERIE, serie)
                editTemporadaActivityResultLauncher.launch(editTemporadaIntent)
                true
            }
            R.id.viewTemporadaMi -> {
                val temporada = temporadaList[position]
                val viewTemporadaIntent = Intent(this, TemporadaActivity::class.java)
                viewTemporadaIntent.putExtra(EXTRA_TEMPORADA, temporada)
                viewTemporadaActivityResultLauncher.launch(viewTemporadaIntent)
                true
            }
            R.id.removeTemporadaMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim") { _, _ ->
                        temporadaController.remove(temporadaList[position].numeroSequencial)
                        Snackbar.make(
                            temporadaMainActivityBinding.root,
                            "Livro removido",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        temporadaList.removeAt(position)
                        temporadaRvAdapter.notifyDataSetChanged()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(
                            temporadaMainActivityBinding.root,
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

    override fun onTemporadaClick(position: Int) {
        val temporada = temporadaList[position]
        val viewTemporadaIntent = Intent(this, EpisodiosMainActivity::class.java)
        viewTemporadaIntent.putExtra(EXTRA_TEMPORADA, temporada)
        startActivity(viewTemporadaIntent)
    }

    private fun fillActivityWithSerieData() {
        serie = intent.getParcelableExtra(EXTRA_SERIE)!!
        temporadaMainActivityBinding.nomeSerieTv.text = serie.nome
        temporadaMainActivityBinding.anoLancamentoTv.text = serie.anoLancamento
        temporadaMainActivityBinding.emissoraTv.text = serie.emissora
        temporadaMainActivityBinding.generoTv.text = serie.genero
    }

    override fun onStart() {
        super.onStart()
        if (AuthFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.refreshMi -> {
            temporadaRvAdapter.notifyDataSetChanged()
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
}