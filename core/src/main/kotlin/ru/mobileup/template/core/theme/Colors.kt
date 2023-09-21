package ru.mobileup.template.core.theme

import ru.mobileup.template.core.theme.custom.BackgroundColors
import ru.mobileup.template.core.theme.custom.BorderColors
import ru.mobileup.template.core.theme.custom.ButtonColors
import ru.mobileup.template.core.theme.custom.CaptionColors
import ru.mobileup.template.core.theme.custom.ChipsColors
import ru.mobileup.template.core.theme.custom.CustomColors
import ru.mobileup.template.core.theme.custom.GlobalColors
import ru.mobileup.template.core.theme.custom.IconColors
import ru.mobileup.template.core.theme.custom.InvertColor
import ru.mobileup.template.core.theme.custom.PrimaryColor
import ru.mobileup.template.core.theme.custom.SecondaryColor
import ru.mobileup.template.core.theme.custom.TextColors
import ru.mobileup.template.core.theme.values.Colors
import ru.mobileup.template.core.theme.values.brand
import ru.mobileup.template.core.theme.values.lightModeBackgroundCardA
import ru.mobileup.template.core.theme.values.lightModeBackgroundCardB
import ru.mobileup.template.core.theme.values.lightModeBackgroundCardC
import ru.mobileup.template.core.theme.values.lightModeBackgroundCardD
import ru.mobileup.template.core.theme.values.lightModeBackgroundInput
import ru.mobileup.template.core.theme.values.lightModeBackgroundScreen
import ru.mobileup.template.core.theme.values.lightModeBackgroundSnackbar
import ru.mobileup.template.core.theme.values.lightModeBackgroundToast
import ru.mobileup.template.core.theme.values.lightModeBorderActive
import ru.mobileup.template.core.theme.values.lightModeBorderInputInvert
import ru.mobileup.template.core.theme.values.lightModeBorderInvert
import ru.mobileup.template.core.theme.values.lightModeBorderPrimary
import ru.mobileup.template.core.theme.values.lightModeButtonPrimary
import ru.mobileup.template.core.theme.values.lightModeButtonPrimaryDisabled
import ru.mobileup.template.core.theme.values.lightModeButtonPrimaryInevrt
import ru.mobileup.template.core.theme.values.lightModeButtonPrimaryPressed
import ru.mobileup.template.core.theme.values.lightModeButtonSuccess
import ru.mobileup.template.core.theme.values.lightModeChipsDefault
import ru.mobileup.template.core.theme.values.lightModeChipsSelected
import ru.mobileup.template.core.theme.values.lightModeIconPrimary
import ru.mobileup.template.core.theme.values.lightModeIconPrimaryInvert
import ru.mobileup.template.core.theme.values.lightModeIconSecondary
import ru.mobileup.template.core.theme.values.lightModeIconSuccess
import ru.mobileup.template.core.theme.values.lightModeTagsGreenBg
import ru.mobileup.template.core.theme.values.lightModeTagsGreenText
import ru.mobileup.template.core.theme.values.lightModeTagsPending
import ru.mobileup.template.core.theme.values.lightModeTagsPendingBg
import ru.mobileup.template.core.theme.values.lightModeTagsRedBg
import ru.mobileup.template.core.theme.values.lightModeTagsRedText
import ru.mobileup.template.core.theme.values.lightModeTextCaptionPrimary
import ru.mobileup.template.core.theme.values.lightModeTextCaptionSecondary
import ru.mobileup.template.core.theme.values.lightModeTextError
import ru.mobileup.template.core.theme.values.lightModeTextInput
import ru.mobileup.template.core.theme.values.lightModeTextPrimary
import ru.mobileup.template.core.theme.values.lightModeTextPrimaryInvert
import ru.mobileup.template.core.theme.values.lightModeTextSuccess

val LightAppColors = CustomColors(
    isLight = true,

    common = GlobalColors(
        brand = { Colors.brand() },
    ),

    background = BackgroundColors(
        screen = { Colors.lightModeBackgroundScreen() },
        cardA = { Colors.lightModeBackgroundCardA() },
        cardB = { Colors.lightModeBackgroundCardB() },
        cardC = { Colors.lightModeBackgroundCardC() },
        cardD = { Colors.lightModeBackgroundCardD() },
        input = { Colors.lightModeBackgroundInput() },
        tagsGreen = { Colors.lightModeTagsGreenBg() },
        tagsRed = { Colors.lightModeTagsRedBg() },
        snackbar = { Colors.lightModeBackgroundSnackbar() },
        tagsYellow = { Colors.lightModeTagsPendingBg() },
        toast = { Colors.lightModeBackgroundToast() }
    ),

    border = BorderColors(
        input = { Colors.lightModeBorderInputInvert() },
        active = { Colors.lightModeBorderActive() },
        primary = { Colors.lightModeBorderPrimary() },
        invert = { Colors.lightModeBorderInvert() }
    ),

    text = TextColors(
        primary = PrimaryColor(
            default = { Colors.lightModeTextPrimary() },
            invert = InvertColor(
                default = { Colors.lightModeTextPrimaryInvert() },
            )
        ),
        caption = CaptionColors(
            primary = PrimaryColor(
                default = { Colors.lightModeTextCaptionPrimary() }
            ),
            secondary = SecondaryColor(
                default = { Colors.lightModeTextCaptionSecondary() }
            )
        ),
        input = { Colors.lightModeTextInput() },
        error = { Colors.lightModeTextError() },
        success = { Colors.lightModeTextSuccess() },
        tagsGreen = { Colors.lightModeTagsGreenText() },
        tagsRed = { Colors.lightModeTagsRedText() },
        tagsYellow = { Colors.lightModeTagsPending() }
    ),

    icon = IconColors(
        primary = PrimaryColor(
            default = { Colors.lightModeIconPrimary() },
            invert = InvertColor(
                default = { Colors.lightModeIconPrimaryInvert() },
            )
        ),
        secondary = SecondaryColor(
            default = { Colors.lightModeIconSecondary() },
        ),
        success = { Colors.lightModeIconSuccess() }
    ),

    button = ButtonColors(
        primary = PrimaryColor(
            default = { Colors.lightModeButtonPrimary() },
            disabled = { Colors.lightModeButtonPrimaryDisabled() },
            pressed = { Colors.lightModeButtonPrimaryPressed() },
            invert = InvertColor(
                default = { Colors.lightModeButtonPrimaryInevrt() },
            )
        ),
        success = { Colors.lightModeButtonSuccess() }
    ),

    chips = ChipsColors(
        default = { Colors.lightModeChipsDefault() },
        selected = { Colors.lightModeChipsSelected() }
    )
)

val DarkAppColors = LightAppColors // not implemented
