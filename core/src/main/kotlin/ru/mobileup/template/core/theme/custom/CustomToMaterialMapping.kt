package ru.mobileup.template.core.theme.custom

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
fun CustomColors.toMaterialColors(): ColorScheme {
    return ColorScheme(
        primary = common.brand(),
        secondary = icon.primary.default(),
        tertiary = icon.secondary.default(),
        background = background.screen(),
        surface = background.cardA(),
        error = text.error(),
        onPrimary = text.primary.default(),
        onSecondary = text.primary.default(),
        onTertiary = text.primary.default(),
        onBackground = text.primary.default(),
        onSurface = text.primary.default(),
        onError = background.screen(),
        primaryContainer = background.screen(),
        secondaryContainer = icon.primary.invert.default(),
        tertiaryContainer = icon.secondary.invert(),
        errorContainer = background.screen(),
        onPrimaryContainer = text.primary.invert.default(),
        onSecondaryContainer = text.primary.invert.default(),
        onTertiaryContainer = text.primary.invert.default(),
        onErrorContainer = text.error(),
        inversePrimary = text.primary.invert.default(),
        surfaceVariant = background.cardB(),
        onSurfaceVariant = text.primary.default(),
        surfaceTint = background.cardC(),
        inverseSurface = background.cardB(),
        inverseOnSurface = text.primary.invert.default(),
        outline = border.active(),
        outlineVariant = border.primary(),
        scrim = border.input().copy(alpha = 0.6f)
    )
}

fun CustomTypography.toMaterialTypography(): Typography {
    return Typography(
        displayLarge = title.boldLarge,
        displayMedium = title.boldNormal,
        displaySmall = title.boldSmall,
        headlineLarge = title.mediumSmall,
        headlineSmall = title.regularLarge,
        titleMedium = body.regularNormal,
        titleSmall = body.regularSmall,
        labelLarge = button.mediumNormal,
        bodySmall = caption.regularLarge,
        labelSmall = caption.regularNormal
    )
}
