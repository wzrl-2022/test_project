package com.example.testproject.helpers

class SimpleValidator {
    private val regex = Regex("[A-Za-z0-9_-]+")

    fun validate(value: String) = value.length >= 3 && regex.matches(value)
}