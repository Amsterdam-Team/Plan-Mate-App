package logic.entities
import java.util.*

data class User(
    val id: UUID,
    val isAdmin: Boolean,
    val username: String,
    val password: String
)

