package commands

import collection.CollectionManager
import io.IOManager

class ClearCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "clear"
    override val description = "очистить коллекцию"

    override fun execution(args: List<String>) {
        collectionManager.clear()
        io.println("Коллекция очищена")
    }
}