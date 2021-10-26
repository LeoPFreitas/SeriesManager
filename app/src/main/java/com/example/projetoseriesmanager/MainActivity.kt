package com.example.projetoseriesmanager

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.databinding.ActivityMainBinding
import com.example.projetoseriesmanager.model.Serie

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

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
    }

    private fun initializeSeriesList() {
        for (i in 1..20) {
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