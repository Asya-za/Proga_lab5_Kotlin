package model
import java.time.LocalDateTime
import exceptions.ValidationException

data class Dragon(
    var id: Int, // уникальное значение >0, не null, генерируется автоматически
    var name: String, // не null, не пустая строка
    var coordinates: Coordinates, // не null
    var creationDate: LocalDateTime, // не null, генерируется автоматически
    var age: Long, // >0
    var weight: Double, // >0
    var type: DragonType, // не null
    var character: DragonCharacter, // не null
    var head: DragonHead?
) : Comparable<Dragon> {

    override fun compareTo(other: Dragon): Int {
        return this.id.compareTo(other.id)
    }

    init {
        if (name.isBlank()) {
            throw ValidationException("Имя не может быть пустым")
        }
        if(age <= 0) {
            throw ValidationException("Возраст должен быть больше 0")
        }
        if (weight <= 0) {
            throw ValidationException("Вес должен быть больше 0")
        }

    }
}