package com.example.projetoseriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.projetoseriesmanager.model.Genero

class GeneroSlite(context: Context) : GeneroDAO {
    companion object GeneroCompanion {
        const val TABLE_GENERO = "genero"
        const val COL_NOME = "nome"
    }

    private val seriesBD: SQLiteDatabase = DatabaseBuilder(context).seriesBD

    override fun create(genero: Genero): Long {
        val generoCv = ContentValues()
        generoCv.put(COL_NOME, genero.nome)

        return seriesBD.insert(SerieSqlite.TABLE_SERIE, null, generoCv)
    }

    override fun getAll(): MutableList<String> {
        val generos: MutableList<String> = ArrayList()
        val generoCursor = seriesBD.rawQuery("SELECT $COL_NOME from $TABLE_GENERO", null)

        if (generoCursor.moveToFirst()) {
            while (!generoCursor.isAfterLast) {
                val genero: String =
                    generoCursor.getString(generoCursor.getColumnIndexOrThrow("nome"))
                generos.add(genero)
                generoCursor.moveToNext()
            }
        }

        return generos
    }

}