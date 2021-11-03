package com.example.projetoseriesmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.SerieMainActivity.Extras.EXTRA_SERIE
import com.example.projetoseriesmanager.databinding.TemporadaMainActivityBinding
import com.example.projetoseriesmanager.model.Serie

class TemporadaMainActivity : AppCompatActivity() {
    private lateinit var serie: Serie

    private val temporadaMainActivityBinding: TemporadaMainActivityBinding by lazy {
        TemporadaMainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(temporadaMainActivityBinding.root)

        serie = intent.getParcelableExtra(EXTRA_SERIE)!!
        fillActivityWithSerieData()

    }

    private fun fillActivityWithSerieData() {
        temporadaMainActivityBinding.nomeSerieTv.text = serie.nome
        temporadaMainActivityBinding.anoLancamentoTv.text = serie.anoLancamento
        temporadaMainActivityBinding.emissoraTv.text = serie.emissora
        temporadaMainActivityBinding.generoTv.text = serie.genero
    }
}