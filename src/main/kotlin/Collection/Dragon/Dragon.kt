data class Dragon(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: LocalDateTime,
    val age: Long,
    val weight: Double,
    val type: DragonType,
    val character: DragonCharacter?,/
    val head: DragonHead
)