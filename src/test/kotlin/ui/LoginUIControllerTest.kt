package ui

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException
import logic.usecases.LoginUseCase
import logic.usecases.testFactory.SUCCESS_MESSAGE_FOR_LOGIN
import logic.usecases.testFactory.WRONG_PASSWORD
import logic.usecases.testFactory.WRONG_USER_NAME
import logic.usecases.testFactory.simulateConsoleInteraction
import logic.usecases.testFactory.validUserData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler

class LoginUIControllerTest {
    private lateinit var useCase : LoginUseCase
    private lateinit var uiController : LoginUIController
    private lateinit var adminMenuHandler : AdminMenuHandler
    private lateinit var mateMenuHandler: MateMenuHandler

    @BeforeEach
    fun setup(){
        useCase = mockk()
        adminMenuHandler = mockk()
        mateMenuHandler = mockk()
        uiController = LoginUIController(useCase,adminMenuHandler,mateMenuHandler)
    }

    @Test
    fun `should print success message when useCase return full user data`(){
        val username = "Hend"
        val password = "H123456"
        val input = "$username\n$password"

        every { useCase.verifyUserState(username, password) } returns validUserData()

        val output = simulateConsoleInteraction(input) {
            uiController.execute()
        }

        assert(output.toString().contains(SUCCESS_MESSAGE_FOR_LOGIN))
    }

    @Test
    fun `should print wrong username message when useCase throw wrong user name exception`(){
        val username = "Hen"
        val password = "H123456"
        val input = "$username\n$password"

        every {
            useCase.verifyUserState(
                username,
                password
            )
        } throws PlanMateException.AuthorizationException.WrongUsernameException

        val output = simulateConsoleInteraction(input) {
            uiController.execute()
        }

        assert(output.contains(WRONG_USER_NAME))
    }

    @Test
    fun `should print wrong password message when useCase throw wrong password exception`(){
        val username = "Hend"
        val password = "12345"
        val input = "$username\n$password"

        every {
            useCase.verifyUserState(
                username,
                password
            )
        } throws PlanMateException.AuthorizationException.WrongPasswordException

        val output = simulateConsoleInteraction(input) {
            uiController.execute()
        }

        assert(output.contains(WRONG_PASSWORD))
    }
}