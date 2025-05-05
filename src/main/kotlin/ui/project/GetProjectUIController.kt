package ui.project


import data.datasources.projectDataSource.ProjectDataSourceInterface
import data.datasources.taskDataSource.TaskDataSourceInterface
import data.repository.project.ProjectRepositoryImpl
import data.repository.task.TaskRepositoryImpl
import logic.entities.Project
import logic.entities.Task
import logic.repository.ProjectRepository
import logic.usecases.project.GetProjectDetailsUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import ui.controller.BaseUIController
import ui.utils.tryToExecute
import utils.printSwimlanesView
import java.util.*

class GetProjectUIController(
    private val getProjectsUseCase: GetProjectDetailsUseCase,
    private val consoleIO: ConsoleIO
):BaseUIController {

    override fun execute(){
        consoleIO.println("Enter Project ID :)")
        val id = consoleIO.readFromUser()
        tryToExecute({
            getProjectsUseCase(id)
        },{
            printSwimlanesView(it)
        })
    }

}