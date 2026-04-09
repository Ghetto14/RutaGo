package com.ghettodev.rutago.domain.validator

object AuthValidator {

    fun isValidName(name: String): Boolean = name.trim().length >= 3

    fun isValidEmail(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidPhone(phone: String):
            Boolean = phone.length == 10 && phone.all { it.isDigit() }
}