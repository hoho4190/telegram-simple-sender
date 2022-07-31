package com.hoho.telsimsender.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
)