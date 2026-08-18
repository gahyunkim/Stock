package com.example.stock.backend.sdui

import com.example.stock.backend.stock.StockEntity

fun StockEntity.toUiListItem(): UiListItem = UiListItem(
    imageUrl = logoUrl,
    title = name,
    subtitle = symbol,
    trailingText = "%,d원".format(price),
    trailingCaption = "%+.1f%%".format(changePercent),
    action = "open:$symbol",
)
