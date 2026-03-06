package collection

import model.*
import java.io.File
import java.time.LocalDateTime
import java.io.BufferedInputStream
import com.google.gson.*
import java.lang.reflect.Type


class FileManager (private val fileName: String) {

    private val file = File(fileName)
    private val gson: Gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter()).create()



    fun readfile(): String {
        if (!file.canRead()){
            throw IllegalStateException("Нет прав на чтение файла: $fileName")
        }
        val inputStream = BufferedInputStream(file.inputStream())
        val text = inputStream.bufferedReader().readText()
        inputStream.close()
        return text
    }

    fun readCollection(): List<Pair<Long, Dragon>> {
        val fileJson = readfile().trim() ////убираем пробелы и всякую такую шняжку
        if (fileJson.isEmpty()) return emptyList()

        try {
            val root = gson.fromJson(fileJson, JsonElement::class.java)
            if (root == null) {
                return emptyList()
            }

            if (!root.isJsonObject) {
                throw IllegalArgumentException("Неверный формат ввода")
            }

            return parseObjectFormat(root.asJsonObject)
        }
        catch (e: JsonParseException) {
            throw IllegalArgumentException("Файл не является корректным JSON: ${e.message}")
        }
    }

    private fun  parseObjectFormat (obj: JsonObject): List<Pair<Long, Dragon>> {
        val result = mutableListOf<Pair<Long, Dragon>>()

        for (entry in obj.entrySet()) {
            val keyString = entry.key
            val value = entry.value


            val key = keyString.toLongOrNull()
            if (key == null) {
                throw IllegalArgumentException("Ключ '$keyString' должен быть числом")
            }
            val dragon = gson.fromJson(value, Dragon::class.java)
            result.add(Pair(key, dragon))

        }
        return result
    }



    private fun parseFormat(arr: JsonArray): List<Pair<Long, Dragon>> {
        val result = mutableListOf<Pair<Long, Dragon>>()

        for (element in arr) {
            val obj = element.asJsonObject
            val key = obj.get("key").asLong
            val dragonJson = obj.get("dragon")

            val dragon = gson.fromJson(dragonJson, Dragon::class.java)
            result.add(Pair(key, dragon))
        }
        return result
    }

    class LocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        override fun serialize(
            src: LocalDateTime?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.toString())
        }

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): LocalDateTime {
            val text = json?.asString ?: throw JsonParseException("Нет даты")
            return LocalDateTime.parse(text)
        }
    }
}
