package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val numeroSequencial: Int = -1,
    val anoLancamento: String = "",
    val nomeSerie: String = ""
) : Parcelable
