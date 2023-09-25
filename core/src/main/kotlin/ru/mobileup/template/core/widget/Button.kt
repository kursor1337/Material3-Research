package ru.mobileup.template.core.widget

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mobileup.template.core.R
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.theme.custom.CustomTheme

private val defaultBorderColor = Color.Transparent

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    borderColor: Color = CustomTheme.colors.border.active(),
    backgroundColor: Color = CustomTheme.colors.button.primary.default(),
    backgroundDisableColor: Color = CustomTheme.colors.button.primary.disabled(),
    backgroundPressColor: Color = CustomTheme.colors.button.primary.pressed(),
    textColor: Color = CustomTheme.colors.text.primary.invert.default(),
    textPressColor: Color = CustomTheme.colors.text.primary.invert.pressed(),
    textDisableColor: Color = CustomTheme.colors.text.primary.invert.disabled(),
    textAlign: TextAlign = TextAlign.Center,
    focusRequester: FocusRequester = remember { FocusRequester() },
    shape: Shape = RoundedCornerShape(16.dp),
    isEnabled: Boolean = true,
    isProgress: Boolean = false,
    height: Dp = 56.dp,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) = Box(
    modifier = modifier.heightIn(0.dp, height)
) {
    ContainedButton(
        text = if (isProgress) "" else text,
        onClick = if (isProgress) {
            {}
        } else {
            onClick
        },
        borderColor = borderColor,
        backgroundColor = backgroundColor,
        backgroundDisableColor = backgroundDisableColor,
        backgroundPressColor = backgroundPressColor,
        textColor = textColor,
        textPressColor = textPressColor,
        textDisableColor = textDisableColor,
        focusRequester = focusRequester,
        shape = shape,
        isEnabled = isEnabled && !isProgress,
        leftIcon = leftIcon,
        rightIcon = rightIcon,
        textAlign = textAlign,
    )
    if (isProgress) {
        InfinityProgress(color = CustomTheme.colors.icon.primary.invert.default())
    }
}

@Composable
private fun BoxScope.InfinityProgress(color: Color) {
    CircularProgressIndicator(
        color = color,
        modifier = Modifier
            .align(Alignment.Center)
            .size(24.dp)
    )
}

@Composable
fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    borderColor: Color = CustomTheme.colors.border.active(),
    textColor: Color = CustomTheme.colors.text.primary.default(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    isEnabled: Boolean = true,
    leftIcon: @Composable () -> Unit = {},
    rightIcon: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    var newBorderColor by remember { mutableStateOf(defaultBorderColor) }

    TextButton(
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
        ),
        modifier = modifier.then(
            Modifier
                .heightIn(0.dp, 56.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    newBorderColor = if (it.isFocused) borderColor else defaultBorderColor
                }
                .focusTarget()
                .border(BorderStroke(3.dp, newBorderColor), shape)
        )
    ) {
        leftIcon()
        Text(
            text = text,
            color = textColor,
            style = CustomTheme.typography.button.mediumNormal,
            fontSize = 14.sp,
        )
        rightIcon()
    }
}

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomTheme.colors.button.success()
        )
    ) {
        Text(
            text = text,
            style = CustomTheme.typography.button.mediumNormal,
            color = CustomTheme.colors.text.primary.invert.default()
        )
    }
}

