package ru.mobileup.template.core.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.template.core.theme.custom.CustomTheme

@Composable
fun BlockHeader(
    modifier: Modifier = Modifier,
    headerTitle: StringDesc,
    headerAction: StringDesc,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    showAction: Boolean = true,
    useHeaderVisibleAnimation: Boolean = false,
    onHeaderActionClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headerTitle.localized(),
            style = style,
            color = color
        )
        if (useHeaderVisibleAnimation) {
            AnimatedVisibility(visible = showAction) {
                ActionHeaderButton(
                    headerAction = headerAction,
                    onHeaderActionClick = onHeaderActionClick
                )
            }
        } else {
            if (showAction) {
                ActionHeaderButton(
                    headerAction = headerAction,
                    onHeaderActionClick = onHeaderActionClick
                )
            }
        }
    }
}

@Composable
inline fun BlockHeader(
    modifier: Modifier = Modifier,
    headerTitle: StringDesc,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    headerContent: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headerTitle.localized(),
            style = style,
            color = color
        )
        headerContent()
    }
}
@Composable
fun ActionHeaderButton(
    headerAction: StringDesc,
    onHeaderActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false),
            onClick = onHeaderActionClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = headerAction.localized().uppercase(),
            style = CustomTheme.typography.button.boldCaps,
            color = CustomTheme.colors.button.success()
        )
    }
}