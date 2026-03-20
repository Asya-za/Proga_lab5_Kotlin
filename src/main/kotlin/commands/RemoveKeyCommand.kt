package commands

import collection.CollectionManager
import io.IOManager

class RemoveKeyCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "remove_key"
    override val description = "удалить элемент по ключу"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            io.println("Введите ключ")
            return
        }

        val key = args[0].toLong()
        collectionManager.removeByKey(key)
    }
}