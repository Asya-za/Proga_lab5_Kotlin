package commands

import collection.CollectionManager
//import generation.IdGenerator
import model.*
import java.time.LocalDateTime
import exceptions.ValidationException
import model.DragonHead


class InsertCommand(private val collectionManager: CollectionManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

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

        try {
            val dragon = collectionManager.createDragon(collectionManager.nextId())
                collectionManager.storage[key] = dragon
            println("Дракон добавлен")
        }
        catch (e: ValidationException) {
            println("Ошибка: ${e.message}")
        }
    }
}