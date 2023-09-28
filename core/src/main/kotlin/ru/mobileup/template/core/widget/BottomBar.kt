package ru.mobileup.template.core.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mobileup.template.core.R
import ru.mobileup.template.core.theme.custom.CustomTheme

enum class Page {
    Main, Catalog, Favorites, Orders, Profile
}

@Composable
fun BottomBar(
    currentPage: Page,
    elevated: Boolean,
    onPageSelected: (Page) -> Unit,
) {
    NavigationBar(
        containerColor = CustomTheme.colors.background.screen(),
        tonalElevation = if (elevated) 16.dp else 0.dp
    ) {
        NavigationItem(
            iconRes = R.drawable.ic_24_home,
            labelRes = R.string.nav_bar_item_1,
            isSelected = currentPage == Page.Main,
            onClick = { onPageSelected(Page.Main) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_flower,
            labelRes = R.string.nav_bar_item_2,
            isSelected = currentPage == Page.Catalog,
            onClick = { onPageSelected(Page.Catalog) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_favourites,
            labelRes = R.string.nav_bar_item_3,
            isSelected = currentPage == Page.Favorites,
            onClick = { onPageSelected(Page.Favorites) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_orders,
            labelRes = R.string.nav_bar_item_4,
            isSelected = currentPage == Page.Orders,
            onClick = { onPageSelected(Page.Orders) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_profile,
            labelRes = R.string.nav_bar_item_5,
            isSelected = currentPage == Page.Profile,
            onClick = { onPageSelected(Page.Profile) }
        )
    }
}

@Composable
fun RowScope.NavigationItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = CustomTheme.colors.icon.primary.default(),
            selectedTextColor = CustomTheme.colors.icon.primary.default(),
            unselectedIconColor = CustomTheme.colors.icon.primary.disabled(),
            unselectedTextColor = CustomTheme.colors.icon.primary.disabled(),
        ),
        icon = {
            NavigationIcon(
                iconRes = iconRes,
                isSelected = isSelected,
            )
        },
        label = {
            BoxWithConstraints {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(unbounded = true)
                        .requiredWidth(maxWidth + 24.dp),
                    text = stringResource(labelRes),
                    maxLines = 1,
                    style = CustomTheme.typography.button.mediumNormal,
                    color = if (isSelected) {
                        CustomTheme.colors.text.primary.default()
                    } else {
                        CustomTheme.colors.text.caption.secondary.default()
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
            }
        },
    )
}

@Composable
private fun NavigationIcon(
    @DrawableRes iconRes: Int,
    isSelected: Boolean,
) {
    Icon(
        modifier = Modifier.padding(4.dp),
        painter = painterResource(iconRes),
        tint = if (isSelected) {
            CustomTheme.colors.icon.primary.default()
        } else {
            CustomTheme.colors.icon.secondary.default()
        },
        contentDescription = null
    )
}