package com.example.caculator.ui.theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val buttonColor = when (label) {
        in "0".."9", "." -> MaterialTheme.colorScheme.surface
        in listOf("+", "-", "x", "/", "=") -> MaterialTheme.colorScheme.primaryContainer
        in listOf("C", "CE", "BS", "+/-") -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when (label) {
        in "0".."9", "." -> MaterialTheme.colorScheme.onSurface
        in listOf("+", "-", "x", "/", "=") -> MaterialTheme.colorScheme.onPrimaryContainer
        in listOf("C", "CE", "BS", "+/-") -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = textColor
        )
    ) {
        Text(text = label, fontSize = 24.sp)
    }
}