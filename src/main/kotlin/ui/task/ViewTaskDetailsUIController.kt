package ui.task

import logic.entities.Task
import logic.usecases.task.DeleteTaskUseCase
import logic.usecases.task.EditTaskUseCase
import logic.usecases.task.GetTaskByIdUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.DisplayUtils
import ui.utils.tryToExecute

class ViewTaskDetailsUIController(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {


    override suspend fun execute() {
        consoleIO.println("🔍 Please enter Task ID to view details:")

        tryToExecute(action = {
            val taskId = consoleIO.readFromUser()
            getTaskByIdUseCase(taskId)
        }, onSuccess = { task ->
            showTaskDetails(task)
            showTaskOptionsMenu(

                onEditTaskDetails = {
                    //todo EditTask UIController

                },
                onDeleteTaskDetails = {
                    deleteTaskUseCase.execute(task.id.toString())
                },
                onBackTask = {},
            )

        })
    }


    private fun showTaskDetails(task: Task) {
        consoleIO.println("\n📝 Task Details:")
        consoleIO.println("ID         : ${task.id}")
        consoleIO.println("Name       : ${task.name}")
        consoleIO.println("State      : ${task.state}")
        consoleIO.println("Project ID : ${task.projectId}")
    }

    //
    private suspend fun showTaskOptionsMenu(
        onEditTaskDetails: () -> Unit,
        onDeleteTaskDetails: suspend () -> Unit,
        onBackTask: () -> Unit,

        ) {

        DisplayUtils.printSubTitle("\n⚙️  Main Menu")

        DisplayUtils.printWithSideComment("1. ✏️  Edit Task", "Edit task  ")
        DisplayUtils.printWithSideComment("2. 🗑️  Delete Task", "Delete")
        DisplayUtils.printWithSideComment("3. 🔙 Back", "Back  to  app")


        when (consoleIO.readFromUser().trim()) {
            "1" -> onEditTaskDetails()
            "2" -> onDeleteTaskDetails()
            "3" -> onBackTask()
            else -> consoleIO.println(" Invalid option. Returning to main menu.")

        }
    }


}
