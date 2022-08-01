package com.hoho.telsimsender

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.dto.TelegramInfo
import com.hoho.telsimsender.util.TestUtil
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Disabled
@DisplayName("Kotlin E2E Test - TelegramSender")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TelegramSenderKotlinTest {

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
        val telegramSender = TelegramSender(telegramInfo)
        val message = "Kotlin TelegramSender sendMessage test"

        // When
        val call = telegramSender.sendMessage(message)
        val response = call.execute()

        // Then
        println("url: ${response.raw().request().url()}")
        val sendResponse = SendResponse.from(response)
        println(TestUtil.convertSendResToPrettyStr(sendResponse))
        Assertions.assertTrue(response.isSuccessful)
    }

    @Disabled
    @Test
    @DisplayName("sendMessage Test - async")
    fun sendMessageTest_async() {
        // Given
        val telegramSender = TelegramSender(telegramInfo)
        val message = "Kotlin TelegramSender sendMessage test - async"

        // When
        val call = telegramSender.sendMessage(message)
        call.enqueue(object : Callback<SendResponse> {

            // Then
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                println("url: ${response.raw().request().url()}")
                val sendResponse = SendResponse.from(response)
                println(TestUtil.convertSendResToPrettyStr(sendResponse))
                Assertions.assertTrue(response.isSuccessful)
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                println(t.message)
            }
        })

        Thread.sleep(2000L)
    }

    @Disabled
    @Test
    @DisplayName("sendAllMessage Test")
    fun sendAllMessageTest() {
        // Given
        val telegramSender = TelegramSender(telegramInfo)
        val message = "Kotlin TelegramSender sendAllMessage test"

        // When
        val callList = telegramSender.sendAllMessage(message)
        val responseList = callList.map { it.execute() }

        // Then
        Assertions.assertAll(
            responseList.map { response ->
                println("url: ${response.raw().request().url()}")
                val sendResponse = SendResponse.from(response)
                println(TestUtil.convertSendResToPrettyStr(sendResponse))
                Executable { Assertions.assertTrue(response.isSuccessful) }
            }
        )
    }
}