package commands

interface Command {
    val name: String
    val description: String

    fun execution(args: List <String>)
}