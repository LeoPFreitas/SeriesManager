package com.example.projetoseriesmanager.repository.sqlite

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.projetoseriesmanager.R
import com.example.projetoseriesmanager.repository.TemporadaSqlite

class DatabaseBuilder(context: Context) {

    companion object {
        private val BD_SERIES_MANAGER = "series_manager"

        private val CRIAR_TABELA_GENERO_STMT =
            "CREATE TABLE IF NOT EXISTS ${GeneroSlite.TABLE_GENERO} (" +
                    "${GeneroSlite.COL_NOME} TEXT NOT NULL PRIMARY KEY);"

        private val INSERT_ROMANCE_TABELA_GENERO_STMT =
            "INSERT INTO genero (${GeneroSlite.COL_NOME}) VALUES('Romance');"
        private val INSERT_AVENTURA_TABELA_GENERO_STMT =
            "INSERT INTO genero (${GeneroSlite.COL_NOME}) VALUES('Aventura');"
        private val INSERT_TERROR_TABELA_GENERO_STMT =
            "INSERT INTO genero (${GeneroSlite.COL_NOME}) VALUES('Terror');"

        private val CRIAR_TABELA_SERIE_STMT =
            "CREATE TABLE IF NOT EXISTS ${SerieSqlite.TABLE_SERIE} (" +
                    "${SerieSqlite.COL_NOME} TEXT NOT NULL PRIMARY KEY, " +
                    "${SerieSqlite.COL_ANO_LANCAMENTO} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_EMISSORA} TEXT NOT NULL, " +
                    "${SerieSqlite.COL_GENERO} TEXT NOT NULL, " +
                    "FOREIGN KEY(${SerieSqlite.COL_GENERO}) REFERENCES ${GeneroSlite.TABLE_GENERO}(${GeneroSlite.COL_NOME})" +
                    ");"

        private val CRIAR_TABELA_TEMPORADA_STMT =
            "CREATE TABLE IF NOT EXISTS ${TemporadaSqlite.TABLE_TEMPORADA} (" +
                    "${TemporadaSqlite.COL_NUMERO_SEQUENCIAL} INTEGER NOT NULL PRIMARY KEY, " +
                    "${TemporadaSqlite.COL_ANO_LANCAMENTO} TEXT NOT NULL, " +
                    "${TemporadaSqlite.COL_NOME_SERIE} TEXT NOT NULL, " +
                    "FOREIGN KEY(${TemporadaSqlite.COL_NOME_SERIE}) REFERENCES ${SerieSqlite.TABLE_SERIE}(${SerieSqlite.COL_NOME}) ON DELETE CASCADE" +
                    ");"

        private val CRIAR_TABELA_EPISODIO_STMT =
            "CREATE TABLE IF NOT EXISTS ${EpisodioSqlite.TABLE_EPISODIO} (" +
                    "${EpisodioSqlite.COL_NUMERO_SEQUENCIAL} INTEGER NOT NULL PRIMARY KEY, " +
                    "${EpisodioSqlite.COL_NOME} TEXT NOT NULL, " +
                    "${EpisodioSqlite.COL_DURACAO} INTEGER NOT NULL, " +
                    "${EpisodioSqlite.COL_VISTO} INTEGER NOT NULL DEFAULT 0, " +
                    "${EpisodioSqlite.COL_TEMPORADA} INTEGER NOT NULL, " +
                    "FOREIGN KEY(${EpisodioSqlite.COL_TEMPORADA}) REFERENCES ${TemporadaSqlite.TABLE_TEMPORADA}(${TemporadaSqlite.COL_NUMERO_SEQUENCIAL}) ON DELETE CASCADE " +
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
            seriesBD.execSQL(CRIAR_TABELA_EPISODIO_STMT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }
}