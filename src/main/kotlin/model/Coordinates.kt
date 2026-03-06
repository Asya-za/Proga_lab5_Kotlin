package model

import exceptions.ValidationException

data class Coordinates(
    var x: Float,   // максимум 523
    var y: Long     // не null
) {
    init {
        if (x > 523) {
            throw ValidationException("Координата x должна быть <= 523")
        }
    }
}