package collection

import model.Dragon
import model.Coordinates
import model.DragonType
import model.DragonCharacter
import java.time.LocalDateTime
import java.util.Hashtable
import com.google.gson.GsonBuilder
import java.io.FileWriter

class CollectionManager(val time: LocalDateTime, val fileName: String) {

    val storage: Hashtable<Long, MutableList<Dragon>> = Hashtable()

    fun size(): Int {
        return storage.values.flatten().size
    }

    fun showAll() {
        if (storage.isEmpty()) {
            println("Коллекция пустая")
            return
        }

        for ((key, list) in storage) {
            for (dragon in list) {
                println("Ключ = $key")
                println("Значение = $dragon")
            }
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
        val sortedList = storage.values.flatten().sorted()

        if (sortedList.isEmpty()) {
            println("Элементы не найдены")
        } else {

            for (dragon in sortedList) {
                println(dragon)
            }
        }
    }

    fun filterStartsWithName(prefix: String) {
        val filtered = storage.values.flatten().filter {
            it.name.startsWith(prefix)
        }

        if (filtered.isEmpty()) {
            println("Элементы не найдены")
        } else {
            for (dragon in filtered) {
                println(dragon)
            }
        }
    }

    fun groupCountingById() {
        val grouped = storage.values
            .flatten()
            .groupingBy { it.id }
            .eachCount()

        if (grouped.isEmpty()) {
            println("Коллекция пуста")
        } else {
            for ((id, count) in grouped) {
                println("ID: $id -> количество: $count")
            }
        }
    }

    fun createDragon(): Dragon {

        println("Введите имя")
        val name = readln()

        var x: Float
        while (true) {
            println("Введите координату x")
            try {
                x = readln().toFloat()
                break
            } catch (e: Exception) {
                println("Координата должна быть числом")
            }
        }

        var y: Long
        while (true) {
            println("Введите координату y")
            try {
                y = readln().toLong()
                break
            } catch (e: Exception) {
                println("Координата должна быть числом")
            }
        }

        var age: Long
        while (true) {
            println("Введите возраст")
            try {
                age = readln().toLong()
                break
            } catch (e: Exception) {
                println("Возраст должен быть числом")
            }
        }

        var weight: Double
        while (true) {
            println("Введите вес")
            try {
                weight = readln().toDouble()
                break
            } catch (e: Exception) {
                println("Вес должен быть числом")
            }
        }

        var type: DragonType
        while (true) {
            println("Тип: WATER, UNDERGROUND, AIR, FIRE")
            try {
                type = DragonType.valueOf(readln())
                break
            } catch (e: Exception) {
                println("Неверный тип")
            }
        }

        var character: DragonCharacter
        while (true) {
            println("Характер: WISE, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE")
            try {
                character = DragonCharacter.valueOf(readln())
                break
            } catch (e: Exception) {
                println("Неверный характер")
            }
        }

        val id = (storage.values.flatten().maxOfOrNull { it.id } ?: 0) + 1

        return Dragon(
            id = id,
            name = name,
            coordinates = Coordinates(x, y),
            creationDate = LocalDateTime.now(),
            age = age,
            weight = weight,
            type = type,
            character = character,
            head = null
        )
    }

    fun updateById(id: Long, newDragon: Dragon) {
        for ((key, list) in storage) {
            for (i in list.indices) {
                if (list[i].id.toLong() == id) {
                    list[i] = newDragon
                    println("Элемент обновлён")
                    return
                }
            }
        }

        println("Элемент с таким id не найден")
    }

    fun loadCollectionFromFile() {
        val fileManager = FileManager(fileName)
        val lines = fileManager.readfile()

        for (l in lines) {
            try {
                val pair = fileManager.decode(l)
                val key = pair.first
                val dragon = pair.second

                if (storage.containsKey(key)) {
                    storage[key]?.add(dragon)
                } else {
                    storage[key] = mutableListOf(dragon)
                }

            } catch (e: Exception) {
                println("Ошибка чтения строки: $l")
            }
        }
    }

    private val gson = GsonBuilder().create()

    fun save() {
        try {
            val fileWriter = FileWriter(fileName)
            gson.toJson(storage, fileWriter)
            fileWriter.close()
            println("Коллекция сохранена")
        } catch (e: Exception) {
            println("Ошибка сохранения")
        }

    }
}