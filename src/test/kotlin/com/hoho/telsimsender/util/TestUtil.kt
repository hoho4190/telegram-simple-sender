package com.hoho.telsimsender.util

import com.hoho.telsimsender.dto.SendResponse
import com.hoho.telsimsender.dto.TelegramInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Response

internal object TestUtil {

    private val json: Json = Json { prettyPrint = true }

    /**
     * Telegram info resource file name.
     */
    private const val TELEGRAM_INFO_RES_FILE_NAME = "telegram-info.json"

    /**
     * Get TelegramInfo.
     */
    @JvmStatic
    fun getTelegramInfo(): TelegramInfo =
        json.decodeFromString(FileUtil.readResource(TELEGRAM_INFO_RES_FILE_NAME))

    /**
     * Convert Response to pretty String.
     *
     * @param response
     */
    inline fun <reified T> convertResToPrettyStr(response: Response<T>): String {
        return if (response.isSuccessful) {
            json.encodeToString(response.body())
        } else {
            val sendResponse = json.decodeFromString<SendResponse>(response.errorBody()!!.string())
            json.encodeToString(sendResponse)
        }
    }
}