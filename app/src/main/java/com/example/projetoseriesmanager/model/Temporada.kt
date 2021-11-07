package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val numeroSequencial: Int,
    val anoLancamento: String,
    val nomeSerie: String
) : Parcelable
