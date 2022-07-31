package com.hoho.telsimsender.dto

import kotlinx.serialization.Serializable

/**
 * API url info
 */
@Serializable
internal data class TelApiInfo(
    var api: String
)