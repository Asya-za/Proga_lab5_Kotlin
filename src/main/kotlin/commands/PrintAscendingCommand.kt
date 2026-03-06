package commands

import collection.CollectionManager

class PrintAscendingCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "print_ascending"
    override val description = "вывести элементы в порядке возрастания"

    override fun execution(args: List<String>) {
        collectionManager.printAscending()
    }
}