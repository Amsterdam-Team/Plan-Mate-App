package logic.usecases.user

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.exception.PlanMateException.ValidationException.UserAlreadyExistsException
import logic.repository.AuthRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import helper.TestDataFactory.createUser
import org.junit.jupiter.api.assertThrows

class CreateUserUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: CreateUserUseCase
    private lateinit var validateInputUseCase: ValidateInputUseCase
    private lateinit var stateManager: StateManager

    @BeforeEach
    fun setup() {
        validateInputUseCase = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = CreateUserUseCase(repository,validateInputUseCase,stateManager)
    }

    @Test
    fun `should return success when username and password are valid`() = runTest {
        // Given
        val user = createUser()
        coEvery { repository.createUser(any()) } returns true

        // When
        val result = useCase.execute(user.username, user.password)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return error when username already exists`() =runTest {
        // Given
        val user = createUser(username = "mohammad")
        coEvery { repository.createUser(any()) } throws UserAlreadyExistsException

        // When & Then
        assertThrows <UserAlreadyExistsException>{  useCase.execute(user.username, user.password) }
    }

    @Test
    fun `should return error when password is blank`() =runTest {
        // Given
        val username = "mohammad"
        val blankPassword = "  "

        // When & Then
        assertThrows<InvalidPasswordException>{useCase.execute(username, blankPassword)}
    }

    @Test
    fun `should return error when username is blank`() = runTest{
        // Given
        val blankUsername = "  "
        val password = "123456"
        // When & Then
        assertThrows<InvalidUsernameException>{useCase.execute(blankUsername, password)}
    }

    @Test
    fun `should return error when both username and password are blank`() = runTest {
        // Given
        val blankUsername = "    "
        val blankPassword = "    "

        // When & Then
        assertThrows<InvalidUsernameException>{useCase.execute(blankUsername, blankPassword)}
    }
}