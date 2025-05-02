package helper

import logic.entities.User
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.UUID

fun validUserData() = User(
    username = "Hend",
    password = "H123456",
    isAdmin = true,
    id = UUID.fromString("ebcb217c-b373-4e88-afbd-cbb5640a031a")
)

fun simulateConsoleInteraction(input : String, block: () -> Unit) : String{
    val inputStream = ByteArrayInputStream(input.toByteArray())
    System.setIn(inputStream)

    val outputStream = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStream))

    try {
        block()
    } finally {
        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    return (outputStream.toString())
}

const val SUCCESS_MESSAGE_FOR_LOGIN="Success Login"
const val WRONG_USER_NAME="The user name you entered is wrong , Please enter correct user name"
const val WRONG_PASSWORD="The password you entered is wrong , Please enter correct password"