package commands

import collection.CollectionManager

class ReplaceIfGreaterCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "replace_if_greater"
    override val description = "заменить значение по ключу, если новое значение больше старого"

    override fun execution(args: List<String>) {
        collectionManager.replaceIfGreater()
    }
}