package com.example.projetoseriesmanager.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoseriesmanager.OnTemporadaClickListener
import com.example.projetoseriesmanager.R
import com.example.projetoseriesmanager.databinding.TemporadaLayoutBinding
import com.example.projetoseriesmanager.model.Temporada

class TemporadaRvAdapter(
    private val onTemporadaClickListener: OnTemporadaClickListener,
    private val temporadaList: MutableList<Temporada>
) : RecyclerView.Adapter<TemporadaRvAdapter.TemporadaLayoutHolder>() {

    var contextMenuPosition: Int = -1

    inner class TemporadaLayoutHolder(temporadaLayoutBinding: TemporadaLayoutBinding) :
        RecyclerView.ViewHolder(temporadaLayoutBinding.root), View.OnCreateContextMenuListener {
        val numeroSequencialTv: TextView = temporadaLayoutBinding.numeroSequencialTv
        val anoLancamentoTv: TextView = temporadaLayoutBinding.anoLancamentoTv

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemporadaLayoutHolder =
        TemporadaLayoutHolder(
            TemporadaLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TemporadaLayoutHolder, position: Int) {
        val temporada = temporadaList[position]

        with(holder) {
            numeroSequencialTv.text = temporada.numeroSequencial.toString()
            anoLancamentoTv.text = temporada.anoLancamento
            itemView.setOnClickListener {
                onTemporadaClickListener.onTemporadaClick(position)
            }
            itemView.setOnLongClickListener {
                contextMenuPosition = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return temporadaList.size
    }
}