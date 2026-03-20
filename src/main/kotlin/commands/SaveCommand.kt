package commands

import collection.CollectionManager
import io.IOManager

class SaveCommand(private val collectionManager: CollectionManager, private val io: IOManager) : Command {

    override val name = "save"
    override val description = "сохранить коллекцию в файл"

    override fun execution(args: List<String>) {
        val fileName = args[0]
        if (args.isEmpty()){
            io.println("Ошибка: нужно указать имя файла")
            return
        }
        collectionManager.save(fileName)
    }
}