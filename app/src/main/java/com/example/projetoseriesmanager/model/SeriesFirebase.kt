package com.example.projetoseriesmanager.model

import com.example.projetoseriesmanager.repository.SerieDAO
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*

class SeriesFirebase : SerieDAO {
    companion object {
        private const val BD_SERIES_MANAGER = "series_manager"
    }

    private val seriesManagerRtDb = Firebase.database.getReference(BD_SERIES_MANAGER)

    private val seriesList: MutableList<Serie> = mutableListOf()

    init {
        seriesManagerRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newSeries: Serie? = snapshot.value as? Serie
                newSeries?.apply {
                    if (seriesList.find { it.nome == newSeries.nome } == null)
                        seriesList.add(this)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updatedSerie: Serie? = snapshot.value as? Serie
                updatedSerie?.apply {
                    seriesList[seriesList.indexOfFirst { it.nome == this.nome }] = this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val series: Serie? = snapshot.value as? Serie
                series?.apply {
                    seriesList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
        seriesManagerRtDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                seriesList.clear()
                snapshot.children.forEach {
                    val serie: Serie = it.getValue<Serie>()?: Serie()
                    seriesList.add(serie)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO("Not yet implemented")
            }
        })
    }

    override fun create(serie: Serie): Long {
        createOrUpdate(serie)
        return 0L
    }

    override fun getOne(nome: String): Serie = seriesList.firstOrNull { it.nome == nome } ?: Serie()

    override fun getAll(): MutableList<Serie> = seriesList

    override fun update(serie: Serie): Int {
        createOrUpdate(serie)
        return 0
    }

    override fun remove(nome: String): Int {
        seriesManagerRtDb.child(nome).removeValue()
        return 1
    }

    private fun createOrUpdate(series: Serie) {
        seriesManagerRtDb.child(series.nome).setValue(series)
    }
}