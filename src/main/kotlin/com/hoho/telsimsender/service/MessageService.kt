package com.hoho.telsimsender.service

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.util.RetrofitUtil
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MessageService {
    companion object {
        @JvmStatic
        @OptIn(ExperimentalSerializationApi::class)
        val instance: MessageService by lazy {
            Retrofit.Builder()
                .baseUrl(RetrofitUtil.telApiInfo.api)
                .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
                .build()
                .create()
        }
    }

    @GET("bot{token}/sendMessage")
    fun sendMessage(
        @Path("token", encoded = true) token: String,
        @Query("chat_id") chatId: Long,
        @Query("text") text: String,
    ): Call<SendResponse>
}