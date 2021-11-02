package com.example.projetoseriesmanager.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.projetoseriesmanager.R

class DatabaseBuilde(context: Context) {

    companion object {
        private val BD_SERIES_MANAGER = "series_manager"

        private val CRIAR_TABELA_GENERO_STMT = "CREATE TABLE IF NOT EXISTS genero (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL UNIQUE);"

        private val CRIAR_TABELA_SERIE_STMT =
            "CREATE TABLE IF NOT EXISTS ${SerieSqlite.TABLE_SERIE} (" +
                    "${SerieSqlite.COL_NOME} TEXT NOT NULL PRIMARY KEY, " +
                    "${SerieSqlite.COL_ANO_LANCAMENTO} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_EMISSORA} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_GENERO} TEXT NOT NULL, " +
                    "FOREIGN KEY(${SerieSqlite.COL_GENERO}) REFERENCES genero(nome)" +
                    ");"
    }

    val seriesBD: SQLiteDatabase =
        context.openOrCreateDatabase(BD_SERIES_MANAGER, MODE_PRIVATE, null)

    init {
        try {
            seriesBD.execSQL(CRIAR_TABELA_GENERO_STMT)
            seriesBD.execSQL(CRIAR_TABELA_SERIE_STMT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }
}