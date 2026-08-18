package com.example.stock.core.model

import kotlinx.serialization.Serializable

/** 서버가 내려주는 화면 하나(제목 + 루트 컴포넌트 트리)를 표현한다. */
@Serializable
data class UiScreen(
    val title: String,
    val content: UiComponent,
)
