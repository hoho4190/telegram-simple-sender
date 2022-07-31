package com.hoho.telsimsender

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.dto.TelegramInfo
import com.hoho.telsimsender.service.MessageService
import retrofit2.Call

/**
 * Telegram sender
 */
class TelegramSender(
    private val telegramInfo: TelegramInfo
) {

    /**
     * Send message.
     *
     * @param message
     * @param chatId default is telegramInfo.chatIds.first()
     */
    @JvmOverloads
    fun sendMessage(
        message: String,
        chatId: Long? = telegramInfo.chatIds.first()
    ): Call<SendResponse> {
        if (chatId == null) {
            throw RuntimeException("chatId is null")
        }

        return MessageService.instance.sendMessage(telegramInfo.getEncodingToken(), chatId, message)
    }

    /**
     * Send message to all chatIds.
     *
     * @param message
     * @param chatIds default is telegramInfo.chatIds
     */
    @JvmOverloads
    fun sendAllMessage(
        message: String,
        chatIds: LinkedHashSet<Long>? = telegramInfo.chatIds
    ): List<Call<SendResponse>> {
        if (chatIds == null) {
            throw RuntimeException("chatIds is null")
        } else if (chatIds.isEmpty()) {
            throw RuntimeException("chatIds is empty")
        }

        return chatIds.map {
            sendMessage(message, it)
        }
    }
}