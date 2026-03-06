package commands

import collection.CollectionManager

class RemoveGreaterKeyCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "remove_greater_key"
    override val description = "удалить из коллекции все элементы, ключ которых больше заданного"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            println("Необходимо указать ключ")
            return
        }

        val key: Long

        try {
            key = args[0].toLong()
        } catch (e: NumberFormatException) {
            println("Ключ должен быть числом")
            return
        }

        collectionManager.removeGreaterKey(key)
    }
}