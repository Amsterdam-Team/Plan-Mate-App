package ui.controller.task

import logic.entities.Task
import logic.usecases.task.GetTaskByIdUseCase
import org.koin.java.KoinJavaComponent.getKoin
import ui.console.ConsoleIO
import ui.controller.base.BaseUIController
import ui.utils.DisplayUtils
import ui.utils.tryToExecute

class ViewTaskDetailsUIController(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    //   private val consoleIO = ConsoleIOImpl()

    override fun execute() {
        consoleIO.println("🔍 Please enter Task ID to view details:")

        tryToExecute(
            action = {
                val taskId = consoleIO.readFromUser()
                getTaskByIdUseCase(taskId)
            },
            onSuccess = { task ->
                showTaskDetails(task)
                showTaskOptionsMenu(

                    onEditTaskDetails = {
                        val editTaskUiController: EditTaskUiController = getKoin().get()
                        editTaskUiController.execute()
                    },
                    onDeleteTaskDetails = {
                        val deleteTaskUIController: DeleteTaskUIController = getKoin().get()
                        deleteTaskUIController.execute()
                    },
                    onBackTask = {},
                )

            }
        )
    }


    private fun showTaskDetails(task: Task) {
        consoleIO.println("\n📝 Task Details:")
        consoleIO.println("ID         : ${task.id}")
        consoleIO.println("Name       : ${task.name}")
        consoleIO.println("State      : ${task.state}")
        consoleIO.println("Project ID : ${task.projectId}")
    }

    private fun showTaskOptionsMenu(
        onEditTaskDetails: () -> Unit,
        onDeleteTaskDetails: () -> Unit,
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
            else -> consoleIO.println("❌ Invalid option. Returning to main menu.")
        }
    }
}
