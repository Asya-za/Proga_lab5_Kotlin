package model

data class Coordinates(
    val x: Float,   // максимум 523
    val y: Long     // не null
) {
    init {
        require(x <= 523f)
    }
}