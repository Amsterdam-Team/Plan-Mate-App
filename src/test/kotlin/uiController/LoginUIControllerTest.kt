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
    fun `should print success message when useCase return full user data`(){
        val username = "Hend"
        val password = "H123456"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } returns validUserData()

        uiController.loginUIController()

        assert(output.contains(SUCCESS_MESSAGE_FOR_LOGIN))
    }

    @Test
    fun `should print user not found message when useCase throw user not found exception`(){
        val username = "nada"
        val password = "H12345"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } throws UserNotFoundException

        uiController.loginUIController()

        assert(output.contains(USER_NOT_FOUND))
    }

    @Test
    fun `should print wrong username message when useCase throw wrong user name exception`(){
        val username = "Hen"
        val password = "H123456"

        val output = readInputFromConsole(username)

        every { useCase.verifyUserState(username,password) } throws WrongUsernameException

        uiController.loginUIController()

        assert(output.contains(WRONG_USER_NAME))
    }

    @Test
    fun `should print wrong password message when useCase throw wrong password exception`(){
        val username = "Hend"
        val password = "12345"
        val input = "$username\n$password"

        val output = readInputFromConsole(input)

        every { useCase.verifyUserState(username,password) } throws WrongPasswordException

        uiController.loginUIController()

        assert(output.contains(WRONG_PASSWORD))
    }
}