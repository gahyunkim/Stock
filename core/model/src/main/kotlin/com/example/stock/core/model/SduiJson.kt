package com.example.stock.core.model

import kotlinx.serialization.json.Json

/** SDUI 스키마 JSON 파싱에 쓰는 공용 Json 설정. */
object SduiJson {
    val format = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }
}
