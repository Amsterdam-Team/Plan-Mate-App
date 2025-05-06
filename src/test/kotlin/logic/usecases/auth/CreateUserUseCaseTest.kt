package logic.usecases.auth

import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.entities.User
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import logic.usecases.validation.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateUserUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var validator: ValidateInputUseCase
    private lateinit var useCase: CreateUserUseCase

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        validator = mockk()
        useCase = CreateUserUseCase(authRepository, validator)
    }

    @Test
    fun `should create user successfully when input is valid`() {
        // Arrange
        val username = "john_doe"
        val password = "securePassword123"
        every { validator.isValidName(username) } returns true
        every { authRepository.createUser(any()) } returns true

        // Act
        val result = useCase.execute(username, password)

        // Assert
        assertThat(result).isTrue()
        verify(exactly = 1) { authRepository.createUser(match {
            it.username == username &&
                    it.password == md5Hash(password) &&
                    !it.isAdmin
        }) }
    }

    @Test
    fun `should throw InvalidUsernameException when username is invalid`() {
        // Arrange
        val invalidUsername = "!!@@"
        every { validator.isValidName(invalidUsername) } returns false

        // Act & Assert
        assertThrows<InvalidUsernameException> { useCase.execute(invalidUsername, "ValidPassword123") }

    }

    @Test
    fun `should throw InvalidPasswordException when password is blank`() {
        every { validator.isValidName("validUser") } returns true

        assertThrows<InvalidPasswordException> { useCase.execute("validUser", " ") }
    }

    @Test
    fun `should throw InvalidPasswordException when password is too short`() {
        every { validator.isValidName("validUser") } returns true

        assertThrows<InvalidPasswordException> { useCase.execute("validUser", "short") }
           
    }

    @Test
    fun `should hash the password before saving`() {
        val username = "user"
        val password = "MySecretPassword"
        every { validator.isValidName(username) } returns true
        every { authRepository.createUser(any()) } returns true

        useCase.execute(username, password)

        verify { authRepository.createUser(match { it.password == md5Hash(password) }) }
    }
}
