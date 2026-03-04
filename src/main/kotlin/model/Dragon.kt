package model
import java.time.LocalDateTime

data class Dragon(
    val id: Int, // уникальное значение >0, не null, генерируется автоматически
    val name: String, // не null, не пустая строка
    val coordinates: Coordinates, // не null
    val creationDate: LocalDateTime, // не null, генерируется автоматически
    val age: Long, // >0
    val weight: Double, // >0
    val type: DragonType, // не null
    val character: DragonCharacter, // не null
    val head: DragonHead?
) : Comparable<Dragon> {

    override fun compareTo(other: Dragon): Int {
        return this.id.compareTo(other.id)
    }

    init {
        require(id > 0) {"id должен быть больше нуля"}
        require(name.isNotBlank()) {"имя не может быть пустым"}
        require(age > 0) {"возраст должен быть больше нуля"}
        require(weight > 0) {"weight должен быть больше нуля"}
    }
}