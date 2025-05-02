package data.repository.project.helper

import logic.entities.Project
import logic.entities.Task
import java.util.UUID

object DeleteProjectTestFactory {
    val foodModeProjectId = UUID.fromString("ddb0f2e3-1825-40c4-928d-afd4c01d9839")
    val planeMateProjectId = UUID.fromString("083a5892-d719-430b-8083-7a890f6dc728")
    val someProjects = listOf<Project>(
        createProject(
            id = foodModeProjectId ,
            name = "food mode",
            states = listOf("todo","in progress", "done", "in review"),
            tasks = listOf(Task(
                id = UUID.randomUUID(),
                name = "add auth functionality",
                projectId = foodModeProjectId,
                state = "todo"
            ))
        ),
        createProject(
            id = planeMateProjectId ,
            name = "food mode",
            states = listOf("todo","in progress", "done", "in review"),
            tasks = listOf(Task(
                id = UUID.randomUUID(),
                name = "add auth functionality",
                projectId = planeMateProjectId,
                state = "todo"
            ))
        )
    )
}