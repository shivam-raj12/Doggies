package com.doggies.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    @SerialName("message") val dogList: List<String>,
    @SerialName("status") val status: String
)