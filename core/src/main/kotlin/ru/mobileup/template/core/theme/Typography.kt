package ru.mobileup.template.core.theme

import ru.mobileup.template.core.theme.custom.BodyTypography
import ru.mobileup.template.core.theme.custom.ButtonTypography
import ru.mobileup.template.core.theme.custom.CaptionTypography
import ru.mobileup.template.core.theme.custom.CustomTypography
import ru.mobileup.template.core.theme.custom.TitleTypography
import ru.mobileup.template.core.theme.values.Typography

val AppTypography = CustomTypography(
    title = TitleTypography(
        boldLarge = Typography.titleBoldLarge,
        boldNormal = Typography.titleBoldNormal,
        boldSmall = Typography.titleBoldSmall,
        mediumSmall = Typography.titleMediumSmall,
        regularLarge = Typography.titleRegularLarge,
        boldVeryLarge = Typography.titleBoldVeryLarge
    ),
    body = BodyTypography(
        regularNormal = Typography.bodyRegularNormal,
        boldNormal = Typography.bodyBoldNormal,
        boldSmall = Typography.bodyBoldSmall,
        regularSmall = Typography.bodyRegularSmall,
    ),
    caption = CaptionTypography(
        regularLarge = Typography.captionRegularLarge,
        regularNormal = Typography.captionRegularNormal,
        boldSmall = Typography.captionBoldSmall,
        boldExtra = Typography.captionBoldExtra,
        regularSmall = Typography.captionRegularSmall,
        regularUltraSmall = Typography.captionRegularUltraSmall,
        crossPriceLarge = Typography.captionCrossPriceLarge,
        mediumLarge = Typography.captionMediumLarge,
    ),
    button = ButtonTypography(
        mediumNormal = Typography.buttonMediumNormal,
        mediumSmall = Typography.buttonMediumSmall,
        boldCaps = Typography.buttonBoldcaps,
    )
)
