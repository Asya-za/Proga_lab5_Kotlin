package commands

import kotlin.system.exitProcess

class ExitCommand : Command {
    override val name = "exit"
    override val description = "завершить программу"

    override fun execution(args: List<String>) {
        println("Программа завершена")
        exitProcess(0)
    }
}