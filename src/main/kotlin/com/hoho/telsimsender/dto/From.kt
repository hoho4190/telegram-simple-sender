package com.hoho.telsimsender.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class From(

    val id: Long,

    @SerialName("is_bot")
    val isBot: Boolean,

    @SerialName("first_name")
    val firstName: String,

    val username: String,
)