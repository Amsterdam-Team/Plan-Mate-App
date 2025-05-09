package helpers

import helper.ProjectFactory.PROJECT1_ID
import logic.entities.Task
import java.util.UUID

object DeleteTaskTestFactory {


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




}
