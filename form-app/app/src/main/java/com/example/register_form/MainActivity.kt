package com.example.register_form

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.register_form.models.FormData
import com.example.register_form.models.FormErrors
import com.example.register_form.ui.theme.RegisterformTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterformTheme {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var formData by remember { mutableStateOf(FormData()) }

    var formErrors by remember { mutableStateOf(FormErrors()) }

    var showDatePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("First Name")
                TextField(
                    value = formData.firstName,
                    onValueChange = {
                        formData = formData.copy(firstName = it)
                        formErrors = formErrors.copy(firstName = null)
                    },
                    placeholder = { Text("First Name") },
                    isError = (formErrors.firstName != null),
                    supportingText = {
                        formErrors.firstName?.let { Text(it) }
                    }
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Last Name")
                TextField(
                    value = formData.lastName,
                    onValueChange = {
                        formData = formData.copy(lastName = it)
                        formErrors = formErrors.copy(lastName = null)
                    },
                    placeholder = { Text("Last name") },
                    isError = (formErrors.lastName != null),
                    supportingText = {
                        formErrors.lastName?.let { Text(it) }
                    }
                )
            }
        }

        Row {
            Text("Gender: ")
            formData.options.forEach { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (text == formData.selectedOption),
                            onClick = {
                                formData = formData.copy(selectedOption = text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    RadioButton(
                        selected = (text == formData.selectedOption),
                        onClick = null
                    )
                    Text(text = text, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Birth day")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = formData.birthday,
                    onValueChange = { },
                    placeholder = { Text("Birth day") },
                    readOnly = true,
                    enabled = false,
                    isError = (formErrors.birthday != null),
                    supportingText = {
                        formErrors.birthday?.let { Text(it) }
                    }
                )
                Button(onClick = { showDatePicker = true }) {
                    Text("Select")
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                                val selectedDateMillis = datePickerState.selectedDateMillis
                                if (selectedDateMillis != null) {
                                    calendar.timeInMillis = selectedDateMillis
                                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                                    formData = formData.copy(birthday = sdf.format(calendar.time))
                                    formErrors = formErrors.copy(birthday = null)
                                }
                            }
                        ) { Text("Select") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Address")
            TextField(
                value = formData.address,
                onValueChange = {
                    formData = formData.copy(address = it)
                    formErrors = formErrors.copy(address = null)
                },
                placeholder = { Text("Address") },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                isError = (formErrors.address != null),
                supportingText = {
                    formErrors.address?.let { Text(it) }
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Email")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = formData.email,
                onValueChange = {
                    formData = formData.copy(email = it)
                    formErrors = formErrors.copy(email = null)
                },
                placeholder = { Text("Email") },
                isError = (formErrors.email != null),
                supportingText = {
                    formErrors.email?.let { Text(it) }
                }
            )
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = formData.isAgreeTermsOfUse,
                        onClick = {
                            formData =
                                formData.copy(isAgreeTermsOfUse = !formData.isAgreeTermsOfUse)
                            formErrors = formErrors.copy(isAgreeTermsOfUse = null)
                        },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 8.dp)
            ) {
                Checkbox(checked = formData.isAgreeTermsOfUse, onCheckedChange = null) // <<< SỬA
                Text(
                    text = "I agree to Term of Use",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            formErrors.isAgreeTermsOfUse?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    var currentErrors = FormErrors()

                    if (formData.firstName.isBlank()) {
                        currentErrors = currentErrors.copy(firstName = "First name is required")
                    }

                    if (formData.lastName.isBlank()) {
                        currentErrors = currentErrors.copy(lastName = "Last name is required")
                    }

                    if (formData.birthday.isBlank()) {
                        currentErrors = currentErrors.copy(birthday = "Birthday is required")
                    }

                    if (formData.address.isBlank()) {
                        currentErrors = currentErrors.copy(address = "Address is required")
                    }

                    if (formData.email.isBlank()) {
                        currentErrors = currentErrors.copy(email = "Email is required")
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(formData.email)
                            .matches()
                    ) {
                        currentErrors = currentErrors.copy(email = "Invalid email format")
                    }

                    if (!formData.isAgreeTermsOfUse) {
                        currentErrors =
                            currentErrors.copy(isAgreeTermsOfUse = "You must agree to the terms")
                    }

                    formErrors = currentErrors

                    if (currentErrors === FormErrors()) {
                        println("Validation Success!")
                        println(formData)
                    }

                    println("Validation Failed!")
                }
            ) {
                Text("Register")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegisterformTheme {
        Greeting("Android")
    }
}