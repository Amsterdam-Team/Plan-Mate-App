package usecase

import helper.validUserData
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.AuthorizationException.*
import logic.repository.AuthRepository
import logic.usecases.LoginUseCase
import org.junit.jupiter.api.Assertions
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
    fun `should return full user data when username and password are found`(){
        //Given
        val username= "Hend"
        val password = "H123456"
        //When
        every { repository.login(username, password) } returns validUserData()
        //then
        val returnedUser = useCase.verifyUserState(username, password)
        Assertions.assertEquals(validUserData(), returnedUser)
    }

    @Test
    fun `should throw WrongUsernameException when username is wrong`(){
        //Given
        val username= "Hen"
        val password = "H123456"

        every { repository.login(username, password) } throws WrongUsername

        //When && Throw
        assertThrows<WrongUsername> {
            useCase.verifyUserState(username, password)
        }
    }

    @Test
    fun `should throw WrongPasswordException when password is wrong`(){
        //Given
        val username= "Hend"
        val password = "H123457"

        every { repository.login(username, password) } throws WrongPassword

        //When && Throw
        assertThrows<WrongPassword> {
            useCase.verifyUserState(username, password)
        }
    }

    @Test
    fun `should throw UserNotFoundException when username and password not found`(){
        //Given
        val username= "Hen"
        val password = "K12345"

        every { repository.login(username, password)
        } throws UserNotFoundException

        //When && Throw
        assertThrows<UserNotFoundException> {
            useCase.verifyUserState(username, password)
        }
    }
}