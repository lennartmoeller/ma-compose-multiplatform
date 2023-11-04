package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Icon(
    @SerialName("name") val name: String,
    @SerialName("unicode") val unicode: String,
)
