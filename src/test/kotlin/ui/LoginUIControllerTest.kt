package ui

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.usecases.testFactory.SUCCESS_MESSAGE_FOR_LOGIN
import logic.usecases.testFactory.WRONG_PASSWORD
import logic.usecases.testFactory.WRONG_USER_NAME
import logic.usecases.testFactory.validUserData
import logic.exception.PlanMateException.AuthorizationException.WrongPasswordException
import logic.exception.PlanMateException.AuthorizationException.WrongUsernameException
import logic.usecases.login.LoginUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler

class LoginUIControllerTest {
    private lateinit var useCase : LoginUseCase
    private lateinit var uiController : LoginUIController
    private lateinit var adminMenuHandler : AdminMenuHandler
    private lateinit var mateMenuHandler: MateMenuHandler
    private lateinit var consoleIO: ConsoleIO

    @BeforeEach
    fun setup(){
        useCase = mockk()
        adminMenuHandler = mockk()
        mateMenuHandler = mockk()
        consoleIO = mockk()
        uiController = LoginUIController(useCase,adminMenuHandler,mateMenuHandler,consoleIO)
    }


    @Test
    fun `should print success message when useCase return full user data`() = runTest{
        val username = "Hend"
        val password = "H123456"
        val input = "${username}\n${password}"
        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns input

        coEvery { useCase.validateUserCredentials(username, password) } returns validUserData()

        uiController.execute()

        assertThat(slot.equals(SUCCESS_MESSAGE_FOR_LOGIN))
    }

    @Test
    fun `should print wrong username message when useCase throw wrong user name exception`()= runTest{
        val username = "Hen"
        val password = "H123456"
        val input = "${username}\n${password}"

        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns input


        coEvery { useCase.validateUserCredentials(username, password) } throws WrongUsernameException

        uiController.execute()

        assertThat(slot.equals(WRONG_USER_NAME))
    }

    @Test
    fun `should print wrong password message when useCase throw wrong password exception`()= runTest{
        val username = "Hend"
        val password = "12345"
        val input = "${username}\n${password}"
        val slot = CapturingSlot<String>()

        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returns input


        coEvery { useCase.validateUserCredentials(username, password) } throws WrongPasswordException

        uiController.execute()

        assertThat(slot.equals(WRONG_PASSWORD))
    }
}