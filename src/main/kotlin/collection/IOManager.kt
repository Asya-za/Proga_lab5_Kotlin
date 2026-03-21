package collection

import java.io.File
import java.util.Scanner

/**
 * Класс для ввода и вывода данных.
 * Используется для работы с консолью и файлами.
 */
class IOManager {

    private var scanner: Scanner = Scanner(System.`in`)
    /**
     * Выводит сообщение в консоль.
     * @param message текст сообщения
     */
    fun println(message: String) {
        kotlin.io.println(message)
    }
    /**
     * Считывает строку из текущего источника ввода.
     * @return введённая строка
     */
    fun readLine(): String {
        return if (scanner.hasNextLine()) scanner.nextLine() else ""
    }
    /**
     * Устанавливает файл как источник ввода.
     * @param file файл
     */
    fun setFileInput(file: File) {
        scanner = Scanner(file)
    }
}