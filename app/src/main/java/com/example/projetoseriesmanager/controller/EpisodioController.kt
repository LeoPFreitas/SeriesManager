package com.example.projetoseriesmanager.controller

import com.example.projetoseriesmanager.EpisodiosMainActivity
import com.example.projetoseriesmanager.model.Episodio
import com.example.projetoseriesmanager.repository.EpisodioDAO
import com.example.projetoseriesmanager.repository.firebase.EpisodioFirebase
import com.example.projetoseriesmanager.repository.sqlite.EpisodioSqlite

class EpisodioController(episodioActivity: EpisodiosMainActivity) {
    private val episodioDAO: EpisodioDAO = EpisodioFirebase()

    fun insert(episodio: Episodio) = episodioDAO.create(episodio)
    fun getAll(temporadaNumeroSequencial: Int) = episodioDAO.getAll(temporadaNumeroSequencial)
    fun update(episodio: Episodio) = episodioDAO.update(episodio)
    fun remove(numeroSequencial: Int) = episodioDAO.remove(numeroSequencial)
}