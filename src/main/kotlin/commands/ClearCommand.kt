package commands

import collection.CollectionManager

class ClearCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "clear"
    override val description = "очистить коллекцию"

    override fun execution(args: List<String>) {
        collectionManager.clear()
        println("Коллекция очищена")
    }
}