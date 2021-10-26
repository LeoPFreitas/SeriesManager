package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodio(
    val id: Int,
    val numeroSequencial: Int,
    val nome: String,
    val duracao: Int,
    val foiVisto: Boolean,
    val temporadaId: Int
) : Parcelable
