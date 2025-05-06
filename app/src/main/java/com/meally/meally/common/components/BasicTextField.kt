package com.meally.meally.common.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography

@Composable
fun BasicTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors =
        OutlinedTextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
        ),
    textStyle: TextStyle =
        Typography.body1.copy(
            color = MaterialTheme.colorScheme.onBackground,
        ),
    shape: Shape = RoundedCornerShape(100.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = modifier,
        colors = colors,
        shape = shape,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        label = label,
    )
}

@Preview
@Composable
private fun BasicTextFieldPreview() {
    MeallyTheme {
        BasicTextField(
            text = "Some text",
            onTextChanged = {},
        )
    }
}
