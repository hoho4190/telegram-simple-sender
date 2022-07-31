package com.hoho.telsimsender

import com.hoho.telsimsender.dto.TelegramInfo
import com.hoho.telsimsender.util.TestUtil
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable

@Disabled
@DisplayName("Kotlin E2E Test - TelegramSimpleSender")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TelegramSimpleSenderKotlinTest {

    private lateinit var telegramInfo: TelegramInfo

    @BeforeAll
    fun beforeAll() {
        telegramInfo = TestUtil.getTelegramInfo()
    }

    @Disabled
    @Test
    @DisplayName("sendMessage Test")
    fun sendMessageTest() {
        // Given
        TelegramSimpleSender.setup(telegramInfo)
        val message = "Kotlin TelegramSimpleSender sendMessage test"

        // When
        val call = TelegramSimpleSender.sendMessage(message)
        val response = call.execute()

        // Then
        println("url: ${response.raw().request().url()}")
        println(TestUtil.convertResToPrettyStr(response))
        Assertions.assertTrue(response.isSuccessful)
    }

    @Disabled
    @Test
    @DisplayName("sendAllMessage Test")
    fun sendAllMessageTest() {
        // Given
        TelegramSimpleSender.setup(telegramInfo)
        val message = "Kotlin TelegramSimpleSender sendAllMessage test"

        // When
        val callList = TelegramSimpleSender.sendAllMessage(message)
        val responseList = callList.map { it.execute() }

        // Then
        Assertions.assertAll(
            responseList.map { response ->
                println("url: ${response.raw().request().url()}")
                println(TestUtil.convertResToPrettyStr(response))
                Executable { Assertions.assertTrue(response.isSuccessful) }
            }
        )
    }
}