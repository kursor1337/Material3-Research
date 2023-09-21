package ru.mobileup.template.core.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.theme.custom.CustomTheme

@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): BiasAlignment {
    val bias by animateFloatAsState(targetBiasValue)
    val biasAlignment by remember {
        derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
    }
    return biasAlignment
}

@Composable
fun FlSwitch(
    checked: Boolean = true,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    CustomSwitch(
        modifier = modifier,
        checkedThumbColor = CustomTheme.colors.common.brand(),
        uncheckedThumbColor = CustomTheme.colors.button.primary.disabled(),
        checkedTrackColor = CustomTheme.colors.common.brand().copy(alpha = 0.4f),
        uncheckedTrackColor = CustomTheme.colors.background.cardA(),
        isSwitchOn = checked,
        isEnabled = isEnabled,
        onCheckedChange = onCheckedChange
    )
}

@Composable
private fun CustomSwitch(
    modifier: Modifier,
    width: Dp = 56.dp,
    height: Dp = 28.dp,
    checkedThumbColor: Color = CustomTheme.colors.common.brand(),
    uncheckedThumbColor: Color = CustomTheme.colors.icon.primary.default(),
    checkedTrackColor: Color = CustomTheme.colors.common.brand(),
    uncheckedTrackColor: Color = CustomTheme.colors.icon.secondary.default(),
    gapBetweenThumbAndTrackEdge: Dp = 4.dp,
    cornerSize: Int = 56,
    iconInnerPadding: Dp = 4.dp,
    thumbSize: Dp = 20.dp,
    isSwitchOn: Boolean = true,
    isEnabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    // this is to disable the ripple effect
    val interactionSource = remember { MutableInteractionSource() }

    // for moving the thumb
    val alignment = animateAlignmentAsState(if (isSwitchOn) 1f else -1f)

    // outer rectangle with border
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(
                color = if (isSwitchOn) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = cornerSize)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                if (isEnabled) {
                    onCheckedChange(!isSwitchOn)
                }
            },
        contentAlignment = Alignment.Center
    ) {

        // this is to add padding at the each horizontal side
        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {
            // thumb with icon
            Box(
                modifier = Modifier
                    .size(size = thumbSize)
                    .background(
                        color = if (isSwitchOn) checkedThumbColor else uncheckedThumbColor,
                        shape = CircleShape
                    )
                    .padding(all = iconInnerPadding),
            )
        }
    }
}

@Preview("MainSwitchCheckedPreview", showBackground = true)
@Composable
fun MainSwitchCheckedPreview() {
    AppTheme {
        FlSwitch(checked = true)
    }
}

@Preview("MainSwitchUnCheckedPreview", showBackground = true)
@Composable
fun MainSwitchUnCheckedPreview() {
    AppTheme {
        FlSwitch(checked = false)
    }
}