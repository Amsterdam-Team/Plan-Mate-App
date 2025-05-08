package logic.usecases

import com.google.common.truth.Truth.assertThat
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.UnAuthenticatedException
import logic.usecases.utils.StateManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.UUID.randomUUID
import kotlin.test.Test

class StateManagerTest {
    @BeforeEach
    fun setUp() {
        StateManager.logOut()
    }

    @Test
    fun `should set and get the logged-in user successfully`() {
        // Given
        val user = User(id = randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)

        // When
        StateManager.setLoggedInUser(user)
        val loggedInUser = StateManager.getLoggedInUser()

        // Then
        assertThat(loggedInUser).isEqualTo(user)
    }

    @Test
    fun `should throw UnAuthenticatedException when trying to get user without login`() {
        // Expect
        assertThrows<UnAuthenticatedException> {
            StateManager.getLoggedInUser()
        }
    }

    @Test
    fun `should log out the user successfully when user exist`() {
        // Given
        val user = User(id = randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)
        StateManager.setLoggedInUser(user)

        // When
        StateManager.logOut()

        // Then
        assertThrows<UnAuthenticatedException> {
            StateManager.getLoggedInUser()
        }
    }

    @Test
    fun `should return true when user is logged in`() {
        // Given
        val user = User(id = randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)
        StateManager.setLoggedInUser(user)

        // Then
        assertThat(StateManager.isUserLoggedIn()).isTrue()
    }

    @Test
    fun `should return false when no user is logged in`() {
        // Then
        assertThat(StateManager.isUserLoggedIn()).isFalse()
    }
}