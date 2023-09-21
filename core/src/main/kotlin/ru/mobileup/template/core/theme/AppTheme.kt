package ru.mobileup.template.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.mobileup.template.core.theme.custom.CustomTheme
import ru.mobileup.template.core.theme.custom.toMaterialColors
import ru.mobileup.template.core.theme.custom.toMaterialTypography

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) DarkAppColors else LightAppColors
    val typography = AppTypography

    CustomTheme(colors, typography) {
        MaterialTheme(
            colorScheme = colors.toMaterialColors(),
            typography = typography.toMaterialTypography(),
            content = content
        )
    }
}