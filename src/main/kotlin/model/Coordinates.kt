package model

import exceptions.ValidationException

data class Coordinates(
    val x: Float,   // максимум 523
    val y: Long     // не null
) {
    init {
        if (x > 523) {
            throw ValidationException("Координата x должна быть <= 523")
        }
    }
}