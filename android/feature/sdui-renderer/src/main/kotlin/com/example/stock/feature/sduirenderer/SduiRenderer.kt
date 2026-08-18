package com.example.stock.feature.sduirenderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.stock.core.model.UiButton
import com.example.stock.core.model.UiCarousel
import com.example.stock.core.model.UiColumn
import com.example.stock.core.model.UiComponent
import com.example.stock.core.model.UiGrid
import com.example.stock.core.model.UiImage
import com.example.stock.core.model.UiRow
import com.example.stock.core.model.UiSpacer
import com.example.stock.core.model.UiText
import com.example.stock.core.model.UiTextStyle
import com.example.stock.core.model.UiVerticalList

/**
 * SDUI 스키마 노드([UiComponent])를 실제 Compose UI로 그리는 렌더러 엔진.
 * 새 컴포넌트 타입은 core:model에 sealed 구현체를 추가하고 여기 when 분기를 채워야 한다
 * (when이 exhaustive라 빠뜨리면 컴파일 에러로 바로 드러난다).
 */
@Composable
fun SduiRenderer(
    component: UiComponent,
    onAction: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    when (component) {
        is UiColumn -> Column(modifier = modifier) {
            component.children.forEach { child -> SduiRenderer(child, onAction) }
        }

        is UiRow -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            component.children.forEach { child -> SduiRenderer(child, onAction) }
        }

        is UiText -> Text(
            text = component.value,
            style = when (component.style) {
                UiTextStyle.TITLE -> MaterialTheme.typography.titleLarge
                UiTextStyle.BODY -> MaterialTheme.typography.bodyMedium
                UiTextStyle.CAPTION -> MaterialTheme.typography.bodySmall
            },
            modifier = modifier,
        )

        is UiButton -> Button(
            onClick = { component.action?.let(onAction) },
            modifier = modifier,
        ) {
            Text(component.label)
        }

        is UiSpacer -> Spacer(
            modifier = modifier
                .height(component.size.dp)
                .width(component.size.dp),
        )

        is UiImage -> AsyncImage(
            model = component.url,
            contentDescription = component.contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )

        is UiVerticalList -> LazyColumn(modifier = modifier) {
            items(component.items) { item -> UiListItemRow(item, onAction) }
        }

        is UiGrid -> LazyVerticalGrid(
            columns = GridCells.Fixed(component.columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,
        ) {
            items(component.items) { item -> UiListItemCard(item, onAction) }
        }

        is UiCarousel -> LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,
        ) {
            items(component.items) { item -> UiListItemCard(item, onAction) }
        }
    }
}
