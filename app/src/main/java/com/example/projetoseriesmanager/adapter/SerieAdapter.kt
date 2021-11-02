package com.example.projetoseriesmanager.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.projetoseriesmanager.databinding.SerieLayoutBinding
import com.example.projetoseriesmanager.model.Serie

class SerieAdapter(val contexto: Context, layout: Int, val serieList: MutableList<Serie>) :
    ArrayAdapter<Serie>(contexto, layout, serieList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val serieLayoutView: View

        if (convertView != null) {
            serieLayoutView = convertView
        } else {
            val inflate =
                SerieLayoutBinding.inflate(contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            with(inflate) {
                serieLayoutView = inflate.root
                serieLayoutView.tag =
                    SerieLayoutHolder(nomeTv, anoLancamentoTv, emissoraTv, generoTv)
            }
        }

        val serie = serieList[position]

        with(serieLayoutView.tag as SerieLayoutHolder) {
            nomeTv.text = serie.nome
            anoLancamentoTv.text = serie.anoLancamento
            emissoraTv.text = serie.emissora
            generoTv.text = serie.genero
        }

        return serieLayoutView
    }

    private data class SerieLayoutHolder(
        val nomeTv: TextView,
        val anoLancamentoTv: TextView,
        val emissoraTv: TextView,
        val generoTv: TextView
    )
}