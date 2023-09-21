package ru.mobileup.template.core.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val isLight: Boolean,
    val common: GlobalColors,
    val background: BackgroundColors,
    val text: TextColors,
    val icon: IconColors,
    val button: ButtonColors,
    val border: BorderColors,
    val chips: ChipsColors
)

data class GlobalColors(
    val brand: @Composable () -> Color,
)

data class BackgroundColors(
    val screen: @Composable () -> Color,
    val cardA: @Composable () -> Color,
    val cardB: @Composable () -> Color,
    val cardC: @Composable () -> Color,
    val cardD: @Composable () -> Color,
    val input: @Composable () -> Color,
    val tagsGreen: @Composable () -> Color,
    val tagsRed: @Composable () -> Color,
    val tagsYellow: @Composable () -> Color,
    val snackbar: @Composable () -> Color,
    val toast: @Composable () -> Color
)

data class TextColors(
    val primary: PrimaryColor,
    val caption: CaptionColors,
    val input: @Composable () -> Color,
    val error: @Composable () -> Color,
    val success: @Composable () -> Color,
    val tagsGreen: @Composable () -> Color,
    val tagsRed: @Composable () -> Color,
    val tagsYellow: @Composable () -> Color
)

data class IconColors(
    val primary: PrimaryColor,
    val secondary: SecondaryColor,
    val success: @Composable () -> Color,
)

data class ButtonColors(
    val primary: PrimaryColor,
    val success: @Composable () -> Color,
)

data class BorderColors(
    val input: @Composable () -> Color,
    val active: @Composable () -> Color,
    val primary: @Composable () -> Color,
    val invert: @Composable () -> Color
)

data class CaptionColors(
    val primary: PrimaryColor,
    val secondary: SecondaryColor,
)

data class PrimaryColor(
    val default: @Composable () -> Color,
    val pressed: @Composable () -> Color = default,
    val disabled: @Composable () -> Color = default,
    val invert: InvertColor = InvertColor(default = default)
)

data class SecondaryColor(
    val default: @Composable () -> Color,
    val invert: @Composable () -> Color = default,
    val pressed: @Composable () -> Color = default,
    val selected: @Composable () -> Color = default
)

data class InvertColor(
    val default: @Composable () -> Color,
    val pressed: @Composable () -> Color = default,
    val disabled: @Composable () -> Color = default
)

data class ChipsColors(
    val default: @Composable () -> Color,
    val selected: @Composable () -> Color = default,
)

val LocalCustomColors = staticCompositionLocalOf<CustomColors?> { null }
