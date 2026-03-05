package commands

import collection.CollectionManager

class FilterStartsWithNameCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "filter_starts_with_name"
    override val description = "вывести элементы, имя которых начинается с подстроки"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            println("Введите строку")
            return
        }

        val prefix = args[0]
        collectionManager.filterStartsWithName(prefix)
    }
}