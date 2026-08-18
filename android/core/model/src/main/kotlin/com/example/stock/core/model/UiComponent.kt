package com.example.stock.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 서버가 내려주는 SDUI 스키마의 UI 노드 하나를 표현한다.
 * 새 컴포넌트 타입을 추가할 때는 이 sealed interface에 구현체를 추가하고
 * feature:sdui-renderer의 SduiRenderer에도 매핑을 추가해야 한다.
 */
@Serializable
sealed interface UiComponent

@Serializable
@SerialName("column")
data class UiColumn(
    val children: List<UiComponent>,
) : UiComponent

@Serializable
@SerialName("row")
data class UiRow(
    val children: List<UiComponent>,
) : UiComponent

@Serializable
@SerialName("text")
data class UiText(
    val value: String,
    val style: UiTextStyle = UiTextStyle.BODY,
) : UiComponent

@Serializable
enum class UiTextStyle { TITLE, BODY, CAPTION }

@Serializable
@SerialName("button")
data class UiButton(
    val label: String,
    val action: String? = null,
) : UiComponent

@Serializable
@SerialName("spacer")
data class UiSpacer(
    val size: Int = 8,
) : UiComponent

@Serializable
@SerialName("image")
data class UiImage(
    val url: String,
    val contentDescription: String? = null,
) : UiComponent

/** 리스트/그리드/캐러셀 컨테이너가 공통으로 쓰는 아이템 모델. UiComponent가 아니라 데이터 홀더다. */
@Serializable
data class UiListItem(
    val imageUrl: String? = null,
    val title: String,
    val subtitle: String? = null,
    val trailingText: String? = null,
    val trailingCaption: String? = null,
    val action: String? = null,
)

@Serializable
@SerialName("vertical_list")
data class UiVerticalList(
    val items: List<UiListItem>,
) : UiComponent

@Serializable
@SerialName("grid")
data class UiGrid(
    val items: List<UiListItem>,
    val columns: Int = 2,
) : UiComponent

@Serializable
@SerialName("carousel")
data class UiCarousel(
    val items: List<UiListItem>,
) : UiComponent
