package ru.mobileup.template.core.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.template.core.R
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.theme.custom.CustomTheme
import ru.mobileup.template.core.utils.dispatchOnBackPressed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlToolbar(
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null,
    title: String? = null,
    singleLine: Boolean = false,
    navigationIcon: @Composable (() -> Unit)? = { BackButton() },
    actionIcon: @Composable (() -> Unit)? = null,
    overlay: @Composable (BoxScope.() -> Unit)? = null,
    backgroundColor: Color = CustomTheme.colors.background.screen(),
    contentColor: Color = CustomTheme.colors.text.primary.default(),
) = Box(modifier = modifier) {
    TopAppBar(
        title = {
            /*
             * Нужно для изменения отступа заголовка от navigationIcon
             * который имеет свой TitleIconModifier шириной в 72.dp
             * */
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(navigationIcon?.let { -16.dp } ?: 0.dp)) {
                title?.let {
                    Text(
                        text = it,
                        overflow = if (singleLine) TextOverflow.Ellipsis else TextOverflow.Clip,
                        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                        style = CustomTheme.typography.title.boldLarge,
                    )
                } ?: run {
                    content?.invoke()
                }
            }
        },
        actions = { actionIcon?.invoke() },
        navigationIcon = { navigationIcon?.invoke() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor,
            titleContentColor = contentColor,
        )
    )

    overlay?.invoke(this)
}

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    NavigationButton(iconRes = R.drawable.ic_24_back, modifier)
}

@Composable
fun CloseButton(modifier: Modifier = Modifier) {
    NavigationButton(iconRes = R.drawable.ic_24_close, modifier)
}

@Composable
fun NavigationButton(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    IconButton(
        onClick = { dispatchOnBackPressed(context) },
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(iconRes),
            tint = CustomTheme.colors.icon.primary.default(),
            contentDescription = null
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    title: String? = null,
    onClick: (() -> Unit),
    tint: Color = CustomTheme.colors.icon.success(),
    tintTitle: Color = CustomTheme.colors.button.success(),
) {
    if (iconRes != null) {
        IconButton(onClick = onClick, modifier = modifier) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = tint
            )
        }
    } else if (title != null) {
        TextButton(onClick = onClick, modifier = modifier) {
            Text(
                text = title.uppercase(),
                style = CustomTheme.typography.button.boldCaps,
                color = tintTitle,
            )
        }
    }
}

@Preview("FlToolbarPreview", showBackground = true)
@Composable
fun FlToolbarPreview() {
    AppTheme {
        FlToolbar(
            title = "Hello",
            navigationIcon = { CloseButton() },
            actionIcon = {
                ActionButton(
                    modifier = Modifier.padding(end = 16.dp),
                    title = "На карте",
                    onClick = {}
                )
            })
    }
}