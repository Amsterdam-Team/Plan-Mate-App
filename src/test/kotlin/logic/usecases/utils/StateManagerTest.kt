package logic.usecases.utils

import com.google.common.truth.Truth
import logic.entities.User
import logic.exception.PlanMateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class StateManagerTest {
    @BeforeEach
    fun setUp() {
        StateManager.logOut()
    }

    @Test
    fun `should set and get the logged-in user successfully`() {
        // Given
        val user = User(id = UUID.randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)

        // When
        StateManager.setLoggedInUser(user)
        val loggedInUser = StateManager.getLoggedInUser()

        // Then
        Truth.assertThat(loggedInUser).isEqualTo(user)
    }

    @Test
    fun `should throw UnAuthenticatedException when trying to get user without login`() {
        // Expect
        assertThrows<PlanMateException.AuthorizationException.UnAuthenticatedException> {
            StateManager.getLoggedInUser()
        }
    }

    @Test
    fun `should log out the user successfully when user exist`() {
        // Given
        val user = User(id = UUID.randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)
        StateManager.setLoggedInUser(user)

        // When
        StateManager.logOut()

        // Then
        assertThrows<PlanMateException.AuthorizationException.UnAuthenticatedException> {
            StateManager.getLoggedInUser()
        }
    }

    @Test
    fun `should return true when user is logged in`() {
        // Given
        val user = User(id = UUID.randomUUID(), username = "admin", password = "hashed_password", isAdmin = true)
        StateManager.setLoggedInUser(user)

        // Then
        Truth.assertThat(StateManager.isUserLoggedIn()).isTrue()
    }

    @Test
    fun `should return false when no user is logged in`() {
        // Then
        Truth.assertThat(StateManager.isUserLoggedIn()).isFalse()
    }
}