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

    private fun listsScreen(): UiScreen = UiScreen(
        title = "관심 종목",
        content = UiColumn(
            children = listOf(
                UiText(value = "세로 리스트", style = UiTextStyle.TITLE),
                UiVerticalList(items = watchlistItems()),
                UiSpacer(size = 16),
                UiText(value = "그리드", style = UiTextStyle.TITLE),
                UiGrid(items = watchlistItems(), columns = 2),
                UiSpacer(size = 16),
                UiText(value = "캐러셀", style = UiTextStyle.TITLE),
                UiCarousel(items = watchlistItems()),
            ),
        ),
    )

    private fun watchlistItems(): List<UiListItem> = listOf(
        UiListItem(
            imageUrl = "https://placehold.co/64x64/1976D2/FFFFFF.png?text=SS",
            title = "삼성전자",
            subtitle = "005930",
            trailingText = "71,200원",
            trailingCaption = "+1.2%",
            action = "open:005930",
        ),
        UiListItem(
            imageUrl = "https://placehold.co/64x64/FFCC00/000000.png?text=K",
            title = "카카오",
            subtitle = "035720",
            trailingText = "42,300원",
            trailingCaption = "-0.8%",
            action = "open:035720",
        ),
        UiListItem(
            imageUrl = "https://placehold.co/64x64/03C75A/FFFFFF.png?text=N",
            title = "NAVER",
            subtitle = "035420",
            trailingText = "210,500원",
            trailingCaption = "+2.1%",
            action = "open:035420",
        ),
    )
}
