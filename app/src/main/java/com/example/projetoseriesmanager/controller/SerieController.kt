package com.example.projetoseriesmanager.controller

import com.example.projetoseriesmanager.SerieMainActivity
import com.example.projetoseriesmanager.model.Serie
import com.example.projetoseriesmanager.model.SeriesFirebase
import com.example.projetoseriesmanager.repository.SerieDAO
import com.example.projetoseriesmanager.repository.SerieSqlite

class SerieController(mainActivity: SerieMainActivity) {
    private val serieDAO: SerieDAO = SeriesFirebase()

    fun insert(serie: Serie) = serieDAO.create(serie)
    fun getOne(nome: String) = serieDAO.getOne(nome)
    fun getAll() = serieDAO.getAll()
    fun update(serie: Serie) = serieDAO.update(serie)
    fun remove(nome: String) = serieDAO.remove(nome)
}