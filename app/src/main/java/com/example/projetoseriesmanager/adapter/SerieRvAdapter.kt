package com.example.projetoseriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoseriesmanager.OnSerieClickListener
import com.example.projetoseriesmanager.R
import com.example.projetoseriesmanager.databinding.SerieLayoutBinding
import com.example.projetoseriesmanager.model.Serie

class SerieRvAdapter(
    private val onSerieClickListener: OnSerieClickListener,
    private val serieList: MutableList<Serie>
) : RecyclerView.Adapter<SerieRvAdapter.SerieLayoutHolder>() {

    var contextMenuPosition: Int = -1

    inner class SerieLayoutHolder(serieLayoutBinding: SerieLayoutBinding) :
        RecyclerView.ViewHolder(serieLayoutBinding.root), View.OnCreateContextMenuListener {
        val nomeTv: TextView = serieLayoutBinding.nomeTv
        val anoLancamentoTv: TextView = serieLayoutBinding.anoLancamentoTv
        val emissoraTv: TextView = serieLayoutBinding.emissoraTv
        val generoTv: TextView = serieLayoutBinding.generoTv

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_serie, menu)
        }
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
                contextMenuPosition = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return serieList.size
    }
}