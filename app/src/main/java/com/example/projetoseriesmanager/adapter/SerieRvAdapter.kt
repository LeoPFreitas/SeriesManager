package com.example.projetoseriesmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoseriesmanager.OnSerieClickListener
import com.example.projetoseriesmanager.databinding.SerieLayoutBinding
import com.example.projetoseriesmanager.model.Serie

class SerieRvAdapter(
    private val onSerieClickListener: OnSerieClickListener,
    private val serieList: MutableList<Serie>
) : RecyclerView.Adapter<SerieRvAdapter.SerieLayoutHolder>() {

    var initPosition: Int = -1

    inner class SerieLayoutHolder(serieLayoutBinding: SerieLayoutBinding) :
        RecyclerView.ViewHolder(serieLayoutBinding.root) {
        val nomeTv: TextView = serieLayoutBinding.nomeTv
        val anoLancamentoTv: TextView = serieLayoutBinding.anoLancamentoTv
        val emissoraTv: TextView = serieLayoutBinding.emissoraTv
        val generoTv: TextView = serieLayoutBinding.generoTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieLayoutHolder =
        SerieLayoutHolder(
            SerieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SerieLayoutHolder, position: Int) {
        val serie = serieList[position]

        with(holder) {
            nomeTv.text = serie.nome
            anoLancamentoTv.text = serie.anoLancamento
            emissoraTv.text = serie.emissora
            generoTv.text = serie.genero
            itemView.setOnClickListener {
                onSerieClickListener.onSerieClick(position)
            }
            itemView.setOnLongClickListener {
                initPosition = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return serieList.size
    }
}