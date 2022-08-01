package com.hoho.telsimsender.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response

/**
 * Send response
 *
 * If ok is true, result is returned.
 * and if false, errorCode, description are returned.
 */
@Serializable
data class SendResponse(

    val ok: Boolean,

    val result: Result? = null,

    @SerialName("error_code")
    val errorCode: Int? = null,

    val description: String? = null
) {
    companion object {

        @JvmStatic
        fun from(response: Response<SendResponse>): SendResponse =
            if (response.isSuccessful) {
                response.body()!!
            } else {
                try {
                    Json.decodeFromString(response.errorBody()!!.use { it.string() })
                } catch (e: Exception) {
                    SendResponse(false, null, 0, "Error parsing failed")
                }
            }
    }
}