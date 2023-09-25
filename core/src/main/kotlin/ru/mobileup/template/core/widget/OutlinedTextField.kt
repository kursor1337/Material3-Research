package ru.mobileup.template.core.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.toCompose
import ru.mobileup.template.core.R
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.theme.custom.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicBorderTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    val textColor = textStyle.color
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    BasicTextField(
        value = value,
        modifier = if (label != null) {
            modifier
                // Merge semantics at the beginning of the modifier chain to ensure padding is
                // considered part of the text field.
                .semantics(mergeDescendants = true) {}
        } else {
            modifier
        },
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value.text,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = TextFieldDefaults.textFieldWithLabelPadding(
                    bottom = 20.dp
                ),
            )
        }
    )
}

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    placeholder: String? = null,
    backgroundColor: Color = CustomTheme.colors.background.input(),
    placeholderColor: Color = CustomTheme.colors.text.caption.primary.default(),
    borderColor: Color = CustomTheme.colors.border.active(),
    textErrorColor: Color = CustomTheme.colors.text.error(),
    textColor: Color = CustomTheme.colors.text.primary.default(),
    textPlaceholderColor: Color = CustomTheme.colors.text.input(),
    shape: Shape = RoundedCornerShape(16.dp),
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textFieldHeight: Dp = Dp.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: StringDesc? = null,
    isEnabled: Boolean = true,
    hasFocus: Boolean = false,
    singleLine: Boolean = minLines <= 1,
    readOnly: Boolean = false,
    changeCursorPositionEvent: Flow<Int> = emptyFlow()
) {
    val focusRequester = remember { FocusRequester() }
    val isInFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    SideEffect {
        if (hasFocus) {
            focusRequester.requestFocus()
        } else if (isInFocus.value) {
            focusManager.clearFocus()
        }
    }

    val currentValue by rememberUpdatedState(value)

    var currentSelection by rememberSaveable(stateSaver = TextRangeSaver) {
        mutableStateOf(TextRange(0))
    }

    var currentComposition by rememberSaveable(stateSaver = NullableTextRangeSaver) {
        mutableStateOf(null)
    }

    val currentTextFieldValue by remember {
        derivedStateOf {
            TextFieldValue(currentValue, currentSelection, currentComposition)
        }
    }

    LaunchedEffect(key1 = changeCursorPositionEvent) {
        changeCursorPositionEvent.collectLatest {
            currentSelection = TextRange(it)
        }
    }

    BasicBorderTextField(
        label = label?.let {
            {
                Text(
                    text = it,
                    color = textPlaceholderColor,
                    style = CustomTheme.typography.caption.regularUltraSmall
                )
            }
        },
        value = currentTextFieldValue,
        onValueChange = {
            onValueChanged(it.text)
            currentSelection = it.selection
            currentComposition = it.composition
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable { onClick() }
            .requiredHeight(textFieldHeight)
            .then(
                Modifier.border(
                    border = BorderStroke(
                        if (hasFocus) 2.dp else 1.dp,
                        if (hasFocus) borderColor else CustomTheme.colors.border.primary()
                    ),
                    shape = shape
                )
            )
            .focusRequester(focusRequester)
            .onFocusChanged {
                isInFocus.value = it.isFocused
                onFocusChanged(it)
            },
        readOnly = readOnly,
        enabled = isEnabled,
        textStyle = CustomTheme.typography.body.regularNormal,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = error != null,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        placeholder = placeholder?.let {
            {
                Text(
                    text = it,
                    color = textPlaceholderColor,
                    style = CustomTheme.typography.body.regularNormal
                )
            }
        },
        interactionSource = remember { MutableInteractionSource() },
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor,
            errorTextColor = textErrorColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            errorContainerColor = backgroundColor,
            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor,
            disabledPlaceholderColor = placeholderColor,
            errorPlaceholderColor = placeholderColor,
            errorBorderColor = Color.Transparent,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = CustomTheme.colors.border.primary(),
            disabledBorderColor = Color.Transparent
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutlinedTextField(
    inputControl: InputControl,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    onClick: () -> Unit = {},
    backgroundColor: Color = CustomTheme.colors.background.input(),
    placeholderColor: Color = CustomTheme.colors.text.caption.primary.default(),
    borderColor: Color = CustomTheme.colors.border.active(),
    enabled: Boolean? = null,
    textErrorColor: Color = CustomTheme.colors.text.error(),
    textColor: Color = CustomTheme.colors.text.primary.default(),
    textPlaceholderColor: Color = CustomTheme.colors.text.input(),
    textHeaderColor: Color = CustomTheme.colors.text.caption.primary.default(),
    shape: Shape = RoundedCornerShape(16.dp),
    footerText: String? = null,
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textFieldHeight: Dp = Dp.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = inputControl.visualTransformation.toCompose(),
    header: String? = null,
    readOnly: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    changeCursorPositionEvent: Flow<Int> = emptyFlow()
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val hasFocus by inputControl.hasFocus.collectAsState()
    val error: StringDesc? by inputControl.error.collectAsState()
    val text by inputControl.text.collectAsState()
    val isInputControlEnabled by inputControl.enabled.collectAsState()
    val isEnabled = enabled ?: isInputControlEnabled

    LaunchedEffect(key1 = inputControl) {
        inputControl.scrollToItEvent.collectLatest {
            bringIntoViewRequester.bringIntoView()
        }
    }

    Column(
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        header?.let {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textHeaderColor,
                style = CustomTheme.typography.caption.regularSmall,
            )
        }

        OutlinedTextField(
            value = text,
            shape = shape,
            onClick = onClick,
            onValueChanged = {
                inputControl.onTextChanged(it)
            },
            onFocusChanged = { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                }
                inputControl.onFocusChanged(focusState.isFocused)
            },
            placeholder = placeholder,
            backgroundColor = backgroundColor,
            placeholderColor = placeholderColor,
            borderColor = borderColor,
            textColor = textColor,
            textPlaceholderColor = textPlaceholderColor,
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textFieldHeight = textFieldHeight,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            keyboardOptions = inputControl.keyboardOptions.toCompose(),
            keyboardActions = keyboardActions,
            error = error,
            isEnabled = isEnabled,
            hasFocus = hasFocus,
            singleLine = inputControl.singleLine,
            readOnly = readOnly,
            changeCursorPositionEvent = changeCursorPositionEvent
        )

        Footer(
            errorText = error?.localized() ?: footerText,
            color = if (error != null) textErrorColor else textHeaderColor
        )
    }
}

@Composable
fun Footer(
    errorText: String?,
    color: Color,
    modifier: Modifier = Modifier
) {
    errorText?.let {
        Text(
            modifier = modifier.padding(top = 2.dp),
            text = it,
            style = CustomTheme.typography.caption.regularSmall,
            color = color
        )
    }
}

private val TextRangeSaver = listSaver(
    save = { listOf(it.start, it.end) },
    restore = { TextRange(it[0], it[1]) }
)

private val NullableTextRangeSaver = listSaver<TextRange?, Int>(
    save = { if (it != null) listOf(it.start, it.end) else emptyList() },
    restore = { TextRange(it[0], it[1]) }
)

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldPreview() {
    val inputControl = InputControl(CoroutineScope(Dispatchers.Main))

    AppTheme {
        OutlinedTextField(
            inputControl = inputControl,
            placeholder = "Placeholder",
            footerText = "Footer text",
            label = "Label",
            leadingIcon = {
                Icon(
                    tint = CustomTheme.colors.icon.secondary.default(),
                    painter = painterResource(id = R.drawable.ic_16_search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "На карте",
                    color = CustomTheme.colors.text.primary.default(),
                    style = CustomTheme.typography.button.mediumNormal,
                )
            },
            header = "Header"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldFocusPreview() {
    val inputControl = InputControl(CoroutineScope(Dispatchers.Main)).apply {
        requestFocus()
    }

    AppTheme {
        OutlinedTextField(
            inputControl = inputControl,
            placeholder = "Placeholder",
        )
    }
}