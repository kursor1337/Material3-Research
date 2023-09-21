package ru.mobileup.template.core.theme.custom

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CustomTypography(
    val title: TitleTypography,
    val body: BodyTypography,
    val caption: CaptionTypography,
    val button: ButtonTypography
)

data class TitleTypography(
    val boldVeryLarge: TextStyle,
    val boldLarge: TextStyle,
    val boldNormal: TextStyle,
    val boldSmall: TextStyle,
    val mediumSmall: TextStyle,
    val regularLarge: TextStyle,
)

data class BodyTypography(
    val regularNormal: TextStyle,
    val boldNormal: TextStyle,
    val boldSmall: TextStyle,
    val regularSmall: TextStyle,
)

data class CaptionTypography(
    val regularLarge: TextStyle,
    val regularNormal: TextStyle,
    val boldSmall: TextStyle,
    val boldExtra: TextStyle,
    val regularSmall: TextStyle,
    val regularUltraSmall: TextStyle,
    val crossPriceLarge: TextStyle,
    val mediumLarge: TextStyle
)

data class ButtonTypography(
    val mediumNormal: TextStyle,
    val mediumSmall: TextStyle,
    val boldCaps: TextStyle
)

val LocalCustomTypography = staticCompositionLocalOf<CustomTypography?> { null }
