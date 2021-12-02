package com.example.projetoseriesmanager

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.SerieMainActivity.Extras.EXTRA_SERIE
import com.example.projetoseriesmanager.controller.GeneroController
import com.example.projetoseriesmanager.databinding.SerieActivityBinding
import com.example.projetoseriesmanager.model.Serie

class SerieActivity : AppCompatActivity() {

    private val activitySerieBinding: SerieActivityBinding by lazy {
        SerieActivityBinding.inflate(layoutInflater)
    }

    private val generoController: GeneroController by lazy { GeneroController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        val generos: MutableList<String> = generoController.getAll()
        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.simple_spinner_item, generos)
        activitySerieBinding.generoSp.adapter = spinnerAdapter

        activitySerieBinding.salvarBt.setOnClickListener {
            val serie = Serie(
                activitySerieBinding.nomeEt.text.toString(),
                activitySerieBinding.anoLancamentoEt.text.toString(),
                activitySerieBinding.emissoraEt.text.toString(),
                activitySerieBinding.generoSp.selectedItem.toString()
            )
            val resultIntent = intent.putExtra(EXTRA_SERIE, serie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val position = intent.getIntExtra(SerieMainActivity.EXTRA_POSITION, -1)
        intent.getParcelableExtra<Serie>(EXTRA_SERIE)?.run {
            activitySerieBinding.nomeEt.isEnabled = false
            activitySerieBinding.nomeEt.setText(this.nome)
            activitySerieBinding.anoLancamentoEt.setText(this.anoLancamento)
            activitySerieBinding.emissoraEt.setText(this.emissora)
            activitySerieBinding.generoSp.setSelection(spinnerAdapter.getPosition(this.genero))

            if (position == -1) {
                activitySerieBinding.nomeEt.isEnabled = false
                activitySerieBinding.anoLancamentoEt.isEnabled = false
                activitySerieBinding.emissoraEt.isEnabled = false
                activitySerieBinding.generoSp.isEnabled = false
                activitySerieBinding.salvarBt.visibility = View.GONE
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