package com.example.projetoseriesmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.EpisodiosMainActivity.Extras.EXTRA_EPISODIO
import com.example.projetoseriesmanager.EpisodiosMainActivity.Extras.EXTRA_EPISODIO_POSITION
import com.example.projetoseriesmanager.EpisodiosMainActivity.Extras.EXTRA_TEMPORADA
import com.example.projetoseriesmanager.databinding.EpisodioActivityBinding
import com.example.projetoseriesmanager.model.Episodio
import com.example.projetoseriesmanager.model.Temporada

class EpisodioActivity : AppCompatActivity() {
    private val activityEpisodioBinding: EpisodioActivityBinding by lazy {
        EpisodioActivityBinding.inflate(layoutInflater)
    }

    private lateinit var temporada: Temporada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityEpisodioBinding.root)

        activityEpisodioBinding.salvarBt.setOnClickListener {
            temporada = intent.getParcelableExtra(EXTRA_TEMPORADA)!!
            val episodio = Episodio(
                activityEpisodioBinding.numeroSequencialEt.text.toString().toInt(),
                activityEpisodioBinding.nomeEt.text.toString(),
                activityEpisodioBinding.duracaoEt.text.toString().toInt(),
                activityEpisodioBinding.vistoCb.isChecked,
                temporada.numeroSequencial
            )

            val resultIntent = intent.putExtra(EXTRA_EPISODIO, episodio)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val position = intent.getIntExtra(EXTRA_EPISODIO_POSITION, -1)
        intent.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.run {
            activityEpisodioBinding.nomeEt.setText(this.nome)
            activityEpisodioBinding.duracaoEt.setText(this.duracao.toString())
            activityEpisodioBinding.numeroSequencialEt.setText(this.numeroSequencial.toString())
            activityEpisodioBinding.vistoCb.isChecked = this.visto
            activityEpisodioBinding.numeroSequencialEt.isEnabled = false
            activityEpisodioBinding.vistoCb.isEnabled = true

            if (position == -1) {
                activityEpisodioBinding.nomeEt.isEnabled = false
                activityEpisodioBinding.duracaoEt.isEnabled = false
                activityEpisodioBinding.vistoCb.isEnabled = false
                activityEpisodioBinding.salvarBt.visibility = View.GONE
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