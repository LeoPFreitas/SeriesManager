package com.example.projetoseriesmanager

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.databinding.ActivityMainBinding
import com.example.projetoseriesmanager.model.Serie

class MainActivity : AppCompatActivity() {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>

    // datasource
    private val seriesList: MutableList<Serie> = mutableListOf()

    // adapter
    private val seriesAdapter: ArrayAdapter<String> by lazy {
//        val resource = R.id.seriesLayout
        ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, seriesList.run {
            val seriesStringList = mutableListOf<String>()
            this.forEach { serie -> seriesStringList.add(serie.toString()) }
            seriesStringList
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // Inicializa lista
        initializeSeriesList()

        // associar
        activityMainBinding.activityMain.adapter = seriesAdapter

        serieActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == RESULT_OK) {
                    res.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                        seriesList.add(this) // n é necessário
                        seriesAdapter.add(this.toString())
                    }
                }
            }

        activityMainBinding.addSerieBtn.setOnClickListener {
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

}