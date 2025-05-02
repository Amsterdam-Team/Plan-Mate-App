package logic.usecases.state.testFactory

import logic.entities.Project
import java.util.*

object GetProjectStatesUseCaseTestFactory {
    const val EXISTING_PROJECT_ID: String = "123e4567-e89b-12d3-a456-426614174000"
    const val INVALID_PROJECT_ID: String = "xyz"
    const val NOT_EXISTING_PROJECT_ID: String = "4444567-e44b-44d3-a456-426614174000"

    private val EXPECTED_PROJECT_STATES: List<String> = listOf("to-do", "in progress", "done")

    val dummyProject = Project(
        id = UUID.fromString(EXISTING_PROJECT_ID),
        name = "PlanMate",
        states = EXPECTED_PROJECT_STATES,
        tasks = emptyList()
    )
}