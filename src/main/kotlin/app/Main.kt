package app

import commands.*
import collection.CollectionManager
import collection.CommandManager
import java.time.LocalDateTime
import java.util.Scanner
import collection.FileManager
import exceptions.ExitException
import io.IOManager

import java.io.PrintStream

fun main(args: Array<String>) {
    System.setOut(PrintStream(System.out, true, "UTF-8"))
    System.setErr(PrintStream(System.err, true, "UTF-8"))

    if (args.isEmpty()) {
        System.err.println("Ошибка: нужно передать имя файла")
        return
    }



    val fileName = args[0]
    val fileManager = FileManager(fileName)

    val initializationTime = LocalDateTime.now()
    val io = IOManager()
    val collectionManager = CollectionManager(initializationTime, fileName, io)
    val commandManager = CommandManager()

    try{
        collectionManager.loadCollectionFromFile(fileManager)
        io.println("Коллекция загружена. Количество элементов: ${collectionManager.Size()}")
    }
    catch (e: Exception) {
        io.println("Не удалось загрузить коллекцию: ${e.message}")
    }

    commandManager.addToList(HelpCommand(commandManager, io))
    commandManager.addToList(InfoCommand(collectionManager, io))
    commandManager.addToList(ShowCommand(collectionManager))
    commandManager.addToList(ExitCommand(io))
    commandManager.addToList(ClearCommand(collectionManager, io))
    commandManager.addToList(PrintAscendingCommand(collectionManager))
    commandManager.addToList(SaveCommand(collectionManager, io))
    commandManager.addToList(InsertCommand(collectionManager, io))
    commandManager.addToList(UpdateCommand(collectionManager, io))
    commandManager.addToList(RemoveGreaterKeyCommand(collectionManager, io))
    commandManager.addToList(FilterStartsWithNameCommand(collectionManager, io))
    commandManager.addToList(GroupCountingByIdCommand(collectionManager))
    commandManager.addToList(RemoveGreaterCommand(collectionManager))
    commandManager.addToList(RemoveKeyCommand(collectionManager, io))
    commandManager.addToList(ReplaceIfGreaterCommand(collectionManager))
    commandManager.addToList(ExecuteScriptCommand(collectionManager, commandManager, io))


    io.println("Программа запущена")
    io.println("Файл: $fileName")


    try {
        while (true) {
            print("> ")
            System.out.flush()

            val line = io.readLine()

            if (line.isEmpty()) continue

            val commandDecoding = line.split(Regex("\\s+"))
            val nameCommand = commandDecoding[0]
            val commandArgs = commandDecoding.drop(1)

            val isExecuted = commandManager.execution(nameCommand, commandArgs)

            if (!isExecuted) {
                io.println("Команды $nameCommand нет \nВведите help")
            }
        }
    } catch (e: ExitException) {}
}