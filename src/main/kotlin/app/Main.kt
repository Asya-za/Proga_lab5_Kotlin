package app

import commands.ExitCommand
import commands.HelpCommand
import commands.InfoCommand
import commands.ShowCommand
import collection.CollectionManager
import collection.CommandManager
import java.time.LocalDateTime
import java.util.Scanner
import java.io.PrintStream

fun main(args: Array<String>) {
    System.setOut(PrintStream(System.out, true, "UTF-8"))
    System.setErr(PrintStream(System.err, true, "UTF-8"))

    if (args.isEmpty()) {
        System.err.println("Ошибка: нужно передать имя файла")
        return
    }



    val fileName = args[0]
    val initializationTime = LocalDateTime.now()
    val collectionManager = CollectionManager(initializationTime, fileName)
    val commandManager = CommandManager()


    commandManager.addToList(HelpCommand(commandManager))
    commandManager.addToList(InfoCommand(collectionManager))
    commandManager.addToList(ShowCommand(collectionManager))
    commandManager.addToList(ExitCommand())

    println("Программа запущена")
    println("Файл: $fileName")



    val scan = Scanner(System.`in`)
    while (true) {
        if (!scan.hasNextLine()) break
        val line = scan.nextLine().trim() ///убираем пррбелы

        if (line.isEmpty()) continue

        val commandDecoding = line.split(Regex("\\s+"))
        val nameCommand = commandDecoding[0]
        val commandArgs = commandDecoding.drop(1)

        val isExecuted = commandManager.execution(nameCommand, commandArgs)
        if (!isExecuted) {
            println("Команды $nameCommand нет\nВведите help")
        }
    }
}