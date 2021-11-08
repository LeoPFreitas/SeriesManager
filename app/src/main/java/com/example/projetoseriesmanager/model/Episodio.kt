package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodio(
    val numeroSequencial: Int = -1,
    val nome: String = "",
    val duracao: Int = -1,
    val visto: Boolean = false,
    val temporada: Int = -1
) : Parcelable
