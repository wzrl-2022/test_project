package com.example.testproject.ui.registration

data class RegistrationState(
    val phoneNumber: String,
    val name: String,
    val username: String,
    val validationError: Boolean
)