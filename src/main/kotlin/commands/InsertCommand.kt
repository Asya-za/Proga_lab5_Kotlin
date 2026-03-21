package commands

import collection.CollectionManager
//import generation.IdGenerator
import model.*
import java.time.LocalDateTime
import exceptions.ValidationException
import collection.IOManager
import model.DragonHead


class InsertCommand(private val collectionManager: CollectionManager, private val io: IOManager): Command {
    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            io.println("Необходимо указать ключ")
            return
        }

        val key: Long
        try {
            key = args[0].toLong()
        }
        catch (e: NumberFormatException){
            io.println("Ключ должен быть числом")
            return
        }

        if (collectionManager.storage.containsKey(key)) {
            io.println("Уже существует элемент с таким ключом")
            return
        }

        try {
            val dragon = collectionManager.createDragon(collectionManager.nextId())
                collectionManager.storage[key] = dragon
            io.println("Дракон добавлен")
        }
        catch (e: ValidationException) {
            io.println("Ошибка: ${e.message}")
        }
    }
}