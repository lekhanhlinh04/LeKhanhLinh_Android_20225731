package com.example.register_form.models

data class FormErrors(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val address: String? = null,
    val birthday: String? = null,
    val isAgreeTermsOfUse: String? = null
)
