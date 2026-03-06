package collection

import model.Dragon
import java.time.LocalDateTime
import java.util.Hashtable
import com.google.gson.GsonBuilder
import java.io.FileWriter
import collection.FileManager


class CollectionManager (val time: LocalDateTime, val fileName: String) {
    val storage: Hashtable <Long, Dragon> = Hashtable()

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


    fun loadCollectionFromFile(fileManager: FileManager) {
        val elements = fileManager.readCollection()
        for ((key, dragon) in elements) {
            storage[key] = dragon
        }
    }

    private var nextId: Int = 1
    fun size(): Int = storage.size
    fun nextId(): Int {
        val id = nextId
        nextId++
        return id
    }

    fun updateNextId() {
        var maxId = 0
        for (dragon in storage.values) {
            if (dragon.id > maxId) maxId = dragon.id
        }
        nextId = maxId + 1
        if (nextId < 1) nextId = 1
    }
}