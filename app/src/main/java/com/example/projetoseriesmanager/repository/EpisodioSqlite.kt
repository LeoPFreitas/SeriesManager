package com.example.projetoseriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.projetoseriesmanager.model.Episodio

class EpisodioSqlite(context: Context) : EpisodioDAO {
    companion object SerieCompanion {
        const val TABLE_EPISODIO = "episodio"
        const val COL_NUMERO_SEQUENCIAL = "numero_sequencial"
        const val COL_NOME = "nome"
        const val COL_DURACAO = "duracao"
        const val COL_VISTO = "visto"
        const val COL_TEMPORADA = "temporada"
    }

    private val serieDatabase: SQLiteDatabase = DatabaseBuilder(context).seriesBD

    override fun create(episodio: Episodio): Long {
        val episodioCv = ContentValues()
        episodioToContentValues(episodioCv, episodio)

        return serieDatabase.insert(TABLE_EPISODIO, null, episodioCv)
    }

    override fun getOne(numeroSequencial: Int): Episodio {
        val episodioCursor = serieDatabase.query(
            true,
            TABLE_EPISODIO,
            null,
            "$TABLE_EPISODIO = ?",
            arrayOf(numeroSequencial.toString()),
            null,
            null,
            null,
            null
        )

        return if (episodioCursor.moveToFirst()) {
            with(episodioCursor) {
                Episodio(
                    getInt(getColumnIndexOrThrow(COL_NUMERO_SEQUENCIAL)),
                    getString(getColumnIndexOrThrow(COL_NOME)),
                    getInt(getColumnIndexOrThrow(COL_DURACAO)),
                    getString(getColumnIndexOrThrow(COL_VISTO)).toBoolean()
                )
            }
        } else {
            Episodio()
        }
    }

    override fun getAll(temporadaNumeroSequencial: Int): MutableList<Episodio> {
        val episodios: MutableList<Episodio> = ArrayList()
        val episodioCursor = serieDatabase.rawQuery(
            "SELECT * FROM $TABLE_EPISODIO WHERE $COL_TEMPORADA = ?",
            arrayOf(temporadaNumeroSequencial.toString())
        )

        if (episodioCursor.moveToFirst()) {
            while (!episodioCursor.isAfterLast) {
                val episodio = Episodio(
                    episodioCursor.getString(
                        episodioCursor.getColumnIndexOrThrow(
                            COL_NUMERO_SEQUENCIAL
                        )
                    ).toInt(),
                    episodioCursor.getString(episodioCursor.getColumnIndexOrThrow(COL_NOME)),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow(COL_DURACAO)),
                    episodioCursor.getString(episodioCursor.getColumnIndexOrThrow(COL_VISTO))
                        .toBoolean(),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow(COL_TEMPORADA))
                )
                episodios.add(episodio)
                episodioCursor.moveToNext()
            }
        }
        return episodios
    }

    override fun update(episodio: Episodio): Int {
        val episodioCv = ContentValues()
        episodioToContentValues(episodioCv, episodio)

        return serieDatabase.update(
            TABLE_EPISODIO,
            episodioCv,
            "$COL_NUMERO_SEQUENCIAL = ?",
            arrayOf(episodio.numeroSequencial.toString())
        )
    }

    override fun remove(numeroSequencial: Int): Int {
        return serieDatabase.delete(
            TABLE_EPISODIO,
            "$COL_NUMERO_SEQUENCIAL = ?",
            arrayOf(numeroSequencial.toString())
        )
    }

    private fun episodioToContentValues(
        episodioCv: ContentValues,
        episodio: Episodio
    ) {
        episodioCv.put(COL_NUMERO_SEQUENCIAL, episodio.numeroSequencial)
        episodioCv.put(COL_NOME, episodio.nome)
        episodioCv.put(COL_DURACAO, episodio.duracao)
        episodioCv.put(COL_VISTO, episodio.visto)
        episodioCv.put(COL_TEMPORADA, episodio.temporada)
    }
}