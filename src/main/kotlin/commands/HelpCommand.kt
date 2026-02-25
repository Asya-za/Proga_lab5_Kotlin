package commands


import collection.CommandManager

class HelpCommand(private val commandManager: CommandManager) : Command {
    override val name = "help"
    override val description = "вывести справку по доступным командам"

    override fun execution(args: List<String>) {
        println("Доступные команды:")

        for (command in commandManager.allCommands()) {
            println("${command.name}: ${command.description}")
        }
    }
}