package collection

import model.Dragon
import java.time.LocalDateTime
import java.util.Hashtable
import com.google.gson.GsonBuilder
import java.io.FileWriter
import collection.FileManager
import model.Coordinates
import model.DragonCharacter
import model.DragonHead
import model.DragonType


class CollectionManager (val time: LocalDateTime, val fileName: String) {
    val storage: Hashtable<Long, Dragon> = Hashtable()

    fun Size(): Int = storage.size

    fun ShowAll() {
        if (storage.isEmpty()) {
            println("Коллекция пустая")
            return
        }
        for (entry in storage.entries) {
            println("Ключ = ${entry.key}")
            println("Значение = ${entry.value}")
        }
    }

    fun clear() {
        storage.clear()
    }

    fun removeByKey(key: Long) {
        if (storage.containsKey(key)) {
            storage.remove(key)
            println("Элемент удалён")
        } else {
            println("Ключ не найден")
        }
    }

    fun printAscending() {
        val sortedList = storage.values.sorted()

        if (sortedList.isEmpty()) {
            println("Элементы не найдены")
        } else {
            for (dragon in sortedList) {
                println(dragon)
            }

        }
    }

    fun filterStartsWithName(prefix: String) {
        val filtered = storage.values.filter { it.name.startsWith(prefix) }

        if (filtered.isEmpty()) {
            println("Элементы не найдены")
        } else {
            filtered.forEach { println(it) }
        }
    }

    fun groupCountingById() {
        val grouped = storage.values.groupingBy { it.id }.eachCount()

        if (grouped.isEmpty()) {
            println("Коллекция пуста")
        } else {
            grouped.forEach { (id, count) ->
                println("ID: $id -> количество: $count")
            }
        }
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, FileManager.LocalDateTimeAdapter())
        .setPrettyPrinting()
        .create()

    fun save() {
        try {
            val fileWriter = FileWriter(fileName)
            gson.toJson(storage, fileWriter)
            fileWriter.close()
            println("Коллекция сохранена")
        } catch (e: Exception) {
            println("Ошибка сохранения: ${e.message}")
        }
    }


    fun loadCollectionFromFile(fileManager: FileManager) {
        val elements = fileManager.readCollection()
        for ((key, dragon) in elements) {
            storage[key] = dragon.copy(id = nextId())
        }
    }

    private var nextId: Int = 1
    fun size(): Int = storage.size
    fun nextId(): Int {
        val id = nextId
        nextId++
        return id
    }



    fun readLong(message: String): Long {
        while (true) {
            println(message)
            try {
                var value = readln().toLong()
                return value
            } catch (e: NumberFormatException) {
                println("Неверный ввод, должно быть число типа Long")
            }

        }
    }

    fun readInt(message: String): Int {
        while (true) {
            println(message)
            try {
                var value = readln().toInt()
                return value
            } catch (e: NumberFormatException) {
                println("Неверный ввод, должно быть число типа Int")
            }

        }
    }

    fun readFloat(message: String): Float {
        while (true) {
            println(message)
            try {
                var value = readln().toFloat()
                return value
            } catch (e: NumberFormatException) {
                println("Неверный ввод, должно быть число типа Float")
            }
        }
    }

    fun readDouble(message: String): Double {
        while (true) {
            println(message)
            try {
                var value = readln().toDouble()
                return value
            } catch (e: NumberFormatException) {
                println("Неверный ввод, должно быть число типа Double")
            }

        }
    }



    fun updateById(id: Long, newDragon: Dragon) {
        var keyToUpdate: Long? = null
        var oldDragon: Dragon? = null

        for ((key, dragon) in storage) {
            if (dragon.id.toLong() == id) {
                keyToUpdate = key
                oldDragon = dragon
                break
            }
        }

        if (keyToUpdate == null || oldDragon == null) {
            println("Элемент с таким id не найден")
            return
        }

        val updatedDragon = newDragon.copy(
            id = oldDragon.id,
            creationDate = oldDragon.creationDate
        )

        storage[keyToUpdate] = updatedDragon
        println("Элемент обновлён")
    }

    fun createDragon(id: Int): Dragon {

        println("Введите имя")
        val nameDragon = readln()

        val x = readFloat("Введите коррдинату x")
        val y = readLong("Введите коррдинату y")
        val age = readLong("Введите возраст")
        val weight = readDouble("Введите вес")

        var type: DragonType
        while (true) {
            println("Выберете тип дракона: water,underground, air, fire")
            try {
                type = DragonType.valueOf(readln().uppercase())
                break
            } catch (e: Exception) {
                println("Неверный тип дракона")
            }
        }

        var character: DragonCharacter
        while (true) {
            println("Выберете характер дракона: wise, good, chaotic, chaotic_evil, fickle")
            try {
                character = DragonCharacter.valueOf(readln().uppercase())
                break
            } catch (e: Exception) {
                println("Неверный характер дракона")
            }
        }

        println("Создать голову? yes/no")
        val answer = readln().lowercase()
        val head: DragonHead?
        if (answer == "yes") {
            val eyesCount = readInt("Введите количество глаз")
            val toothCount = readDouble("Введите количество зубов")
            head  = DragonHead(eyesCount, toothCount)
        }
        else {
            head = null
        }

        return Dragon(
            id = id,
            name = nameDragon,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = head
        )
    }

    fun removeGreaterKey(key: Long) {
        val keysToRemove = mutableListOf<Long>()
        for (k in storage.keys) {
            if (k > key) {
                keysToRemove.add(k)
            }
        }
        if (keysToRemove.isEmpty()) {
            println("Нет элементов с ключом больше этого")
            return
        }
        for (k in keysToRemove) {
            storage.remove(k)
        }

        println("Удалено элементов: ${keysToRemove.size}")
    }
}
