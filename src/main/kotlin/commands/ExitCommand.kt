package commands
import exceptions.ExitException
import io.IOManager

import kotlin.system.exitProcess

class ExitCommand(private val io: IOManager) : Command {
    override val name = "exit"
    override val description = "завершить программу"

    override fun execution(args: List<String>) {
        io.println("Программа завершена")
        throw ExitException()
    }
}