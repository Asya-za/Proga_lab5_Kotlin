package collection

import model.*
import java.io.File
import java.time.LocalDateTime

class FileManager (private val fileName: String) {
    fun readfile(): List<String> {
        val file = File(fileName)
        if (!file.exists()) {
            return emptyList()
        }

        val lines = file.readLines(Charsets.UTF_8)
        return lines.filter { it.isNotBlank() }
    }

    fun decode(line: String): Pair<Long, Dragon> {
        val parts = line.split("line")
        if (parts.size != 12) {
            throw IllegalArgumentException("Неверный формат строки")
        }

        val key = parts[0].toLong()
        val id = parts[1].toInt()
        val name = parts[2]

        val x = parts[3].toFloat()
        val y = parts[4].toLong()

        val age = parts[5].toLong()
        val weight = parts[6].toDouble()
        val creationDate = LocalDateTime.parse(parts[7])
        val type = DragonType.valueOf(parts[8])
        val charter = DragonCharacter.valueOf(parts[9])

        val eyesCount = parts[10].toInt()
        val toothCount = parts[11].toDouble()
        val head = DragonHead(eyesCount, toothCount)

        val dragon = Dragon(id, name, Coordinates(x, y), creationDate, age, weight, type, charter, head)

        return Pair(key, dragon)
    }

    fun encode(key: Long, dragon: Dragon): String {
        return listOf(key.toString(),
            dragon.id.toString(),
            dragon.name,
            dragon.coordinates.x.toString(),
            dragon.coordinates.y.toString(),
            dragon.creationDate.toString(),
            dragon.age.toString(),
            dragon.weight.toString(),
            dragon.type.toString(),
            dragon.character.toString(),
            dragon.head?.eyesCount.toString(),
            dragon.head?.toothCount.toString()
            ).joinToString(";")
    }

    private fun escape(s: String): String {
        return s.replace("\\", "\\\\").replace(";", "\\;")
    }
    private fun unescape(s: String): String {
        return s.replace("\\;", ";").replace("\\\\", "\\")
    }


    private fun split(line: String): List<String> {
        val result = mutableListOf<String>()
        var current = ""
        var isBackslash = false

        for (l in line) {
            if (isBackslash) {
                current += l
                isBackslash = false
            }
            else if (l == '\\') {
                isBackslash = true
            }
            else if (l == ';') {
                result.add(current)
                current = ""
            } else {
                current += l
            }
        }

        result.add(current)
        return result
    }
}