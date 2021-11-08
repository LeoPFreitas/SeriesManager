package com.example.projetoseriesmanager.adapter

import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoseriesmanager.R
import com.example.projetoseriesmanager.databinding.EpisodioLayoutBinding
import com.example.projetoseriesmanager.model.Episodio

class EpisodioRvAdapter(
    private val episodioList: MutableList<Episodio>
) : RecyclerView.Adapter<EpisodioRvAdapter.EpisodioLayoutHolder>() {

    var contextMenuPosition: Int = -1

    inner class EpisodioLayoutHolder(episodioLayoutBinding: EpisodioLayoutBinding) :
        RecyclerView.ViewHolder(episodioLayoutBinding.root), View.OnCreateContextMenuListener {

        val nomeEpisodioTv: TextView = episodioLayoutBinding.nomeEpisodioTv
        val numeroSequencialEpisodioTv: TextView = episodioLayoutBinding.numeroSequencialEpisodioTv
        val duracaoEpisodioTv: TextView = episodioLayoutBinding.duracaoEpisodioTv
        val foiVistoCb: CheckBox = episodioLayoutBinding.foiVistoCb

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_episodio, menu)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodioRvAdapter.EpisodioLayoutHolder =
        EpisodioLayoutHolder(
            EpisodioLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodioLayoutHolder, position: Int) {
        val episodio = episodioList[position]

        with(holder) {
            nomeEpisodioTv.text = episodio.nome
            numeroSequencialEpisodioTv.text = episodio.numeroSequencial.toString()
            duracaoEpisodioTv.text = episodio.duracao.toString()
            foiVistoCb.isChecked = episodio.visto
            itemView.setOnLongClickListener {
                contextMenuPosition = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return episodioList.size
    }
}