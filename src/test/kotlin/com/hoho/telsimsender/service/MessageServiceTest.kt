package com.hoho.telsimsender.service

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.util.FileUtil
import com.hoho.telsimsender.util.TestUtil
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import retrofit2.*

@DisplayName("Unit Test - Message Service")
internal class MessageServiceTest {

    private val mockDataPath = "mock-data/"

    private lateinit var messageService: MessageService
    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    @OptIn(ExperimentalSerializationApi::class)
    fun beforeEach() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val mockUrl = mockWebServer.url("/")

        messageService = Retrofit.Builder()
            .baseUrl(mockUrl)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create()
    }

    @AfterEach
    fun afterEach() {
        mockWebServer.shutdown()
    }

    @Test
    @DisplayName("sendMessage - Success")
    fun sendMessageSuccessTest() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-success.json")
        mockWebServer.enqueue(MockResponse().setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        val response = call.execute()

        // Then
        Assertions.assertTrue(response.isSuccessful)
        println(TestUtil.convertResToPrettyStr(response))
    }

    @Test
    @DisplayName("sendMessage - Success - async")
    fun sendMessageSuccessTest_async() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-success.json")
        mockWebServer.enqueue(MockResponse().setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        call.enqueue(object : Callback<SendResponse> {

            // Then
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                Assertions.assertTrue(response.isSuccessful)
                println(TestUtil.convertResToPrettyStr(response))
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                println(t.message)
            }
        })

        Thread.sleep(500L)
    }

    @Test
    @DisplayName("sendMessage - Failure - wrong token")
    fun sendMessageFailureTest_wrong_token() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-failure-wrong_token.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(404).setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        val response = call.execute()

        // Then
        Assertions.assertFalse(response.isSuccessful)
        println(TestUtil.convertResToPrettyStr(response))
    }

    @Test
    @DisplayName("sendMessage - Failure - wrong token - async")
    fun sendMessageFailureTest_wrong_token_async() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-failure-wrong_token.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(404).setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        call.enqueue(object : Callback<SendResponse> {

            // Then
            override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
                Assertions.assertFalse(response.isSuccessful)
                println(TestUtil.convertResToPrettyStr(response))
            }

            override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                println(t.message)
            }
        })

        Thread.sleep(500L)
    }

    @Test
    @DisplayName("sendMessage - Failure - wrong chatId")
    fun sendMessageFailureTest_wrong_chat_id() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-failure-wrong_chat_id.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        val response = call.execute()

        // Then
        Assertions.assertFalse(response.isSuccessful)
        println(TestUtil.convertResToPrettyStr(response))
    }

    @Test
    @DisplayName("sendMessage - Failure - empty chatId")
    fun sendMessageFailureTest_empty_chat_id() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-failure-empty_chat_id.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        val response = call.execute()

        // Then
        Assertions.assertFalse(response.isSuccessful)
        println(TestUtil.convertResToPrettyStr(response))
    }

    @Test
    @DisplayName("sendMessage - Failure - empty text")
    fun sendMessageFailureTest_empty_text() {
        // Given
        val mockBody = FileUtil.readResource("$mockDataPath/sendMessage-failure-empty_text.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(mockBody))

        val token = "token"
        val chatId = 987654321L
        val text = "hello"

        // When
        val call = messageService.sendMessage(token, chatId, text)
        val response = call.execute()

        // Then
        Assertions.assertFalse(response.isSuccessful)
        println(TestUtil.convertResToPrettyStr(response))
    }
}