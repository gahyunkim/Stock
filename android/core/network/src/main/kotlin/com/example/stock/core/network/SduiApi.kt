package com.example.stock.core.network

import com.example.stock.core.model.UiScreen
import retrofit2.http.GET
import retrofit2.http.Path

interface SduiApi {
    @GET("screens/{screenId}")
    suspend fun getScreen(@Path("screenId") screenId: String): UiScreen
}
