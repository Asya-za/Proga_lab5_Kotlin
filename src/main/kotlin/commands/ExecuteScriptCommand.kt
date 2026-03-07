package commands

import collection.CollectionManager
import collection.CommandManager

class ExecuteScriptCommand(
    private val collectionManager: CollectionManager,
    private val commandManager: CommandManager
) : Command {

    override val name = "execute_script"
    override val description = "выполнить скрипт из файла"

    override fun execution(args: List<String>) {

        if (args.isEmpty()) {
            println("Укажите файл")
            return
        }

        val fileName = args[0]
        collectionManager.executeScript(fileName)
    }
}