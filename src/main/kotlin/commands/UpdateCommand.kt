package commands

import collection.CollectionManager


class UpdateCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "update"
    override val description = "обновить элемент по id"

    override fun execution(args: List<String>) {
        if (args.isEmpty()) {
            println("Введите id")
            return
        }

        val id = args[0].toLong()
        val dragon = collectionManager.createDragon()
        collectionManager.updateById(id, dragon)
    }
}