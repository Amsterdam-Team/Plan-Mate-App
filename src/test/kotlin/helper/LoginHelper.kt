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

fun readInputFromConsole(input : String) : String{
    System.setIn(ByteArrayInputStream(input.toByteArray()))

    val outputStream = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStream))

    val output = outputStream.toString()

    return (output)
}
const val SUCCESS_MESSAGE_FOR_LOGIN="Success Login"
const val USER_NOT_FOUND="This User Not Found"
const val WRONG_USER_NAME="This Username is Wrong , Please Enter Correct Name"
const val WRONG_PASSWORD="This Password is Wrong , Please Enter Correct Password"