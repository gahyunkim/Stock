package com.example.stock.core.network

import com.example.stock.core.model.SduiJson
import com.example.stock.core.model.UiScreen

/** 실제 백엔드가 연결되기 전까지, 로컬 JSON으로 SDUI 파이프라인을 검증하기 위한 임시 구현. */
class FakeSduiRepository : SduiRepository {
    override suspend fun fetchScreen(screenId: String): UiScreen =
        SduiJson.format.decodeFromString(UiScreen.serializer(), SAMPLE_HOME_SCREEN_JSON)

    private companion object {
        const val SAMPLE_HOME_SCREEN_JSON = """
        {
          "title": "Stock",
          "content": {
            "type": "column",
            "children": [
              { "type": "text", "value": "실시간 시세를 서버가 그려줍니다", "style": "TITLE" },
              { "type": "spacer", "size": 12 },
              { "type": "text", "value": "이 화면은 서버가 내려준 JSON으로 렌더링됩니다.", "style": "BODY" },
              { "type": "spacer", "size": 16 },
              { "type": "button", "label": "새로고침", "action": "refresh" }
            ]
          }
        }
        """
    }
}
