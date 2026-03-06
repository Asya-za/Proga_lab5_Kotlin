package commands

import collection.CollectionManager

class RemoveKeyCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "remove_key"
    override val description = "удалить элемент по ключу"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            println("Введите ключ")
            return
        }

        val key = args[0].toLong()
        collectionManager.removeByKey(key)
    }
}