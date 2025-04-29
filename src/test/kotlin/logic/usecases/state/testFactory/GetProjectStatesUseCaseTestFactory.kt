package logic.usecases.state.testFactory

import java.util.*

object GetProjectStatesUseCaseTestFactory {
    val existingProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    val notExistingProjectID = UUID.fromString("4444567-e44b-44d3-a456-426614174000")

    val EXPECTED_PROJECT_STATES: List<String> = listOf("to-do", "in progress", "done")

}