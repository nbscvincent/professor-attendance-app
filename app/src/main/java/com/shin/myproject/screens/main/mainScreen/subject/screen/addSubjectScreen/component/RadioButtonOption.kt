package com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonOption(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = text == selectedOption,
            onClick = {
                onOptionSelected(text)
            },
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            color = if (text == selectedOption) Color.Red else Color.Gray
        )
    }
}