package usecase

import helper.validUserData
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.AuthorizationException.*
import logic.exception.PlanMateException.ValidationException.InvalidPasswordException
import logic.exception.PlanMateException.ValidationException.InvalidUsernameException
import logic.repository.AuthRepository
import logic.usecases.LoginUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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
        //When
        every { repository.login(username, password) } returns validUserData()
        //then
        val returnedUser = useCase.verifyUserState(username, password)
        Assertions.assertEquals(validUserData(), returnedUser)
    }

    @Test
    fun `should throw wrong username exception when username and password are valid format but username is wrong`(){
        //Given
        val username= "Hen"
        val password = "H123456"

        every { repository.login(username, password) } throws WrongUsernameException

        //When && Throw
        assertThrows<WrongUsernameException> {
            useCase.verifyUserState(username, password)
        }
    }

    @Test
    fun `should throw wrong password exception when username and password are valid format but password is wrong`(){
        //Given
        val username= "Hend"
        val password = "H123457"

        every { repository.login(username, password) } throws WrongPasswordException

        //When && Throw
        assertThrows<WrongPasswordException> {
            useCase.verifyUserState(username, password)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "H@$",
            "1325",
            "",
            "he" //size less than 3
        ]
    )
    fun `should throw invalid username exception when username is invalid format`(username:String){
        val password = "H1234567"

        assertThrows<InvalidUsernameException>{
           useCase.verifyUserState(username,password)
       }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123456",
            "",
            "h1234"
        ]
    )
    fun `should print invalid password exception when password is invalid format`(password:String){
        val username = "Hend"

        assertThrows<InvalidPasswordException>{
            useCase.verifyUserState(username,password)
        }
    }
}