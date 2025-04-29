package logic.usecases.state.testFactory

import java.util.*

object GetTaskStateUseCaseTestFactory {
    val existingTaskID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    val notExistingTaskID = UUID.fromString("4444567-e44b-44d3-a456-426614174000")
    const val EXPECTED_TASK_STATE = "done"
}