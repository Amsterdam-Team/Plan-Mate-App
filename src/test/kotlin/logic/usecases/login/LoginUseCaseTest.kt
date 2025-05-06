package logic.usecases.login

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.AuthorizationException.WrongPasswordException
import logic.exception.PlanMateException.AuthorizationException.WrongUsernameException
import logic.repository.AuthRepository
import logic.usecases.auth.LoginUseCase
import logic.usecases.auth.md5Hash
import logic.usecases.testFactory.validUserData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginUseCaseTest {
    private lateinit var repository : AuthRepository
    private lateinit var useCase : LoginUseCase


    @BeforeEach
    fun setup(){
        repository = mockk()
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `should return full user data when username and password are valid format and found`(){
        //Given
        val username= "Hend"
        val password = "H123456"
        val hashedPassword = md5Hash(password)
        //When
        every { repository.login(username, hashedPassword) } returns validUserData()
        //then
        val returnedUser = useCase.validateUserCredentials(username, password)
        assertThat(returnedUser).isEqualTo(validUserData())
    }

    @Test
    fun `should throw wrong username exception when username is wrong`(){
        //Given
        val username= "Hen"
        val password = "H123456"
        val hashedPassword = md5Hash(password)

        every {
            repository.login(
                username,
                hashedPassword
            )
        } throws WrongUsernameException

        //When && Throw
        assertThrows<WrongUsernameException> {
            useCase.validateUserCredentials(username, password)
        }
    }

    @Test
    fun `should throw wrong password exception when password is wrong`(){
        //Given
        val username= "Hend"
        val password = "H123457"
        val hashedPassword = md5Hash(password)

        every {
            repository.login(
                username,
                hashedPassword
            )
        } throws WrongPasswordException

        //When && Throw
        assertThrows<WrongPasswordException> {
            useCase.validateUserCredentials(username, password)
        }
    }
}