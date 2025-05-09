package logic.usecases.user

import com.google.common.truth.Truth.assertThat
import helper.UserFactory.createUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.exception.PlanMateException.ValidationException.UserAlreadyExistsException
import logic.repository.AuthRepository
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID


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
        coEvery { stateManager.getLoggedInUser() } returns User(
            id = UUID.randomUUID(),
            username = "admin",
            password = "hashed",
            isAdmin = true
        )
        useCase = CreateUserUseCase(repository, validateInputUseCase, stateManager)
    }

    @Test
    fun `should return success when username and password are valid`() = runTest {
        // Given
        val user = createUser(password = "validPass123")
        coEvery { validateInputUseCase.isValidName(user.username) } returns true
        coEvery { repository.createUser(any()) } returns true

        // When
        val result = useCase.execute(user.username, user.password)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return error when username already exists`() = runTest {
        // Given
        val user = createUser(username = "mohammad", password = "validPass123")
        coEvery { validateInputUseCase.isValidName(user.username) } returns true
        coEvery { repository.createUser(any()) } throws UserAlreadyExistsException

        // When & Then
        assertThrows<UserAlreadyExistsException> { useCase.execute(user.username, user.password) }
    }

    @Test
    fun `should return error when password is blank`() = runTest {
        // Given
        val username = "mohammad"
        val blankPassword = "  "
        coEvery { validateInputUseCase.isValidName(username) } returns true

        // When & Then
        assertThrows<InvalidPasswordException> { useCase.execute(username, blankPassword) }
    }

    @Test
    fun `should return error when username is blank`() = runTest {
        // Given
        val blankUsername = "  "
        val password = "123456"
        coEvery { validateInputUseCase.isValidName(blankUsername) } returns false


        // When & Then
        assertThrows<InvalidUsernameException> { useCase.execute(blankUsername, password) }
    }

    @Test
    fun `should return error when both username and password are blank`() = runTest {
        // Given
        val blankUsername = "    "
        val blankPassword = "    "

        // When & Then
        assertThrows<InvalidUsernameException> { useCase.execute(blankUsername, blankPassword) }
    }
}