package commands

import collection.CollectionManager


class InfoCommand(private val collectionManager: CollectionManager) : Command {
    override val name = "info"
    override val description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов, файл)"

    override fun execution(args: List<String>) {
        println("Тип коллекции: java.util.Hashtable")
        println("Дата инициализации: ${collectionManager.time}")
        println("Количество элементов: ${collectionManager.size()}")
        //println("Файл: ${collectionManager.FileName}")
    }
}