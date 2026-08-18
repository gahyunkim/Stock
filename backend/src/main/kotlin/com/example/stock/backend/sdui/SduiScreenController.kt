package com.example.stock.backend.sdui

import com.example.stock.backend.stock.StockRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/screens")
class SduiScreenController(
    private val stockRepository: StockRepository,
) {

    @GetMapping("/{screenId}")
    fun getScreen(
        @PathVariable screenId: String,
    ): UiScreen = when (screenId) {
        "home" -> homeScreen()
        "lists" -> listsScreen()
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

    private fun listsScreen(): UiScreen {
        val items = stockRepository.findAll().map { it.toUiListItem() }
        return UiScreen(
            title = "관심 종목",
            content = UiColumn(
                children = listOf(
                    UiText(value = "세로 리스트", style = UiTextStyle.TITLE),
                    UiVerticalList(items = items),
                    UiSpacer(size = 16),
                    UiText(value = "그리드", style = UiTextStyle.TITLE),
                    UiGrid(items = items, columns = 2),
                    UiSpacer(size = 16),
                    UiText(value = "캐러셀", style = UiTextStyle.TITLE),
                    UiCarousel(items = items),
                ),
            ),
        )
    }
}
