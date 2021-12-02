package com.example.projetoseriesmanager.repository.firebase

import com.example.projetoseriesmanager.model.Episodio
import com.example.projetoseriesmanager.repository.EpisodioDAO
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EpisodioFirebase : EpisodioDAO {
    companion object {
        private const val BD_EPISODIO = "episodio"
    }

    private val episodioRtDb = Firebase.database.getReference(BD_EPISODIO)

    private val episodioList: MutableList<Episodio> = mutableListOf()

    init {
        episodioRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newEpisodio: Episodio? = snapshot.value as? Episodio
                newEpisodio?.apply {
                    if (episodioList.find { it.nome == this.nome } == null) {
                        episodioList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val edited: Episodio? = snapshot.value as? Episodio
                edited?.apply {
                    episodioList[episodioList.indexOfFirst { it.nome == this.nome }] = this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removed: Episodio? = snapshot.value as? Episodio
                removed?.apply {
                    episodioList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
        episodioRtDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                episodioList.clear()
                snapshot.children.forEach {
                    val episodio: Episodio = it.getValue<Episodio>() ?: Episodio()
                    episodioList.add(episodio)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO("Not yet implemented")
            }
        })
    }


    private fun createOrUpdate(episodio: Episodio) {
        val newEpisodio: String = episodio.numeroSequencial.toString()
        episodioRtDb.child(newEpisodio).setValue(episodio)
    }

    override fun create(episodio: Episodio): Long {
        createOrUpdate(episodio)
        return 0L
    }

    override fun getAll(temporadaNumeroSequencial: Int): MutableList<Episodio> {
        return episodioList
    }

    override fun update(episodio: Episodio): Int {
        createOrUpdate(episodio)
        return 0
    }

    override fun remove(numeroSequencial: Int): Int {
        episodioRtDb.child("$numeroSequencial").removeValue()
        return 1
    }

}