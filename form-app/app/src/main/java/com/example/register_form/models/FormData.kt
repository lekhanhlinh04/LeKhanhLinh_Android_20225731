package com.example.register_form.models

data class FormData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val address: String = "",
    val birthday: String = "",
    val isAgreeTermsOfUse: Boolean = false,
    val options: List<String> = listOf("Male", "Female"),
    val selectedOption: String = options[0]
)