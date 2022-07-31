package com.hoho.telsimsender.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(

    @SerialName("message_id")
    val messageId: Long,

    val from: From,

    val chat: Chat,

    val date: Long,

    val text: String,
)