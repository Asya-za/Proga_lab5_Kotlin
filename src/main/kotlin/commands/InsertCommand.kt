package commands

import collection.CollectionManager
//import generation.IdGenerator
import model.*
import java.time.LocalDateTime
import exceptions.ValidationException
import model.DragonHead


class InsertCommand (private val collectionManager: CollectionManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    fun readLong(message: String): Long {
        while (true) {
            println(message)
            try {
                var value = readln().toLong()
                return value
            }
            catch (e: NumberFormatException){
                println("Неверный ввод")
            }

        }
    }

    fun readInt(message: String): Int {
        while (true) {
            println(message)
            try {
                var value = readln().toInt()
                return value
            }
            catch (e: NumberFormatException){
                println("Неверный ввод")
            }

        }
    }

    fun readFloat(message: String): Float {
        while (true) {
            println(message)
            try {
                var value = readln().toFloat()
                return value
            }
            catch (e: NumberFormatException){
                println("Неверный ввод")
            }
        }
    }

    fun readDouble(message: String): Double {
        while (true) {
            println(message)
            try {
                var value = readln().toDouble()
                return value
            }
            catch (e: NumberFormatException){
                println("Неверный ввод")
            }

        }
    }

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            println("Необходимо указать ключ")
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

        if (collectionManager.storage.containsKey(key)) {
            println("Уже существует элемент с таким ключом")
            return
        }

        println("Введите имя")
        val nameDragon = readln()

        val x = readFloat("Введите коррдинату x")
        val y = readLong("Введите коррдинату y")
        val age = readLong("Введите возраст")
        val weight = readDouble("Введите вес")

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

        val eyesCount = readInt("Введите количество глаз")
        val toothCount = readDouble("Введите количество зубов")

        try {
            val dragon = Dragon(
                id = 1,
                name = nameDragon,
                coordinates = Coordinates(x, y),
                creationDate = LocalDateTime.now(),
                age = age,
                weight = weight,
                type = type,
                character = character,
                head = DragonHead(eyesCount, toothCount)
            )

            collectionManager.storage[key] = dragon

            println("Дракон добавлен")
        }
        catch (e: ValidationException) {
            println("Ошибка: ${e.message}")
        }
    }
}