package ui.menuHandler

import logic.entities.User
import ui.controller.BaseUIController

suspend fun mainMenuProjectsForAdmin(
    user : User,
    onCreateProject: suspend () -> Unit,
    onViewProject: suspend () -> Unit,
    onEditProject: suspend () -> Unit,
    onDeleteProject: suspend () -> Unit,
    onViewProjectLogs : suspend ()-> Unit,
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
        5 to object : BaseUIController{
            override suspend fun execute() = onViewProjectLogs()
        },

    )

    val projectMenu = ProjectMenuHandler(projectControllers, user)
    projectMenu.start()
}

suspend fun mainMenuProjectsForMate(
    user : User,
    onViewProject: suspend () -> Unit,
    onViewProjectLogs : suspend ()-> Unit,
   ) {

    val projectControllers: Map<Int, BaseUIController> = mapOf(
        1 to object : BaseUIController {
            override suspend fun execute() = onViewProject()
        },
        2 to object : BaseUIController{
            override suspend fun execute() = onViewProjectLogs()
        },



        )

    val projectMenu = ProjectMenuHandler(projectControllers, user)
    projectMenu.start()
}