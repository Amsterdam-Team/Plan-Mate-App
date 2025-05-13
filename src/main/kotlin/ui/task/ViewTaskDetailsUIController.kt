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
        consoleIO.println(ENTER_TASK_ID_MESSAGE)

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
        consoleIO.println(TASK_DETAILS_MESSAGE)
        consoleIO.println("$ID_KEYWORD         : ${task.id}")
        consoleIO.println("$NAME_KEYWORD       : ${task.name}")
        consoleIO.println("$STATE_KEYWORD      : ${task.state}")
        consoleIO.println("$PROJECT_ID_KEYWORD : ${task.projectId}")
    }

    private suspend fun showTaskOptionsMenu(
        onEditTaskDetails: () -> Unit,
        onDeleteTaskDetails: suspend () -> Unit,
        onBackTask: () -> Unit,
        ) {

        DisplayUtils.printSubTitle(MAIN_MENU_KEYWORD)

        DisplayUtils.printWithSideComment("1. ✏️  $EDIT_TASK_MESSAGE", EDIT_TASK_MESSAGE)
        DisplayUtils.printWithSideComment("2. 🗑️  $DELETE_TASK_MESSAGE", DELETE_TASK_MESSAGE)
        DisplayUtils.printWithSideComment("3. 🔙 $BACK_MESSAGE", BACK_TO_MESSAGE)


        when (consoleIO.readFromUser().trim()) {
            "1" -> onEditTaskDetails()
            "2" -> onDeleteTaskDetails()
            "3" -> onBackTask()
            else -> consoleIO.println(INVALID_OPTION_MESSAGE)

        }
    }

    companion object{
        const val ENTER_TASK_ID_MESSAGE = "🔍 Please enter Task ID to view details:"
        const val TASK_DETAILS_MESSAGE = "\n📝 Task Details:"
        const val ID_KEYWORD = "ID"
        const val NAME_KEYWORD = "Name"
        const val STATE_KEYWORD = "State"
        const val PROJECT_ID_KEYWORD = "Project $ID_KEYWORD"
        const val MAIN_MENU_KEYWORD = "\n⚙️  Main Menu"
        const val EDIT_TASK_MESSAGE = "Edit task"
        const val DELETE_TASK_MESSAGE = "Delete task"
        const val BACK_TO_MESSAGE = "Back to app"
        const val BACK_MESSAGE = "Back"
        const val INVALID_OPTION_MESSAGE = "Invalid option. Returning to main menu."
    }
}
