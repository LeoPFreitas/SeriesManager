package com.example.projetoseriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.projetoseriesmanager.model.Serie

class SerieSqlite(context: Context) : SerieDAO {
    companion object SerieCompanion {
        const val TABLE_SERIE = "serie"
        const val COL_NOME = "nome"
        const val COL_ANO_LANCAMENTO = "ano_lancamento"
        const val COL_EMISSORA = "emissora"
        const val COL_GENERO = "genero"
    }

    private val serieDatabase: SQLiteDatabase = DatabaseBuilde(context).seriesBD

    override fun create(serie: Serie): Long {
        val serieCv = ContentValues()
        serieToContentValues(serieCv, serie)

        return serieDatabase.insert(TABLE_SERIE, null, serieCv)
    }

    override fun getOne(nome: String): Serie {
        val serieCursor = serieDatabase.query(
            true,
            TABLE_SERIE,
            null,
            "$COL_NOME = ?",
            arrayOf(nome),
            null,
            null,
            null,
            null
        )

        return if (serieCursor.moveToFirst()) {
            with(serieCursor) {
                Serie(
                    getString(getColumnIndexOrThrow(COL_NOME)),
                    getString(getColumnIndexOrThrow(COL_ANO_LANCAMENTO)),
                    getString(getColumnIndexOrThrow(COL_EMISSORA)),
                    getString(getColumnIndexOrThrow(COL_GENERO)),
                )

            }
        } else {
            Serie()
        }
    }

    override fun getAll(): MutableList<Serie> {
        val series: MutableList<Serie> = ArrayList()
        val serieCursor = serieDatabase.rawQuery("SELECT * FROM $TABLE_SERIE", null)

        if (serieCursor.moveToFirst()) {
            while (!serieCursor.isAfterLast) {
                val serie = Serie(
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow(COL_NOME)),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow(COL_ANO_LANCAMENTO)),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow(COL_EMISSORA)),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow(COL_GENERO)),
                )
                series.add(serie)
                serieCursor.moveToNext()
            }
        }
        return series
    }

    override fun update(serie: Serie): Int {
        val serieCv = ContentValues()
        serieToContentValues(serieCv, serie)

        return serieDatabase.update(TABLE_SERIE, serieCv, "$COL_NOME = ?", arrayOf(serie.nome))
    }

    override fun remove(nome: String): Int {
        return serieDatabase.delete(TABLE_SERIE, "$COL_NOME = ?", arrayOf(nome))
    }

    private fun serieToContentValues(
        serieCv: ContentValues,
        serie: Serie
    ) {
        serieCv.put(COL_NOME, serie.nome)
        serieCv.put(COL_ANO_LANCAMENTO, serie.anoLancamento)
        serieCv.put(COL_EMISSORA, serie.emissora)
        serieCv.put(COL_GENERO, serie.genero)
    }
}