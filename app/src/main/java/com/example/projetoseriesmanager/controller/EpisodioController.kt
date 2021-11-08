package com.example.projetoseriesmanager.controller

import com.example.projetoseriesmanager.EpisodiosMainActivity
import com.example.projetoseriesmanager.model.Episodio
import com.example.projetoseriesmanager.repository.EpisodioDAO
import com.example.projetoseriesmanager.repository.EpisodioSqlite

class EpisodioController(episodioActivity: EpisodiosMainActivity) {
    private val episodioDAO: EpisodioDAO = EpisodioSqlite(episodioActivity)

    fun insert(episodio: Episodio) = episodioDAO.create(episodio)
    fun getOne(numeroSequencial: Int) = episodioDAO.getOne(numeroSequencial)
    fun getAll(temporadaNumeroSequencial: Int) = episodioDAO.getAll(temporadaNumeroSequencial)
    fun update(episodio: Episodio) = episodioDAO.update(episodio)
    fun remove(numeroSequencial: Int) = episodioDAO.remove(numeroSequencial)
}