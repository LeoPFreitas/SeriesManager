package com.example.projetoseriesmanager.controller

import com.example.projetoseriesmanager.TemporadaMainActivity
import com.example.projetoseriesmanager.model.Temporada
import com.example.projetoseriesmanager.repository.TemporadaDAO
import com.example.projetoseriesmanager.repository.TemporadaSqlite
import com.example.projetoseriesmanager.repository.firebase.TemporadaFirebase

class TemporadaController(temporadaMainActivity: TemporadaMainActivity) {
    private val temporadaDAO: TemporadaDAO = TemporadaFirebase()

    fun addTemporada(temporada: Temporada) = temporadaDAO.create(temporada)

    fun update(temporada: Temporada) = temporadaDAO.update(temporada)


    fun findAllTemporadas(nomeSerie: String) = temporadaDAO.getAllTemporadas(nomeSerie)

    fun remove(numeroSequencial: Int) = temporadaDAO.delete(numeroSequencial)
}