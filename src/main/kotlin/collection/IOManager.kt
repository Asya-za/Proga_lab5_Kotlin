package collection

import java.io.File
import java.util.Scanner

class IOManager {

    private var scanner: Scanner = Scanner(System.`in`)

    fun println(message: String) {
        kotlin.io.println(message)
    }

    fun readLine(): String {
        return scanner.nextLine()
    }

    fun setFileInput(file: File) {
        scanner = Scanner(file)
    }
}