@Composable
fun ContainedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    borderColor: Color,
    backgroundColor: Color,
    backgroundDisableColor: Color,
    backgroundPressColor: Color,
    textColor: Color,
    textPressColor: Color,
    textDisableColor: Color,
    focusRequester: FocusRequester,
    shape: Shape,
    isEnabled: Boolean,
    textAlign: TextAlign,
    leftIcon: @Composable (() -> Unit)?,
    rightIcon: @Composable (() -> Unit)?,
    content: @Composable (
        String,
        Color,
        @Composable (() -> Unit)?,
        @Composable (() -> Unit)?
    ) -> Unit = { text, color, leftIcon, rightIcon ->
        DefaultButtonContent(text, color, textAlign, leftIcon, rightIcon)
    }
) {
    val animationScope = rememberCoroutineScope()
    var animationPlayTime by remember { mutableStateOf(0L) }
    var animationOffsetX by remember { mutableStateOf(0f) }
    val animationShake = remember {
        TargetBasedAnimation(
            animationSpec = repeatable(
                iterations = 4,
                animation = tween(100),
                repeatMode = RepeatMode.Reverse
            ),
            typeConverter = Float.VectorConverter,
            initialValue = -10f,
            targetValue = 10f
        )
    }
    var newBorderColor by remember { mutableStateOf(defaultBorderColor) }
    var newBackgroundColor by remember { mutableStateOf(backgroundColor) }
    var newTextColor by remember { mutableStateOf(textColor) }
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()

    if (isButtonPressed) {
        newBackgroundColor = backgroundPressColor
        newTextColor = textPressColor
    } else {
        newBackgroundColor = backgroundColor
        newTextColor = textColor
    }

    fun onButtonClick(isEnabled: Boolean) {
        if (isEnabled) {
            onClick()
        } else {
            animationScope.launch {
                val startTime = withFrameNanos { it }
                var iteration = 0
                do {
                    animationPlayTime = withFrameNanos { it } - startTime
                    animationOffsetX =
                        animationShake.getValueFromNanos(animationPlayTime)
                    if (animationOffsetX == animationShake.targetValue) {
                        iteration++
                    }
                } while (iteration < 4)
                animationOffsetX = 0f
            }
        }
    }

    Button(
        interactionSource = interactionSource,
        modifier = modifier
            .offset(x = animationOffsetX.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                newBorderColor = if (it.isFocused) borderColor else defaultBorderColor
            }
            .focusTarget()
            .border(BorderStroke(3.dp, newBorderColor), shape)
            .fillMaxHeight(),
        onClick = { onButtonClick(isEnabled) },
        contentPadding = PaddingValues(2.dp),
        colors = if (isEnabled) {
            ButtonDefaults.buttonColors(newBackgroundColor)
        } else {
            ButtonDefaults.buttonColors(backgroundDisableColor)
        },
        elevation = null,
        shape = shape,
    ) {
        content(
            text,
            if (isEnabled) newTextColor else textDisableColor,
            leftIcon,
            rightIcon
        )
    }
}

@Composable
private fun DefaultButtonContent(
    text: String,
    textColor: Color,
    textAlign: TextAlign = TextAlign.Center,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
) {
    leftIcon?.let {
        Box(modifier = Modifier) {
            it.invoke()
        }
    }

    Text(
        modifier = Modifier.weight(1f),
        textAlign = textAlign,
        text = text,
        color = textColor,
        style = CustomTheme.typography.button.mediumNormal,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )

    rightIcon?.let {
        Box(modifier = Modifier) {
            it.invoke()
        }
    }
}

@Preview("TextButtonFocusPreview", showBackground = true)
@Composable
fun TextButtonFocusPreview() {
    AppTheme {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            delay(100)
            focusRequester.requestFocus()
        }
        TextButton(
            text = "Text button",
            onClick = {},
            focusRequester = focusRequester
        )
    }
}

@Preview("TextButtonPreview", showBackground = true)
@Composable
fun TextButtonPreview() {
    AppTheme {
        TextButton(
            text = "Text button",
            onClick = {}
        )
    }
}

@Preview("PrimaryButtonPreview", showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    AppTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = "Lorem ipsum is a placeholder text commonly used to demonstrate",
                onClick = {},
                leftIcon = {
                    Icon(
                        tint = CustomTheme.colors.icon.primary.invert.default(),
                        painter = painterResource(id = R.drawable.ic_16_close),
                        contentDescription = null
                    )
                },
                rightIcon = {
                    Icon(
                        tint = CustomTheme.colors.icon.primary.invert.default(),
                        painter = painterResource(id = R.drawable.ic_16_plus),
                        contentDescription = null
                    )
                }
            )
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = "Lorem ipsum is a placeholder text commonly used to demonstrate",
                onClick = {},
                leftIcon = {
                    Icon(
                        tint = CustomTheme.colors.icon.primary.invert.default(),
                        painter = painterResource(id = R.drawable.ic_16_close),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

@Preview("PrimaryButtonFocusPreview", showBackground = true)
@Composable
fun PrimaryButtonFocusPreview() {
    AppTheme {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            delay(100)
            focusRequester.requestFocus()
        }
        PrimaryButton(
            text = "Primary text",
            onClick = {},
            focusRequester = focusRequester,
        )
    }
}

@Preview("PrimaryButtonDisalePreview", showBackground = true)
@Composable
fun PrimaryButtonDisablePreview() {
    AppTheme {
        PrimaryButton(
            text = "Primary text",
            onClick = {},
            isEnabled = false,
        )
    }
}

@Preview("PrimaryProgressButtonPreview", showBackground = true)
@Composable
fun PrimaryProgressButtonPreview() {
    AppTheme {
        PrimaryButton(
            text = "Primary text",
            onClick = {},
            isProgress = true,
        )
    }
}