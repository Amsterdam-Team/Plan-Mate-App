package usecase

import io.mockk.every
import io.mockk.mockk
import logic.entities.User
import logic.exception.PlanMateException
import logic.repository.AuthRepository
import logic.usecases.task.LoginUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertFalse

class LoginUseCaseTest {
    private lateinit var repository : AuthRepository
    private lateinit var useCase : LoginUseCase
    private  val validAdminUserData = User(
        username = "Hend",
        password = "123456",
        isAdmin = true,
        id = UUID.fromString("ebcb217c-b373-4e88-afbd-cbb5640a031a")
    )
    private  val validMateUserData = User(
        username = "Nada",
        password = "12345",
        isAdmin = false,
        id = UUID.fromString("ebcb257c-b777-4r88-afbk-cbb5640a031a")
    )

    @BeforeEach
    fun setup(){
        repository = mockk()
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `should return role of user when username and password are found`(){
        //Given
        val username= "Hend"
        val password = "123456"
        //When
        every { repository.login(username, password) } returns validAdminUserData
        //then
        val returnedRole = useCase.verifyUserState(username, password)
        Assertions.assertEquals(validAdminUserData.isAdmin, returnedRole)
    }

    @Test
    fun `should return role is true when username and password are valid for admin`(){
        //Given
        val username= "Hend"
        val password = "123456"
        //When
        every { repository.login(username, password) } returns validAdminUserData
        //then
        val returnedRole = useCase.verifyUserState(username, password)
        Assertions.assertTrue(returnedRole)
    }

    @Test
    fun `should return role is false when username and password are valid for mateUser`(){
        //Given
        val username= "Nada"
        val password = "12345"
        //When
        every { repository.login(username, password) } returns validMateUserData
        //then
        val returnedRole = useCase.verifyUserState(username, password)
        assertFalse(returnedRole)
    }

    @Test
    fun `should throw WrongUsernameException when username is wrong`(){
        //Given
        val username= "He"
        val password = "123456"

        every { repository.login(username, password) } throws PlanMateException.AuthorizationException.WrongUsername

        //When && Throw
        assertThrows<PlanMateException.AuthorizationException.WrongUsername> {
            useCase.verifyUserState(username, password)
        }
    }

    @Test
    fun `should throw WrongPasswordException when password is wrong`(){
        //Given
        val username= "Hend"
        val password = "123"

        every { repository.login(username, password) } throws PlanMateException.AuthorizationException.WrongPassword

        //When && Throw
        assertThrows<PlanMateException.AuthorizationException.WrongPassword> {
            useCase.verifyUserState(username, password)
        }
    }

    @Test
    fun `should throw UserNotFoundException when username and password not found`(){
        //Given
        val username= "He"
        val password = "123"

        every {
            repository.login(
                username,
                password
            )
        } throws PlanMateException.AuthorizationException.UserNotFoundException

        //When && Throw
        assertThrows<PlanMateException.AuthorizationException.UserNotFoundException> {
            useCase.verifyUserState(username, password)
        }
    }
}