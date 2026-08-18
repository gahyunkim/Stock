package com.example.stock.backend.sdui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/screens")
class SduiScreenController {

    @GetMapping("/{screenId}")
    fun getScreen(
        @PathVariable screenId: String,
    ): UiScreen = when (screenId) {
        "home" -> homeScreen()
        else -> homeScreen()
    }

    private fun homeScreen(): UiScreen = UiScreen(
        title = "Stock",
        content = UiColumn(
            children = listOf(
                UiText(value = "실시간 시세를 서버가 그려줍니다", style = UiTextStyle.TITLE),
                UiSpacer(size = 12),
                UiText(value = "이 화면은 서버가 내려준 JSON으로 렌더링됩니다."),
                UiSpacer(size = 16),
                UiButton(label = "새로고침", action = "refresh"),
            ),
        ),
    )
}
