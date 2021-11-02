package com.example.projetoseriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoseriesmanager.adapter.SerieRvAdapter
import com.example.projetoseriesmanager.databinding.ActivityMainBinding
import com.example.projetoseriesmanager.model.Serie

class MainActivity : AppCompatActivity(), OnSerieClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private val serieLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val seriesList: MutableList<Serie> = mutableListOf()
    private val seriesAdapter: SerieRvAdapter by lazy { SerieRvAdapter(this, seriesList) }
    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editSerieActivityResultLauncher: ActivityResultLauncher<Intent>

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // Inicializa lista
        initializeSeriesList()

        // associar
        activityMainBinding.seriesRv.adapter = seriesAdapter
        activityMainBinding.seriesRv.layoutManager = serieLayoutManager

        // Add serie
        serieActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
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
                            seriesList[position] = this
                            seriesAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        activityMainBinding.addSerieFab.setOnClickListener {
            serieActivityResultLauncher.launch(Intent(this, SerieActivity::class.java))
        }
    }

    private fun initializeSeriesList() {
        for (i in 1..2) {
            seriesList.add(
                Serie(
                    "Serie ${i}",
                    "${i}",
                    "Emissora",
                    "Gender"
                )
            )
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
                seriesList.removeAt(position)
                seriesAdapter.notifyDataSetChanged()
                true
            }
            else -> false
        }
    }

    override fun onSerieClick(position: Int) {
        val serie = seriesList[position]
        val viewSerieIntent = Intent(this, SerieActivity::class.java)
        viewSerieIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(viewSerieIntent)
    }
}