package commands

import collection.CollectionManager
//import generation.IdGenerator
import model.*
import java.time.LocalDateTime
//import java.util.Scanner

class InsertCommand (private val collectionManager: CollectionManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            println("Необходимо указать ключ после названия команды")
            return
        }


        val key: Long
        try {
            key = args[0].toLong()
        }
        catch (e: NumberFormatException){
            println("Ключ должен быть числом")
            return
        }

        if (collectionManager.Storage.containsKey(key)) {
            println("Уже существует элемент с таким ключом")
            return
        }


        println("Введите имя")
        val nameDragon = readln()

        var x: Float
        while (true) {
            println("Введите коррдинату x")
            try {
                x = readln().toFloat()
                break
            } catch (e: NumberFormatException) {
                println("Кордината должна быть числом")
            }
        }


        var y: Long
        while (true) {
            println("Введите координату y")
            try {
                y = readln().toLong()
                break
            } catch (e: NumberFormatException) {
                println("Кордината должна быть числом")
            }
        }


        var age: Long
        while (true) {
            println("Введите возраст")
            try {
                age = readln().toLong()
                break
            } catch (e: NumberFormatException) {
                println("Возраст должен быть числом")
            }
        }

        var weight: Double
        while (true) {
            println("Введите вес")
            try {
                weight = readln().toDouble()
                break
            } catch (e: NumberFormatException) {
                println("Вес должен быть числом")
            }
        }


        var type: DragonType
        while (true) {
            println("Выберете тип дракона: WATER, UNDERGROUND, AIR, FIRE")
            try {
                type = DragonType.valueOf(readln())
                break
            } catch (e: Exception) {
                println("Неверный тип дракона")
            }
        }



        var character: DragonCharacter
        while (true) {
            println("Выберете характер дракона: WISE, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE")
            try {
                character = DragonCharacter.valueOf(readln())
                break
            } catch (e: Exception) {
                println("Неверный тип дракона")
            }
        }

        val dragon = Dragon(
            id = (collectionManager.Storage.values.maxOfOrNull { it.id } ?: 0) + 1,
            name = nameDragon,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = null
        )

        collectionManager.Storage[key] = dragon

        println("Дракон добавлен")
    }
}