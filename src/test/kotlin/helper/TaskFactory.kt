package helper

import helper.ConstantsFactory.CANCEL
import helper.ConstantsFactory.EXISTING_PROJECT_ID
import helper.ConstantsFactory.EXISTING_STATE
import helper.ConstantsFactory.EXISTING_TASK_ID
import helper.ConstantsFactory.EXPECTED_TASK_STATE
import helper.ConstantsFactory.UN_EXISTING_PROJECT_ID
import helper.ConstantsFactory.VALID_PROJECT_ID
import helper.ConstantsFactory.VALID_STATE
import helper.ConstantsFactory.VALID_TASK_NAME
import helper.ProjectFactory.PROJECT1_ID
import logic.entities.Task
import java.util.UUID

object TaskFactory {

    private fun createTask(
        name: String = "",
        projectID: String = "",
        state: String = ""
    ): Task {
        return Task(
            id = UUID.randomUUID(),
            name = name,
            projectId = UUID.fromString(projectID),
            state = state,
        )
    }

    fun getDummyInputs(
        taskName: String = VALID_TASK_NAME,
        projectId: String = VALID_PROJECT_ID,
        taskState: String = VALID_STATE
    ): List<String> {
        return listOf(taskName, projectId, taskState, CANCEL)
    }

    val validTask = createTask(
        name = "Add Create Task Feature",
        projectID = EXISTING_PROJECT_ID,
        state = EXISTING_STATE
    )

    val taskWithUnExistingProjectID = createTask(
        name = "Add Create Task Feature",
        projectID = UN_EXISTING_PROJECT_ID,
        state = EXISTING_STATE
    )

    val TASK_1 = Task(
        id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
        name = "Fix UI bug",
        projectId = PROJECT1_ID,
        state = "IN_PROGRESS"
    )

    val TASK_2 = Task(
        id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
        name = "Write unit tests",
        projectId = PROJECT1_ID,
        state = "PENDING"
    )

    val existingTask = Task(
        id = UUID.fromString(EXISTING_TASK_ID),
        name = "Add View State Feature",
        projectId = UUID.fromString(EXISTING_PROJECT_ID),
        state = EXPECTED_TASK_STATE
    )

    val existingStates = listOf("TO-DO", "IN-PROGRESS")
    val notExistingTaskID = UUID.randomUUID().toString()
}