package commands

import collection.CollectionManager
import exceptions.ValidationException

class UpdateCommand(private val collectionManager: CollectionManager) : Command {
    override val name = "update"
    override val description = "обновить элемент по id"

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            println("Необходимо указать id")
            return
        }

        val id: Long
        try {
            id = args[0].toLong()
        } catch (e: NumberFormatException) {
            println("id должен быть числом")
            return
        }

        try {
            val dragon = collectionManager.createDragon(id.toInt())
            collectionManager.updateById(id, dragon)
        }
        catch (e: ValidationException) {
            println("Ошибка: ${e.message}")
        }
    }
}