package com.hoho.telsimsender.dto

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.URLEncoder

@DisplayName("TelegramInfo Test")
internal class TelegramInfoTest {

    private val token = "token"

    @Test
    @DisplayName("Constructor Test - token")
    fun constructorTest_token() {
        // When
        val telegramInfo = TelegramInfo(token)

        // Then
        Assertions.assertEquals(token, telegramInfo.token)
        Assertions.assertEquals(URLEncoder.encode(token, "UTF-8"), telegramInfo.getEncodingToken())
        println(telegramInfo)
    }

    @Test
    @DisplayName("Constructor Test - Long")
    fun constructorTest_Long() {
        // Given
        val chatId = 1L

        // When
        val telegramInfo = TelegramInfo(token, chatId)

        // Then
        Assertions.assertEquals(1, telegramInfo.chatIds.size)
        Assertions.assertEquals(chatId, telegramInfo.chatIds.first())
        println(telegramInfo)
    }

    @Test
    @DisplayName("Constructor Test - LinkedHashSet")
    fun constructorTest_LinkedHashSet() {
        // Given
        val linkedHashSet: LinkedHashSet<Long> = linkedSetOf(1L, 1L, 2L)

        // When
        val telegramInfo = TelegramInfo(token, linkedHashSet)

        // Then
        Assertions.assertEquals(2, linkedHashSet.size)
        Assertions.assertEquals(linkedHashSet.size, telegramInfo.chatIds.size)
        println(telegramInfo)
    }

    @Test
    @DisplayName("Constructor Test - vararg")
    fun constructorTest_vararg() {
        // Given
        val vararg = longArrayOf(1L, 1L, 2L)

        // When
        val telegramInfo = TelegramInfo(token, *vararg)

        // Then
        Assertions.assertEquals(3, vararg.size)
        Assertions.assertEquals(2, telegramInfo.chatIds.size)
        println(telegramInfo)
    }
}