package com.hoho.telsimsender

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.dto.TelegramInfo
import com.hoho.telsimsender.service.MessageService
import retrofit2.Call

/**
 * Telegram simple sender
 */
object TelegramSimpleSender {
    private val messageService = MessageService.instance
    private lateinit var telegramInfo: TelegramInfo

    /**
     * Setup TelegramSimpleSender.
     *
     * @param telegramInfo
     */
    @JvmStatic
    fun setup(telegramInfo: TelegramInfo) {
        this.telegramInfo = telegramInfo
    }

    /**
     * Send message.
     *
     * @param message
     * @param chatId default is telegramInfo.chatIds.first()
     */
    @JvmStatic
    @JvmOverloads
    fun sendMessage(
        message: String,
        chatId: Long? = telegramInfo.chatIds.first()
    ): Call<SendResponse> {
        checkSetup()

        if (chatId == null) {
            throw RuntimeException("chatId is null")
        }

        return messageService.sendMessage(telegramInfo.getEncodingToken(), chatId, message)
    }

    /**
     * Send message to all chatIds.
     *
     * @param message
     * @param chatIds default is telegramInfo.chatIds
     */
    @JvmStatic
    @JvmOverloads
    fun sendAllMessage(
        message: String,
        chatIds: LinkedHashSet<Long>? = telegramInfo.chatIds
    ): List<Call<SendResponse>> {
        checkSetup()

        if (chatIds == null) {
            throw RuntimeException("chatIds is null")
        } else if (chatIds.isEmpty()) {
            throw RuntimeException("chatIds is empty")
        }

        return chatIds.map {
            sendMessage(message, it)
        }
    }

    /**
     * Check setup.
     */
    private fun checkSetup(): Boolean =
        if (!this::telegramInfo.isInitialized) {
            throw RuntimeException("TelegramSimpleSender is not initialized.")
        } else {
            true
        }
}