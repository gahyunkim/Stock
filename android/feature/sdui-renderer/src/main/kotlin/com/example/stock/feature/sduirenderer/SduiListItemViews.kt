package com.example.stock.feature.sduirenderer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.stock.core.model.UiListItem

/** 세로 리스트(UiVerticalList)의 아이템 한 줄. */
@Composable
fun UiListItemRow(
    item: UiListItem,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = item.action != null) { item.action?.let(onAction) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (item.imageUrl != null) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier.size(40.dp),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
        ) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
            item.subtitle?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
        }

        if (item.trailingText != null || item.trailingCaption != null) {
            Column(horizontalAlignment = Alignment.End) {
                item.trailingText?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
                item.trailingCaption?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}

/** 그리드(UiGrid)/캐러셀(UiCarousel)에서 공용으로 쓰는 카드 형태 아이템. */
@Composable
fun UiListItemCard(
    item: UiListItem,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .clickable(enabled = item.action != null) { item.action?.let(onAction) },
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (item.imageUrl != null) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(64.dp),
                )
            }
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
            item.subtitle?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            item.trailingText?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            item.trailingCaption?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }
    }
}
