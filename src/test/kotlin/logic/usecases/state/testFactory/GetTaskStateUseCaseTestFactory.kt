package logic.usecases.state.testFactory

import logic.entities.Task
import java.util.*

object GetTaskStateUseCaseTestFactory {
    private val existingProjectID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    const val EXPECTED_TASK_STATE = "done"
    val existingTaskID: UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    val notExistingTaskID: UUID = UUID.randomUUID()
    val invalidTaskID: UUID = UUID.randomUUID()

    val existingTask = Task(
            id = existingTaskID,
            name = "Add View State Feature",
            projectId = existingProjectID,
            state = EXPECTED_TASK_STATE
        )


}