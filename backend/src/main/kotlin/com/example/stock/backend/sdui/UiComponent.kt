package com.example.stock.backend.sdui

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * android의 core:model.UiComponent와 동일한 JSON 모양(type 판별 필드 포함)을 내려주는 SDUI 스키마.
 * 새 컴포넌트 타입을 추가할 때는 여기와 android core:model 양쪽에 함께 추가해야 한다.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = UiColumn::class, name = "column"),
    JsonSubTypes.Type(value = UiRow::class, name = "row"),
    JsonSubTypes.Type(value = UiText::class, name = "text"),
    JsonSubTypes.Type(value = UiButton::class, name = "button"),
    JsonSubTypes.Type(value = UiSpacer::class, name = "spacer"),
    JsonSubTypes.Type(value = UiImage::class, name = "image"),
    JsonSubTypes.Type(value = UiVerticalList::class, name = "vertical_list"),
    JsonSubTypes.Type(value = UiGrid::class, name = "grid"),
    JsonSubTypes.Type(value = UiCarousel::class, name = "carousel"),
)
sealed interface UiComponent

data class UiColumn(
    val children: List<UiComponent>,
) : UiComponent

data class UiRow(
    val children: List<UiComponent>,
) : UiComponent

data class UiText(
    val value: String,
    val style: UiTextStyle = UiTextStyle.BODY,
) : UiComponent

enum class UiTextStyle { TITLE, BODY, CAPTION }

data class UiButton(
    val label: String,
    val action: String? = null,
) : UiComponent

data class UiSpacer(
    val size: Int = 8,
) : UiComponent

data class UiImage(
    val url: String,
    val contentDescription: String? = null,
) : UiComponent

/** 리스트/그리드/캐러셀 컨테이너가 공통으로 쓰는 아이템 모델. */
data class UiListItem(
    val imageUrl: String? = null,
    val title: String,
    val subtitle: String? = null,
    val trailingText: String? = null,
    val trailingCaption: String? = null,
    val action: String? = null,
)

data class UiVerticalList(
    val items: List<UiListItem>,
) : UiComponent

data class UiGrid(
    val items: List<UiListItem>,
    val columns: Int = 2,
) : UiComponent

data class UiCarousel(
    val items: List<UiListItem>,
) : UiComponent

data class UiScreen(
    val title: String,
    val content: UiComponent,
)
