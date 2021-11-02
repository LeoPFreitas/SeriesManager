package com.example.projetoseriesmanager.repository

import com.example.projetoseriesmanager.model.Serie

interface SerieDAO {
    fun create(serie: Serie): Long
    fun getOne(nome: String): Serie
    fun getAll(): MutableList<Serie>
    fun update(serie: Serie): Int
    fun remove(nome: String): Int
}