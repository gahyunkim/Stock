package com.example.stock.core.network

import com.example.stock.core.model.UiScreen

/** 서버로부터 SDUI 화면 스키마를 가져오는 창구. 실제 API 연동 전까지는 [FakeSduiRepository]를 쓴다. */
interface SduiRepository {
    suspend fun fetchScreen(screenId: String): UiScreen
}
