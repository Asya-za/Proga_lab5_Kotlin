package collection

import commands.Command

class CommandManager {
    private val commands: MutableMap <String, Command> = linkedMapOf()

    fun addToList (command: Command) {
        commands[command.name] = command
    }


    fun execution (name: String, args: List<String>): Boolean {
        val command = commands[name]
        if (command == null) {
            return false
        }
        command.execution(args)
        return true
    }

    fun allCommands(): List<Command> = commands.values.toList()
}