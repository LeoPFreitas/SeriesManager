package com.example.projetoseriesmanager.repository

import com.example.projetoseriesmanager.model.Genero

interface GeneroDAO {
    fun create(genero: Genero): Long
    fun getAll(): MutableList<String>
}
