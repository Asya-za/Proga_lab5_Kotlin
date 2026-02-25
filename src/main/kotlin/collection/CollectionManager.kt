package collection

import model.Dragon
import java.time.LocalDateTime
import java.util.Hashtable

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
}