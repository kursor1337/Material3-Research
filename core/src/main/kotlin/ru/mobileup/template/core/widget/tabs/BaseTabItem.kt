package ru.mobileup.template.core.widget.tabs

import dev.icerock.moko.resources.desc.StringDesc

interface BaseTabItem {
    val title: StringDesc
    val isVisible: Boolean
}