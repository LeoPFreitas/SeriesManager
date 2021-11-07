package com.example.projetoseriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.projetoseriesmanager.model.Temporada

class TemporadaSqlite(context: Context) : TemporadaDAO {
    companion object SerieCompanion {
        const val TABLE_TEMPORADA = "temporada"
        const val COL_NUMERO_SEQUENCIAL = "numero_sequencial"
        const val COL_ANO_LANCAMENTO = "ano_lancamento"
        const val COL_NOME_SERIE = "serie"
    }

    private val serieDatabase: SQLiteDatabase = DatabaseBuilde(context).seriesBD

    override fun delete(numeroSequencial: Int): Int {
        serieDatabase.execSQL("PRAGMA foreign_keys = ON")
        return serieDatabase.delete(
            TABLE_TEMPORADA,
            "$COL_NUMERO_SEQUENCIAL = ?",
            arrayOf(numeroSequencial.toString())
        )
    }

    override fun create(temporada: Temporada): Long {
        val temporadaCv = ContentValues()
        temporadaToContentValues(temporadaCv, temporada)

        val insert = serieDatabase.insert(TABLE_TEMPORADA, null, temporadaCv)
        return insert
    }

    private fun temporadaToContentValues(
        temporadaCv: ContentValues,
        temporada: Temporada
    ) {
        temporadaCv.put(COL_NUMERO_SEQUENCIAL, temporada.numeroSequencial)
        temporadaCv.put(COL_ANO_LANCAMENTO, temporada.anoLancamento)
        temporadaCv.put(COL_NOME_SERIE, temporada.nomeSerie)
    }

    override fun getAllTemporadas(nomeSerie: String): MutableList<Temporada> {
        val temporadas: MutableList<Temporada> = ArrayList()
        val temporadaCursor = serieDatabase.rawQuery(
            "SELECT $COL_NOME_SERIE, $COL_ANO_LANCAMENTO, $COL_NUMERO_SEQUENCIAL FROM $TABLE_TEMPORADA WHERE $COL_NOME_SERIE = ?;",
            arrayOf(nomeSerie)
        )

        if (temporadaCursor.moveToFirst()) {
            while (!temporadaCursor.isAfterLast) {
                val temporada: Temporada = Temporada(
                    temporadaCursor.getInt(
                        temporadaCursor.getColumnIndexOrThrow(
                            COL_NUMERO_SEQUENCIAL
                        )
                    ),
                    temporadaCursor.getString(
                        temporadaCursor.getColumnIndexOrThrow(
                            COL_ANO_LANCAMENTO
                        )
                    ),
                    temporadaCursor.getString(temporadaCursor.getColumnIndexOrThrow(COL_NOME_SERIE)),
                )
                temporadas.add(temporada)
                temporadaCursor.moveToNext()
            }
        }
        return temporadas
    }

    override fun getTemporadaId(nomeSerie: String, numeroSequencial: Int): Int {
        val temporadaCursor = serieDatabase.rawQuery(
            "SELECT id from temporada WHERE numero_sequencial = ?  AND nome_serie = ?",
            arrayOf(numeroSequencial.toString(), nomeSerie)
        )
        var temporadaId: Int = 0
        if (temporadaCursor.moveToFirst()) {
            temporadaId = temporadaCursor.getInt(temporadaCursor.getColumnIndexOrThrow("id"))
        }
        return temporadaId
    }

    override fun update(temporada: Temporada): Int {
        val temporadaCv = ContentValues()
        temporadaToContentValues(temporadaCv, temporada)

        return serieDatabase.update(
            TABLE_TEMPORADA,
            temporadaCv,
            "$COL_NUMERO_SEQUENCIAL = ?",
            arrayOf(temporada.numeroSequencial.toString())
        )
    }
}