package com.example.projetoseriesmanager.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.projetoseriesmanager.R

class DatabaseBuilder(context: Context) {

    companion object {
        private val BD_SERIES_MANAGER = "series_manager"

        private val CRIAR_TABELA_GENERO_STMT =
            "CREATE TABLE IF NOT EXISTS ${GeneroSlite.TABLE_GENERO} (" +
                    "${GeneroSlite.TABLE_GENERO} TEXT NOT NULL PRIMARY KEY);"

        private val INSERT_ROMANCE_TABELA_GENERO_STMT =
            "INSERT INTO genero (nome) VALUES('Romance');"
        private val INSERT_AVENTURA_TABELA_GENERO_STMT =
            "INSERT INTO genero (nome) VALUES('Aventura');"
        private val INSERT_TERROR_TABELA_GENERO_STMT = "INSERT INTO genero (nome) VALUES('Terror');"

        private val CRIAR_TABELA_SERIE_STMT =
            "CREATE TABLE IF NOT EXISTS ${SerieSqlite.TABLE_SERIE} (" +
                    "${SerieSqlite.COL_NOME} TEXT NOT NULL PRIMARY KEY, " +
                    "${SerieSqlite.COL_ANO_LANCAMENTO} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_EMISSORA} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_GENERO} TEXT NOT NULL, " +
                    "FOREIGN KEY(${SerieSqlite.COL_GENERO}) REFERENCES genero(nome)" +
                    ");"

        private val CRIAR_TABELA_TEMPORADA_STMT =
            "CREATE TABLE IF NOT EXISTS ${TemporadaSqlite.TABLE_TEMPORADA} (" +
                    "${TemporadaSqlite.COL_NUMERO_SEQUENCIAL} INTEGER NOT NULL PRIMARY KEY, " +
                    "${TemporadaSqlite.COL_ANO_LANCAMENTO} TEXT NOT NULL, " +
                    "${TemporadaSqlite.COL_NOME_SERIE} TEXT NOT NULL, " +
                    "FOREIGN KEY(${TemporadaSqlite.COL_NOME_SERIE}) REFERENCES ${SerieSqlite.TABLE_SERIE}(${SerieSqlite.COL_NOME}) ON DELETE CASCADE" +
                    ");"
    }

    val seriesBD: SQLiteDatabase =
        context.openOrCreateDatabase(BD_SERIES_MANAGER, MODE_PRIVATE, null)

    init {
        try {
            seriesBD.execSQL(CRIAR_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_ROMANCE_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_AVENTURA_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_TERROR_TABELA_GENERO_STMT)
            seriesBD.execSQL(CRIAR_TABELA_SERIE_STMT)
            seriesBD.execSQL(CRIAR_TABELA_TEMPORADA_STMT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }
}