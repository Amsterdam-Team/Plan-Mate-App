package logic.usecases.login

import com.google.common.truth.Truth.assertThat
import helper.UserFactory.validUserData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.AuthorizationException.WrongPasswordException
import logic.exception.PlanMateException.AuthorizationException.WrongUsernameException
import logic.repository.IAuthRepository
import logic.usecases.utils.StateManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ui.utils.md5Hash

class LoginUseCaseTest {
    private lateinit var repository: IAuthRepository
    private lateinit var stateManager: StateManager
    private lateinit var useCase: LoginUseCase


    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        useCase = LoginUseCase(repository, stateManager)
    }

    @Test
    fun `should return full user data when username and password are valid format and found`() =
        runTest {
            //Given
            val username = "Hend"
            val password = "H123456"
            val hashedPassword = md5Hash(password)
            val expectedUser = validUserData()
            coEvery { repository.login(username, hashedPassword) } returns expectedUser

            //When
            val returnedUser = useCase.validateUserCredentials(username, password)

            //then
            assertThat(returnedUser).isEqualTo(expectedUser)
        }

    @Test
    fun `should throw wrong username exception when username is wrong`() = runTest {
        // Given
        val username = "Hen"
        val password = "H123456"
        val hashedPassword = md5Hash(password)

        coEvery { repository.login(username, hashedPassword) } throws WrongUsernameException

        // When & Then
        assertThrows<WrongUsernameException> {
            useCase.validateUserCredentials(username, password)
        }
    }

    @Test
    fun `should throw wrong password exception when password is wrong`() = runTest {
        // Given
        val username = "Hend"
        val password = "WrongPass"
        val hashedPassword = md5Hash(password)

        coEvery { repository.login(username, hashedPassword) } throws WrongPasswordException

        // When & Then
        assertThrows<WrongPasswordException> {
            useCase.validateUserCredentials(username, password)
        }
    }
}