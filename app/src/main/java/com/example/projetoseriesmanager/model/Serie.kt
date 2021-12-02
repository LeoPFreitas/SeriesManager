package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Serie(
    val nome: String = "",
    var anoLancamento: String = "",
    var emissora: String = "",
    var genero: String = "",
    val temporadas: List<Temporada> = emptyList()
) : Parcelable
