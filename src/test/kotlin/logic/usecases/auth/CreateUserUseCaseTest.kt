package logic.usecases.auth

import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.exception.PlanMateException.ValidationException.UserAlreadyExistsException
import logic.repository.AuthRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `should return success when username and password are valid`() {
        // Given
        val user = createUser()
        every { repository.createUser(any()) } just Runs

        // When
        val result = useCase.execute(user.username, user.password, user.isAdmin)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Success::class.java)
    }

    @Test
    fun `should return error when username already exists`() {
        // Given
        val user = createUser(username = "mohammad")
        every { repository.createUser(any()) } throws UserAlreadyExistsException

        // When
        val result = useCase.execute(user.username, user.password, user.isAdmin)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Error::class.java)
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(UserAlreadyExistsException::class.java)
    }

    @Test
    fun `should return error when password is blank`() {
        // Given
        val username = "mohammad"
        val blankPassword = "  "

        // When
        val result = useCase.execute(username, blankPassword, false)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Error::class.java)
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(InvalidPasswordException::class.java)
    }

    @Test
    fun `should return error when username is blank`() {
        // Given
        val blankUsername = "  "
        val password = "123456"

        // When
        val result = useCase.execute(blankUsername, password, false)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Error::class.java)
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(InvalidUsernameException::class.java)
    }

    @Test
    fun `should return error when both username and password are blank`() {
        // Given
        val blankUsername = "    "
        val blankPassword = "    "

        // When
        val result = useCase.execute(blankUsername, blankPassword, false)

        // Then
        assertThat(result).isInstanceOf(ResultStatus.Error::class.java)
        assertThat((result as ResultStatus.Error).exception).isInstanceOf(InvalidUsernameException::class.java)
    }
}