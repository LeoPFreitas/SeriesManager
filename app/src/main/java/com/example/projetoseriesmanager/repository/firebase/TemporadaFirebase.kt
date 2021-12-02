package com.example.projetoseriesmanager.repository.firebase

import com.example.projetoseriesmanager.model.Temporada
import com.example.projetoseriesmanager.repository.TemporadaDAO
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TemporadaFirebase : TemporadaDAO {
    companion object {
        private const val BD_TEMPORADA = "temporadas"
    }

    private val temporadaRtDb = Firebase.database.getReference(BD_TEMPORADA)

    private val temporadaList: MutableList<Temporada> = mutableListOf()

    init {
        temporadaRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newTemporada: Temporada? = snapshot.value as? Temporada
                newTemporada?.apply {
                    if (temporadaList.find { it.numeroSequencial == this.numeroSequencial } == null) {
                        temporadaList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val editTemporada: Temporada? = snapshot.value as? Temporada
                editTemporada?.apply {
                    temporadaList[temporadaList.indexOfFirst { it.numeroSequencial == this.numeroSequencial }] =
                        this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removed: Temporada? = snapshot.value as? Temporada
                removed?.apply {
                    temporadaList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
        temporadaRtDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                temporadaList.clear()
                snapshot.children.forEach {
                    val temporada: Temporada = it.getValue<Temporada>() ?: Temporada()
                    temporadaList.add(temporada)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO("Not yet implemented")
            }
        })
    }

    override fun delete(numeroSequencial: Int): Int {
        temporadaRtDb.child(numeroSequencial.toString()).removeValue()
        return 1
    }

    override fun create(temporada: Temporada): Long {
        createOrUpdate(temporada)
        return 0L
    }

    override fun getAllTemporadas(nomeSerie: String): MutableList<Temporada> {
        return temporadaList
    }

    override fun update(temporada: Temporada): Int {
        createOrUpdate(temporada)
        return -1
    }

    private fun createOrUpdate(temporada: Temporada) {
        val nodeTemporada: String = temporada.numeroSequencial.toString()
        temporadaRtDb.child(nodeTemporada).setValue(temporada)
    }

}