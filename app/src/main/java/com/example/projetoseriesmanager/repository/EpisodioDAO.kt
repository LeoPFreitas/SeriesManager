package com.example.projetoseriesmanager.repository

import com.example.projetoseriesmanager.model.Episodio

interface EpisodioDAO {
    fun create(episodio: Episodio): Long
    fun getAll(temporadaNumeroSequencial: Int): MutableList<Episodio>
    fun update(episodio: Episodio): Int
    fun remove(numeroSequencial: Int): Int
}