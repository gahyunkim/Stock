package com.example.stock.core.network

import com.example.stock.core.model.UiScreen

/** 실제 backend 서버(`GET /screens/{screenId}`)를 호출하는 구현체. */
class RemoteSduiRepository(
    private val api: SduiApi = SduiApiFactory.create(),
) : SduiRepository {
    override suspend fun fetchScreen(screenId: String): UiScreen = api.getScreen(screenId)
}
