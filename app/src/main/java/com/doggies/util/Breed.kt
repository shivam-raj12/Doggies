package com.doggies.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    @SerialName("message") val breedList: Map<String, List<String>>,
    @SerialName("status") val status: String
)