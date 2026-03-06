package app

import commands.*
import collection.CollectionManager
import collection.CommandManager
import java.time.LocalDateTime
//import java.util.Scanner
import collection.FileManager

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
    val collectionManager = CollectionManager(initializationTime, fileName)
    val commandManager = CommandManager()

    try{
        collectionManager.loadCollectionFromFile(fileManager)
        println("Коллекция загружена. Количество элементов: ${collectionManager.Size()}")
    }
    catch (e: Exception) {
        println("Не удалось загрузить коллекцию: ${e.message}")
    }

    commandManager.addToList(HelpCommand(commandManager))
    commandManager.addToList(InfoCommand(collectionManager))
    commandManager.addToList(ShowCommand(collectionManager))
    commandManager.addToList(ExitCommand())
    commandManager.addToList(ClearCommand(collectionManager))
    commandManager.addToList(PrintAscendingCommand(collectionManager))
    commandManager.addToList(SaveCommand(collectionManager))
    commandManager.addToList(InsertCommand(collectionManager))
    commandManager.addToList(UpdateCommand(collectionManager))
    commandManager.addToList(RemoveGreaterKeyCommand(collectionManager))
    commandManager.addToList(RemoveGreaterCommand(collectionManager))
    commandManager.addToList(ReplaceIfGreaterCommand(collectionManager))

    println("Программа запущена")
    println("Файл: $fileName")



    //val scan = Scanner(System.`in`)
    while (true) {
        //if (!scan.hasNextLine()) break
        val line = readln().trim() ///убираем пррбелы

        if (line.isEmpty()) continue

        val commandDecoding = line.split(Regex("\\s+"))
        val nameCommand = commandDecoding[0]
        val commandArgs = commandDecoding.drop(1)

        val isExecuted = commandManager.execution(nameCommand, commandArgs)
        if (!isExecuted) {
            println("Команды $nameCommand нет \nВведите help")
        }
    }
}