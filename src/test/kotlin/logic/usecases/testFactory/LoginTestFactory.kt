package logic.usecases.testFactory

import logic.entities.User
import java.util.UUID

fun validUserData() = User(
    username = "Hend",
    password = "H123456",
    isAdmin = true,
    id = UUID.fromString("ebcb217c-b373-4e88-afbd-cbb5640a031a")
)

const val SUCCESS_MESSAGE_FOR_LOGIN="Success Login......"
const val WRONG_USER_NAME="The user name you entered is wrong , Please enter correct user name"
const val WRONG_PASSWORD="The password you entered is wrong , Please enter correct password"