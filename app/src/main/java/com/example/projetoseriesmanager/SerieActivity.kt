package com.example.projetoseriesmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.MainActivity.Extras.EXTRA_SERIE
import com.example.projetoseriesmanager.databinding.ActivitySerieBinding
import com.example.projetoseriesmanager.model.Serie

class SerieActivity : AppCompatActivity() {

    private val activitySerieBinding: ActivitySerieBinding by lazy {
        ActivitySerieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        activitySerieBinding.salvarBt.setOnClickListener {
            val serie = Serie(
                activitySerieBinding.nomeEt.text.toString(),
                activitySerieBinding.anoLancamentoEt.text.toString(),
                activitySerieBinding.emissoraEt.text.toString(),
                "genero"
            )
            val resultIntent = intent.putExtra(EXTRA_SERIE, serie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)
        intent.getParcelableExtra<Serie>(EXTRA_SERIE)?.run {
            activitySerieBinding.nomeEt.isEnabled = false
            activitySerieBinding.nomeEt.setText(this.nome)
            activitySerieBinding.anoLancamentoEt.setText(this.anoLancamento)
            activitySerieBinding.emissoraEt.setText(this.emissora)
            //                activitySerieBinding.generoSp.selectedItem.toString()

            if (position == -1) {
                activitySerieBinding.nomeEt.isEnabled = false
                activitySerieBinding.anoLancamentoEt.isEnabled = false
                activitySerieBinding.emissoraEt.isEnabled = false
                activitySerieBinding.generoSp.isEnabled = false
                activitySerieBinding.salvarBt.visibility = View.GONE
            }
        }
    }
}