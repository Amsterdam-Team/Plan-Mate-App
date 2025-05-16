import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.entities.LogItem
import logic.entities.User
import logic.repository.AuthRepository
import logic.repository.LogRepository
import logic.usecases.utils.StateManager
import java.util.*

class AuthRepositoryLoggingProxy(
    private val authRepository: AuthRepository,
    private val logRepository: LogRepository
) : AuthRepository {

    override suspend fun login(username: String, password: String): User {
        val user = authRepository.login(username, password)
        StateManager.setLoggedInUser(user)

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val log = LogItem(
            id = UUID.randomUUID(),
            message = "User logged in: $username",
            date = now,
            entityId = user.id
        )

        logRepository.addLog(log)
        return user
    }

    override suspend fun createUser(user: User): Boolean {
        val result = authRepository.createUser(user)

        if (result) {
            val actor = if (StateManager.isUserLoggedIn()) StateManager.getLoggedInUser() else null

            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val log = LogItem(
                id = UUID.randomUUID(),
                message = "User created: ${user.username}",
                date = now,
                entityId = actor?.id ?: user.id
            )

            logRepository.addLog(log)
        }

        return result
    }
}
