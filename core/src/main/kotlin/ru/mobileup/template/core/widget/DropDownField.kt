package ru.mobileup.template.core.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.template.core.R
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.theme.custom.CustomTheme

@Composable
fun DropDownField(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onIndexUpdated: (Int) -> Unit,
    selectedItem: (selectedIndex: Int) -> String = { items[selectedIndex] },
    header: String? = null,
    isEnabled: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(false) }
    var rowSize by remember { mutableStateOf(Size.Zero) }
    val shape = RoundedCornerShape(16.dp)

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { isEnabled }
            .onEach {
                if (!isEnabled) isExpanded = false
            }
            .launchIn(this)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, color = CustomTheme.colors.border.primary(), shape = shape)
            .height(56.dp)
            .clip(shape)
            .background(color = CustomTheme.colors.background.input(), shape = shape)
            .clickable(
                enabled = isEnabled,
                onClick = { isExpanded = true }
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = if (selectedIndex >= 0) header ?: "" else "",
            style = CustomTheme.typography.caption.regularUltraSmall,
            color = CustomTheme.colors.text.input(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .onGloballyPositioned {
                    rowSize = it.size.toSize()
                }
        ) {
            Text(
                text = if (selectedIndex >= 0) {
                    selectedItem(selectedIndex)
                } else {
                    header ?: ""
                },
                style = CustomTheme.typography.body.regularSmall,
                color = if (selectedIndex >= 0 && isEnabled) {
                    CustomTheme.colors.text.primary.default()
                } else {
                    CustomTheme.colors.text.input()
                },
                modifier = Modifier.weight(1f)
            )
            Icon(
                painterResource(R.drawable.ic_16_chevron_down),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .heightIn(
                    min = 64.dp,
                    max = LocalConfiguration.current.screenHeightDp.dp - 120.dp
                )
                .width(
                    with(LocalDensity.current) { rowSize.width.toDp() }
                )
                .background(CustomTheme.colors.background.input())
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = s,
                            style = CustomTheme.typography.body.regularSmall,
                            color = CustomTheme.colors.text.primary.default()
                        )
                    },
                    onClick = {
                        onIndexUpdated(index)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DropDownFieldPreview() {
    AppTheme {
        DropDownField(
            selectedIndex = -1,
            items = listOf("item 1", "item 2", "item 3"),
            header = "Пол",
            onIndexUpdated = { }
        )
    }
}
