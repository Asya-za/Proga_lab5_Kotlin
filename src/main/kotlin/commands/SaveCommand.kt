package commands

import collection.CollectionManager

class SaveCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "save"
    override val description = "сохранить коллекцию в файл"

    override fun execution(args: List<String>) {
        collectionManager.save()
    }
}