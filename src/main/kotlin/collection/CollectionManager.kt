package collection

import model.Dragon
import java.time.LocalDateTime
import java.util.Hashtable
import com.google.gson.*
import java.io.FileWriter

class CollectionManager (val Time: LocalDateTime, val FileName: String) {
    val Storage: Hashtable <Long, Dragon> = Hashtable()

    fun Size(): Int = Storage.size

    fun ShowAll() {
        if (Storage.isEmpty()) {
            println("Коллекция пустая")
            return
        }
        for (entry in Storage.entries) {
            println("Ключ = ${entry.key}")
            println("Значение = ${entry.value}")
        }
    }

    fun clear() {
        Storage.clear()
    }

    fun removeByKey(key: Long) {
        if (Storage.containsKey(key)) {
            Storage.remove(key)
            println("Элемент удалён")
        } else {
            println("Ключ не найден")
        }
    }

    fun printAscending() {
        if (Storage.isEmpty()) {
            println("Элементы не найдены")
            return
        }

        println("Введите поле для сортировки (id, name, age, weight):")
        val field = readLine()
        val sortedList = if (field == "id") {
            Storage.values.sortedBy { it.id }
        } else if (field == "name") {
            Storage.values.sortedBy { it.name }
        } else if (field == "age") {
            Storage.values.sortedBy { it.age }
        } else if (field == "weight") {
            Storage.values.sortedBy { it.weight }
        } else {
            println("Неизвестное поле. Сортировка по id.")
            Storage.values.sortedBy { it.id }
        }

        for (dragon in sortedList) {
            println(dragon)
        }
    }

    fun filterStartsWithName(prefix: String) {
        val filtered = Storage.values.filter { it.name.startsWith(prefix) }

        if (filtered.isEmpty()) {
            println("Элементы не найдены")
        } else {
            filtered.forEach { println(it) }
        }
    }

    fun groupCountingById() {
        val grouped = Storage.values.groupingBy { it.id }.eachCount()

        if (grouped.isEmpty()) {
            println("Коллекция пуста")
        } else {
            grouped.forEach { (id, count) ->
                println("ID: $id - количество: $count")
            }
        }
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java,
            JsonSerializer<LocalDateTime> { src, _, _ ->
                JsonPrimitive(src.toString())
            })
        .registerTypeAdapter(LocalDateTime::class.java,
            JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asString)
            })
        .setPrettyPrinting()
        .create()

    fun save() {
        try {
            val fileWriter = FileWriter(FileName)
            gson.toJson(Storage, fileWriter)
            fileWriter.close()
            println("Коллекция сохранена")
        } catch (e: Exception) {
            println("Ошибка сохранения: ${e.message}")
        }
    }
}