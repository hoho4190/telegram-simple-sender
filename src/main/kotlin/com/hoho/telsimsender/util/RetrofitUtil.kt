package com.hoho.telsimsender.util

import com.hoho.telsimsender.dto.TelApiInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal object RetrofitUtil {

    private const val TEL_API_INFO_RES_FILE_NAME = "telegram-api-info.json"
    val telApiInfo = parseTelApiInfo()

    /**
     * Parse TelApiInfo
     */
    private fun parseTelApiInfo(): TelApiInfo {
        val telApiInfo: TelApiInfo =
            Json.decodeFromString(FileUtil.readResource(TEL_API_INFO_RES_FILE_NAME))
        val api = telApiInfo.api

        if (!api.endsWith("/")) {
            telApiInfo.api += "/"
        }

        return telApiInfo
    }
}