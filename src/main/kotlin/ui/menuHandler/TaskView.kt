import logic.entities.Project
import logic.entities.Task
import logic.usecases.logs.ViewTaskLogsUseCase
import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.DeleteTaskUseCase
import ui.console.ConsoleIO
import ui.utils.getErrorMessageByException
import java.util.UUID

class TaskManagerView(
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase,
    private val consoleIO: ConsoleIO
) {
    private lateinit var currentProject: Project
    private lateinit var currentTasks: List<Task>

    suspend fun showTaskOptions(project: Project) {
        try {
            currentProject = project
            currentTasks = currentProject.tasks
            consoleIO.println("""
            Options:
            [1] Create a New Task
            [2] Delete a Task
            [3] Show Task Logs
            [4] Edit Task
            [0] Go Back to Projects
        """.trimIndent())

            when (consoleIO.readFromUser().trim()) {
                "1" -> createTask()
                "2" -> deleteTask()
                "3" -> showTaskLogs()
                "4" -> editTask()
                "0" -> return
                else -> {
                    consoleIO.println("Invalid option. Please try again.")
                    showTaskOptions(currentProject)
                }
            }
        }catch (e:Exception){
            getErrorMessageByException(e)
        }

    }

    private suspend fun createTask() {
        consoleIO.println("Enter task name:")
        val taskName = getValidatedInputString()

        consoleIO.println("Enter task state; ${currentProject.states}:")
        val taskState = getValidatedInputString()

        if(createTaskUseCase.createTask(taskName, currentProject.id.toString(), taskState)){
            consoleIO.println("Task '$taskName' created successfully!")
        }
        showTaskOptions(currentProject)
    }

    private suspend fun deleteTask() {
        showAllTasks()
        val index = getValidatedTaskIndex()
        val selectedTask = currentTasks[index]

        if (deleteTaskUseCase.execute(selectedTask.id.toString())) {
            consoleIO.println("Task Deleted Successfully.")
        } else {
            consoleIO.println("Failed to delete task.")
        }
        showTaskOptions(currentProject)
    }

    private suspend fun showTaskLogs() {
        showAllTasks()
        val index = getValidatedTaskIndex()
        val selectedTask = currentTasks[index]

        consoleIO.println("Logs for Task '${selectedTask.name}':")
        val logs = viewTaskLogsUseCase.viewTaskLogs(selectedTask.id.toString())
        logs.forEach { consoleIO.println(it.message) }
        showTaskOptions(currentProject)
    }

    private fun editTask(){

    }
    private fun showAllTasks() {
        consoleIO.println("All Tasks:")
        currentTasks.forEachIndexed { index, task ->
            consoleIO.println("${index + 1}) ${task.name} - [${task.state}]")
        }
    }

    private fun getValidatedInputString(): String {
        while (true) {
            val input = consoleIO.readFromUser().trim()
            if (input.isNotEmpty()) return input
            consoleIO.println("Input cannot be empty. Please try again.")
        }
    }

    private fun getValidatedTaskIndex(): Int {
        while (true) {
            consoleIO.println("Enter task number:")
            val input = consoleIO.readFromUser().trim().toIntOrNull()
            if (input != null && input in 1..currentTasks.size) {
                return input - 1
            }
            consoleIO.println("Invalid task number. Please try again.")
        }
    }
}
