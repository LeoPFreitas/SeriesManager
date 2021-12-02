package com.example.projetoseriesmanager.controller

import com.example.projetoseriesmanager.SerieActivity
import com.example.projetoseriesmanager.model.Genero
import com.example.projetoseriesmanager.repository.GeneroDAO
import com.example.projetoseriesmanager.repository.sqlite.GeneroSlite

class GeneroController(serieActivity: SerieActivity) {

    private val generoDAO: GeneroDAO = GeneroSlite(serieActivity)

    fun insert(genero: Genero) = generoDAO.create(genero)
    fun getAll() = generoDAO.getAll()

}
