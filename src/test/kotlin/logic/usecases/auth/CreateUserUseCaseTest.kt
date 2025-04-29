package logic.usecases.auth

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.UserAlreadyExistsException
import logic.repository.AuthRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.ResultStatus
import utils.TestDataFactory.createUser

class CreateUserUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: CreateUserUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = CreateUserUseCase(repository)
    }

    @Test
    fun `should create user and call repository once when username and password are valid`() {
        // Given
        val user = createUser()

        // When
        val result = useCase.execute(user.username, user.password, user.isAdmin)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Success::class.java)
    }

    @Test
    fun `should throw UserAlreadyExistsException when username already exists`() {
        // Given
        val user = createUser()

        // When & Then
        assertThrows<UserAlreadyExistsException> {
            useCase.execute(user.username, "any", user.isAdmin)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is blank`() {
        // Given
        val username = "mohammad"
        val blankPassword = "  "

        // When & Then
        assertThrows<InvalidPasswordException> {
            useCase.execute(username, blankPassword, false)
        }
    }
}