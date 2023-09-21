package ru.mobileup.template.core.widget

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import ru.mobileup.template.core.theme.custom.CustomTheme

private const val START_ANNOTATION_SYMBOL = "["
private const val END_ANNOTATION_SYMBOL = "]"

@Composable
fun ClickableFooterText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = CustomTheme.typography.caption.regularSmall,
    mainSpanStyle: SpanStyle = SpanStyle(color = CustomTheme.colors.text.caption.primary.default()),
    annotationSpanStyle: SpanStyle = SpanStyle(
        color = CustomTheme.colors.text.caption.primary.default(),
        textDecoration = TextDecoration.Underline,
        fontSize = 12.sp
    ),
    annotations: List<Pair<String, String>>,
    onTextClick: (String) -> Unit
) {
    val annotatedString =
        createAnnotatedString(text, annotationSpanStyle, mainSpanStyle, annotations)
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle,
        onClick = { offset ->
            annotations.forEach {
                annotatedString.getStringAnnotations(tag = it.first, start = offset, end = offset)
                    .firstOrNull()
                    ?.let { onTextClick(it.item) }
            }
        }
    )
}

private fun String.cleanAnnotations() =
    replace(START_ANNOTATION_SYMBOL, "").replace(END_ANNOTATION_SYMBOL, "")

@Composable
private fun createAnnotatedString(
    text: String,
    annotationSpanStyle: SpanStyle,
    mainSpanStyle: SpanStyle,
    annotations: List<Pair<String, String>>
) = buildAnnotatedString {
    val strings = mutableListOf<String>()
    val string = text.also {
        val matcher = "\\$START_ANNOTATION_SYMBOL(.*?)\\$END_ANNOTATION_SYMBOL".toRegex()
        strings.addAll(matcher.findAll(it).map {
            it.value.cleanAnnotations()
        }.toList())
    }.cleanAnnotations()

    var startIndex = 0
    strings.forEachIndexed { index, it ->
        val curIndex = string.indexOf(it)
        val startText = string.subSequence(startIndex, curIndex)
        startIndex = curIndex + it.length

        withStyle(style = mainSpanStyle) { append(startText) }
        pushStringAnnotation(
            tag = annotations[index].first,
            annotation = annotations[index].second
        )
        withStyle(style = annotationSpanStyle) { append(it) }
    }

    withStyle(style = mainSpanStyle) {
        append(string.subSequence(startIndex, string.length))
    }
}