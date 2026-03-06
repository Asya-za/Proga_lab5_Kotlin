package commands

import collection.CollectionManager

class InsertCommand(private val collectionManager: CollectionManager) : Command {

    override val name = "insert"
    override val description = "добавить новый элемент с заданным ключом"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            println("Необходимо указать ключ")
            return
        }

        val key: Long
        try {
            key = args[0].toLong()
        } catch (e: NumberFormatException) {
            println("Ключ должен быть числом")
            return
        }

        val dragon = collectionManager.createDragon()

        if (!collectionManager.storage.containsKey(key)) {
            collectionManager.storage[key] = mutableListOf()
        }

        collectionManager.storage[key]?.add(dragon)

        println("Дракон добавлен")
    }
}