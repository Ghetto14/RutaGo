package com.ghettodev.rutago.domain.validator

object AuthValidator {

    fun isValidName(name: String): Boolean {
        val trimmed = name.trim()
        return trimmed.length >= 3 && trimmed.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))
    }

    fun isValidEmail(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            .matches(email.trim())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6 &&
                password.any { it.isDigit() } &&
                password.any { it.isLetter() }
    }

    fun passwordsMatch(password: String, confirm: String): Boolean {
        return password == confirm
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.length == 10 && phone.all { it.isDigit() }
    }

    fun getNameError(name: String): String? {
        return when {
            name.isBlank() -> "El nombre no puede estar vacío"
            name.trim().length < 3 -> "El nombre debe tener al menos 3 caracteres"
            !name.trim().matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) -> "El nombre solo puede contener letras"
            else -> null
        }
    }

    fun getEmailError(email: String): String? {
        return when {
            email.isBlank() -> "El correo no puede estar vacío"
            !isValidEmail(email) -> "Correo electrónico inválido"
            else -> null
        }
    }

    fun getPasswordError(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña no puede estar vacía"
            password.length < 6 -> "Mínimo 6 caracteres"
            !password.any { it.isDigit() } -> "Debe contener al menos un número"
            !password.any { it.isLetter() } -> "Debe contener al menos una letra"
            else -> null
        }
    }

    fun getPhoneError(phone: String): String? {
        return when {
            phone.isBlank() -> "El teléfono no puede estar vacío"
            !phone.all { it.isDigit() } -> "Solo se permiten números"
            phone.length != 10 -> "El teléfono debe tener 10 dígitos"
            else -> null
        }
    }
}