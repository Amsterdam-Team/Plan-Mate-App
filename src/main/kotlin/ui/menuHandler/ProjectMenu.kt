package ui.menuHandler

import ui.controller.BaseUIController

suspend fun mainMenuProjects(
    onCreateProject: suspend () -> Unit,
    onViewProject: suspend () -> Unit,
    onEditProject: suspend () -> Unit,
    onDeleteProject: suspend () -> Unit,
) {

    val projectControllers: Map<Int, BaseUIController> = mapOf(
        1 to object : BaseUIController {
            override suspend fun execute() = onCreateProject()
        },
        2 to object : BaseUIController {
            override suspend fun execute() = onViewProject()
        },
        3 to object : BaseUIController {
            override suspend fun execute() = onEditProject()
        },
        4 to object : BaseUIController {
            override suspend fun execute() = onDeleteProject()
        },
    )

    val projectMenu = ProjectMenuHandler(projectControllers)
    projectMenu.start()
}
