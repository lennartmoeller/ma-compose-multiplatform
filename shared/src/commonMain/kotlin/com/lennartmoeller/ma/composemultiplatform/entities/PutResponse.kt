package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutResponse(
    @SerialName("id") var id: Int,
)
