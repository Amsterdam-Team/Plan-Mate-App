package uiController

import io.mockk.every
import io.mockk.mockk
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.*
import logic.usecases.LoginUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.LoginUIController
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.UUID
import kotlin.test.Test

class LoginUIControllerTest {
    private lateinit var useCase : LoginUseCase
    private lateinit var uiController : LoginUIController
    private  val validAdminUserData = User(
        username = "Hend",
        password = "H123456",
        isAdmin = true,
        id = UUID.fromString("ebcb217c-b373-4e88-afbd-cbb5640a031a")
    )

    @BeforeEach
    fun setup(){
        useCase = mockk()
        uiController = LoginUIController(useCase)
    }

    @Test
    fun `should print success message when username and password are valid format and useCase return the role of this correct user`(){
        val username = "Hend"
        val password = "H123456"
        val input = "$username\n$password"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.verifyUserState(username,password) } returns validAdminUserData.isAdmin

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(SUCCESS_MESSAGE_FOR_LOGIN))
    }

    @Test
    fun `should print user not found message when username and password are valid format but useCase throw UserNotFoundException exception`(){
        val username = "nada"
        val password = "H12345"
        val input = "$username\n$password"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.verifyUserState(username,password) } throws UserNotFoundException

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(USER_NOT_FOUND))
    }

    @Test
    fun `should print wrong username message when username and password are valid format but useCase throw WrongUserName exception`(){
        val username = "Hen"
        val password = "H123456"
        val input = "$username\n$password"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.verifyUserState(username,password) } throws WrongUsername

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(WRONG_USER_NAME))
    }

    @Test
    fun `should print wrong password message when username and password are valid format but useCase throw WrongPassword exception`(){
        val username = "Hend"
        val password = "12345"
        val input = "$username\n$password"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        every { useCase.verifyUserState(username,password) } throws WrongPassword

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(WRONG_PASSWORD))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "H@$",
            "1325",
             "",
             "he" //size less than 3
        ]
    )
    fun `should print wrong username message when username is invalid format`(username:String){

        System.setIn(ByteArrayInputStream(username.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(WRONG_USER_NAME))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123456", //only digits
             "",
            "h1234" //length less than 6
        ]
    )
    fun `should print wrong password message when password is invalid format`(password:String){
        val username = "Hend"
        val input = "$username\n$password"
        System.setIn(ByteArrayInputStream(input.toByteArray()))

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        uiController.loginUIController()

        val output = outputStream.toString()
        assert(output.contains(WRONG_PASSWORD))
    }


    companion object{
        const val SUCCESS_MESSAGE_FOR_LOGIN="Success Login"
        const val USER_NOT_FOUND="This User Not Found"
        const val WRONG_USER_NAME="This Username is Wrong , Please Enter Correct Name"
        const val WRONG_PASSWORD="This Password is Wrong , Please Enter Correct Password"
    }
}