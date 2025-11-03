package com.example.caculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caculator.ui.theme.CaculatorTheme
import com.example.caculator.ui.theme.CalculatorButton
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }
    var waitingForOperand2 by remember { mutableStateOf(false) }

    val buttons = listOf(
        "CE", "C", "BS", "/",
        "7", "8", "9", "x",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "+/-", "0", ".", "="
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = display, // <<< SỬA LỖI Ở ĐÂY
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp),
                maxLines = 1,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .weight(1.5f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(buttons) { buttonLabel ->
                CalculatorButton (
                    label = buttonLabel,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxSize(),
                    onClick = {
                        when (buttonLabel) {
                            "C" -> {
                                display = "0"
                                operand1 = null
                                operation = null
                                waitingForOperand2 = false
                            }
                            "CE" -> {
                                display = "0"
                            }
                            "BS" -> {
                                if (display.length > 1) {
                                    display = display.dropLast(1)
                                } else {
                                    display = "0"
                                }
                            }
                            in "0".."9" -> {
                                if (waitingForOperand2) {
                                    display = buttonLabel
                                    waitingForOperand2 = false
                                } else {
                                    display = if (display == "0") buttonLabel else display + buttonLabel
                                }
                            }
                            "." -> {
                                if (waitingForOperand2) {
                                    display = "0."
                                    waitingForOperand2 = false
                                } else if (!display.contains(".")) {
                                    display += "."
                                }
                            }
                            "+/-" -> {
                                if (display.startsWith("-")) {
                                    display = display.removePrefix("-")
                                } else if (display != "0") {
                                    display = "-$display"
                                }
                            }
                            in listOf("+", "-", "x", "/") -> {
                                operand1 = display.toDoubleOrNull()
                                operation = buttonLabel
                                waitingForOperand2 = true
                            }
                            "=" -> {
                                val operand2 = display.toDoubleOrNull()
                                if (operand1 != null && operand2 != null && operation != null) {
                                    val op1 = operand1!!
                                    val result = when (operation) {
                                        "+" -> op1 + operand2
                                        "-" -> op1 - operand2
                                        "x" -> op1 * operand2
                                        "/" -> if (operand2 == 0.0) Double.NaN else op1 / operand2
                                        else -> 0.0
                                    }

                                    display = formatResult(result)

                                    operand1 = null
                                    operation = null
                                    waitingForOperand2 = false
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun formatResult(result: Double): String {
    if (result.isNaN()) {
        return "Error"
    }

    if (result == result.toLong().toDouble()) {
        return result.toLong().toString()
    }

    val decimalFormat = DecimalFormat("#.########")
    return decimalFormat.format(result)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaculatorTheme {
        Greeting("Android")
    }
}