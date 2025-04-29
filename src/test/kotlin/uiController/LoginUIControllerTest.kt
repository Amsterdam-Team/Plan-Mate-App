package uiController

import helper.SUCCESS_MESSAGE_FOR_LOGIN
import helper.USER_NOT_FOUND
import helper.WRONG_PASSWORD
import helper.WRONG_USER_NAME
import helper.readInputFromConsole
import helper.validUserData
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.AuthorizationException.*
import logic.usecases.LoginUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.LoginUIController
import kotlin.test.Test

class LoginUIControllerTest {
    private lateinit var useCase : LoginUseCase
    private lateinit var uiController : LoginUIController

    @BeforeEach
    fun setup(){
        useCase = mockk()
        uiController = LoginUIController(useCase)
    }

    @Test
    fun `should print success message when username and password are valid format and useCase return full user data`(){
        val username = "Hend"
        val password = "H123456"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } returns validUserData()

        uiController.loginUIController()

        assert(output.contains(SUCCESS_MESSAGE_FOR_LOGIN))
    }

    @Test
    fun `should print user not found message when username and password are valid format but useCase throw UserNotFoundException exception`(){
        val username = "nada"
        val password = "H12345"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } throws UserNotFoundException

        uiController.loginUIController()

        assert(output.contains(USER_NOT_FOUND))
    }

    @Test
    fun `should print wrong username message when username and password are valid format but useCase throw WrongUserName exception`(){
        val username = "Hen"
        val password = "H123456"

        val output = readInputFromConsole(username)

        every { useCase.verifyUserState(username,password) } throws WrongUsername

        uiController.loginUIController()

        assert(output.contains(WRONG_USER_NAME))
    }

    @Test
    fun `should print wrong password message when username and password are valid format but useCase throw WrongPassword exception`(){
        val username = "Hend"
        val password = "12345"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } throws WrongPassword

        uiController.loginUIController()

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

        val output = readInputFromConsole(username)

        uiController.loginUIController()

        assert(output.contains(WRONG_USER_NAME))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123456",
             "",
            "h1234"
        ]
    )
    fun `should print wrong password message when password is invalid format`(password:String){
        val username = "Hend"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        uiController.loginUIController()

        assert(output.contains(WRONG_PASSWORD))
    }
}