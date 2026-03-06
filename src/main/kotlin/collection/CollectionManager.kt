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
        if (storage.isEmpty()) {
            println("Элементы не найдены")
            return
        }

        println("По какому параметру сортировать (id, x, y, creationDate, age, weight, eyesCount, toothCount)?")
        val param = readln()
        var sortedList = storage.values.toList()

        if (param == "id") {
            sortedList = storage.values.sortedBy { it.id }
        }
        else if (param == "x") {
            sortedList = storage.values.sortedBy { it.coordinates.x }
        }
        else if (param == "y") {
            sortedList = storage.values.sortedBy { it.coordinates.y }
        }
        else if (param == "creationDate") {
            sortedList = storage.values.sortedBy { it.creationDate }
        }
        else if (param == "age") {
            sortedList = storage.values.sortedBy { it.age }
        }
        else if (param == "weight") {
            sortedList = storage.values.sortedBy { it.weight }
        }
        else if (param == "eyesCount") {
            sortedList = storage.values.sortedBy { it.head?.eyesCount }
        }
        else if (param == "toothCount") {
            sortedList = storage.values.sortedBy { it.head?.toothCount }
        }
        else {
            println("Неверный параметр")
            return
        }

        for (dragon in sortedList) {
            println(dragon)
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

    fun removeGreater() {
        if (storage.isEmpty()) {
            println("Коллекция пуста")
            return
        }

        println("По какому параметру сравнивать (id, x, y, toothCount, age, weight, eyesCount)?")

        val param = readln()

        println("Введите значение для сравнения")

        val value = readln().toDouble()
        val keysToRemove = mutableListOf<Long>()

        for ((key, dragon) in storage) {

            if (param == "id") {
                if (dragon.id > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "x") {
                if (dragon.coordinates.x > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "y") {
                if (dragon.coordinates.y > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "toothCount") {
                if ((dragon.head?.toothCount ?: 0.0) > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "age") {
                if (dragon.age > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "weight") {
                if (dragon.weight > value) {
                    keysToRemove.add(key)
                }
            }

            else if (param == "eyesCount") {
                if ((dragon.head?.eyesCount ?: 0) > value) {
                    keysToRemove.add(key)
                }
            }

            else {
                println("Неверный параметр")
                return
            }
        }

        for (k in keysToRemove) {
            storage.remove(k)
        }

        println("Удалено элементов: ${keysToRemove.size}")
    }

    fun replaceIfGreater() {

        if (storage.isEmpty()) {
            println("Коллекция пуста")
            return
        }

        println("Введите ключ элемента для замены:")
        val key = readln().toLong()
        val current = storage[key]
        if (current == null) {
            println("Элемент с таким ключом не найден")
            return
        }

        println("По какому параметру сравнивать (id, x, y, toothCount, age, weight, eyesCount)?")
        val param = readln()

        var isReplaced = false

        if (param == "id") {
            val value = readInt("Введите новое значение id")
            if (value > current.id) {
                current.id = value
                isReplaced = true
            }
        }
        else if (param == "x") {
            val value = readFloat("Введите новое значение x")
            if (value > current.coordinates.x) {
                current.coordinates.x = value
                isReplaced = true
            }
        }
        else if (param == "y") {
            val value = readLong("Введите новое значение y")
            if (value > current.coordinates.y) {
                current.coordinates.y = value
                isReplaced = true
            }
        }
        else if (param == "age") {
            val value = readLong("Введите новый возраст")
            if (value > current.age) {
                current.age = value
                isReplaced = true
            }
        }
        else if (param == "weight") {
            val value = readDouble("Введите новый вес")
            if (value > current.weight) {
                current.weight = value
                isReplaced = true
            }
        }
        else if (param == "eyesCount") {
            val value = readInt("Введите новое количество глаз")
            val head = current.head
            if (head == null) {
                println("У элемента нет головы")
                return
            }
            if (value > head.eyesCount) {
                head.eyesCount = value
                isReplaced = true
            }
        }
        else if (param == "toothCount") {
            val value = readDouble("Введите новое количество зубов")
            val head = current.head
            if (head == null) {
                println("У элемента нет головы")
                return
            }
            if (value > head.toothCount) {
                head.toothCount = value
                isReplaced = true
            }
        }
        else {
            println("Неверный параметр")
            return
        }

        if (isReplaced) {
            println("Элемент успешно заменён")
        } else {
            println("Новое значение не больше старого, замена не выполнена")
        }
    }
}
