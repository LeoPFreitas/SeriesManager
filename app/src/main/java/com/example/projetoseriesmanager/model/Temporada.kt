package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val id: Int,
    val numeroSequencial: Int,
    val anoLancamento: Int,
    val nomeSerie: String
) : Parcelable
