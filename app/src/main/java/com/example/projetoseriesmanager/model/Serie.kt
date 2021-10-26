package com.example.projetoseriesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Serie(
    val nome: String,
    val anoLancamento: String,
    val emissora: String,
    val genero: String
) : Parcelable
