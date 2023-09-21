package ru.mobileup.template.core.widget.tabs

import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import ru.mobileup.template.core.theme.custom.CustomTheme

@Composable
fun <T : BaseTabItem> Tabs(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    selectedTabIndex: Int,
    onClick: (T, Int) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        contentColor = CustomTheme.colors.text.caption.secondary.default(),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = CustomTheme.colors.border.active(),
                height = 3.dp
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                icon = {},
                text = {
                    if (tab.isVisible) {
                        Text(
                            text = tab.title.localized(),
                            style = CustomTheme.typography.title.boldNormal,
                            color = if (selectedTabIndex == index) {
                                CustomTheme.colors.text.primary.default()
                            } else {
                                CustomTheme.colors.text.caption.secondary.default()
                            }
                        )
                    }
                },
                selected = selectedTabIndex == index,
                enabled = tab.isVisible,
                onClick = {
                    onClick(tabs[index], index)
                }
            )
        }
    }
}