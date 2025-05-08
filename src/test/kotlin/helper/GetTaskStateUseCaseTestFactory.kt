package helper

import logic.entities.Task
import java.util.*

object GetTaskStateUseCaseTestFactory {
    private const val existingProjectID = "123e4567-e89b-12d3-a456-426614174000"
    const val EXPECTED_TASK_STATE = "done"
    const val existingTaskID = "123e4567-e89b-12d3-a456-426614174000"

    val notExistingTaskID = UUID.randomUUID().toString()

    const val invalidTaskID = "xyz"

    val existingTask = Task(
            id = UUID.fromString(existingTaskID),
            name = "Add View State Feature",
            projectId =  UUID.fromString(existingProjectID),
            state = EXPECTED_TASK_STATE
        )


}