package com.example.projetoseriesmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.SerieMainActivity.Extras.EXTRA_SERIE
import com.example.projetoseriesmanager.TemporadaMainActivity.Extras.EXTRA_TEMPORADA
import com.example.projetoseriesmanager.databinding.TemporadaActivityBinding
import com.example.projetoseriesmanager.model.Serie
import com.example.projetoseriesmanager.model.Temporada

class TemporadaActivity : AppCompatActivity() {
    private val temporadaActivity: TemporadaActivityBinding by lazy {
        TemporadaActivityBinding.inflate(layoutInflater)
    }

    private lateinit var serie: Serie
    private lateinit var temporada: Temporada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(temporadaActivity.root)

        temporadaActivity.salvarBt.setOnClickListener {
            serie = intent.getParcelableExtra(EXTRA_SERIE)!!
            temporada = Temporada(
                temporadaActivity.numeroSequencialEpisodioEt.text.toString().toInt(),
                temporadaActivity.anoLancamentoEt.text.toString(),
                serie.nome
            )
            val resultIntent = intent.putExtra(EXTRA_TEMPORADA, temporada)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val position = intent.getIntExtra(TemporadaMainActivity.EXTRA_TEMPORADA_POSITION, -1)
        intent.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.run {
            temporadaActivity.numeroSequencialEpisodioEt.setText(this.numeroSequencial.toString())
            temporadaActivity.anoLancamentoEt.setText(this.anoLancamento)

            temporadaActivity.numeroSequencialEpisodioEt.isEnabled = false

            if (position == -1) {
                temporadaActivity.anoLancamentoEt.isEnabled = false
                temporadaActivity.salvarBt.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AuthFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    } 
}