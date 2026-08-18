package com.example.stock.core.network

import com.example.stock.core.model.SduiJson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import retrofit2.create

object SduiApiFactory {
    /** 안드로이드 에뮬레이터에서 호스트 머신의 localhost(backend 8080 포트)로 접근하는 전용 주소. */
    const val EMULATOR_BASE_URL = "http://10.0.2.2:8080/"

    fun create(baseUrl: String = EMULATOR_BASE_URL): SduiApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC },
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                SduiJson.format.asConverterFactory("application/json".toMediaType()),
            )
            .build()

        return retrofit.create()
    }
}
