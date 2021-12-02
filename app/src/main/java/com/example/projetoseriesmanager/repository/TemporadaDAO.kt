package com.example.projetoseriesmanager.repository

import com.example.projetoseriesmanager.model.Temporada

interface TemporadaDAO {
    fun delete(numeroSequencial: Int): Int
    fun create(temporada: Temporada): Long

    fun getAllTemporadas(nomeSerie: String): MutableList<Temporada>
    fun update(temporada: Temporada): Int
}