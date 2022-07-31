package com.hoho.telsimsender.dto

import kotlinx.serialization.Serializable
import java.net.URLEncoder

/**
 * Telegram info
 */
@Serializable
data class TelegramInfo(
    val token: String
) {
    var chatIds: LinkedHashSet<Long> = linkedSetOf()

    constructor(token: String, chatId: Long) : this(token) {
        this.chatIds = linkedSetOf(chatId)
    }

    constructor(token: String, chatIds: LinkedHashSet<Long>) : this(token) {
        this.chatIds = chatIds
    }

    constructor(token: String, vararg chatIds: Long) : this(token) {
        this.chatIds = linkedSetOf(*chatIds.toTypedArray())
    }

    override fun toString(): String {
        return "TelegramInfo(token='$token', chatIds=$chatIds)"
    }

    fun getEncodingToken(): String = URLEncoder.encode(token, "UTF-8")
}