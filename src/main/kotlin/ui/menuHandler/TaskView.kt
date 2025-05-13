package ui.menuHandler

import logic.entities.Project
import logic.entities.Task
import logic.usecases.logs.ViewTaskLogsUseCase
import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.DeleteTaskUseCase
import logic.usecases.task.EditTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import ui.console.ConsoleIO
import ui.utils.getErrorMessageByException

class TaskManagerView(
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val consoleIO: ConsoleIO
) {
    private lateinit var currentProject: Project
    private lateinit var currentTasks: List<Task>

    suspend fun showTaskOptions(project: Project) {
        try {
            currentProject = project
            currentTasks = currentProject.tasks
            consoleIO.println(
                """
            Options:
            [1] Create a New Task
            [2] Delete a Task
            [3] Show Task Logs
            [4] Edit Task
            [0] Go Back to Projects
        """.trimIndent()
            )

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
        } catch (e: Exception) {
            getErrorMessageByException(e)
        }

    }

    private suspend fun createTask() {
        consoleIO.println("Enter task name:")
        val taskName = getValidatedInputString()

        consoleIO.println("Enter task state; ${currentProject.states}:")
        val taskState = getValidatedInputString()

        try {
            createTaskUseCase.createTask(taskName, currentProject.id.toString(), taskState)
            consoleIO.println("Task '$taskName' created successfully.")

            refreshProjectTasks()

        } catch (e: Exception) {
            consoleIO.println(getErrorMessageByException(e))
        }

        showTaskOptions(currentProject)
    }


    private suspend fun deleteTask() {
        showAllTasks()
        val index = getValidatedTaskIndex()
        val selectedTask = currentTasks[index]

        try {
            val isDeleted = deleteTaskUseCase.execute(selectedTask.id.toString())

            if (isDeleted) {
                consoleIO.println("Task '${selectedTask.name}' deleted successfully.")

                refreshProjectTasks()

                if (currentTasks.isEmpty()) {
                    consoleIO.println("No tasks remaining in this project.")
                }
            } else {
                consoleIO.println("Failed to delete task.")
            }
        } catch (e: Exception) {
            consoleIO.println(getErrorMessageByException(e))
        }

        showTaskOptions(currentProject)
    }


    private suspend fun showTaskLogs() {
        showAllTasks()
        val index = getValidatedTaskIndex()
        val selectedTask = currentTasks[index]

        consoleIO.println("Logs for Task '${selectedTask.name}':")

        val logs = viewTaskLogsUseCase.viewTaskLogs(selectedTask.id.toString())

        if (logs.isEmpty()) {
            consoleIO.println("No logs available for this task.")
        } else {
            logs.forEach { log ->
                consoleIO.println("- ${log.message}")
            }
        }

        showTaskOptions(currentProject)
    }

    private suspend fun editTask() {
        showAllTasks()
        val index = getValidatedTaskIndex()
        val selectedTask = currentTasks[index]

        consoleIO.println("Editing Task '${selectedTask.name}'")

        consoleIO.println("Enter new name (leave blank to keep '${selectedTask.name}'):")
        val newName = consoleIO.readFromUser().trim().ifEmpty { selectedTask.name }

        val validStates = currentProject.states
        var newState: String
        while (true) {
            consoleIO.println("Enter new state (leave blank to keep '${selectedTask.state}'): $validStates")
            val input = consoleIO.readFromUser().trim()
            newState = input.ifBlank { selectedTask.state }

            if (validStates.contains(newState)) break
            consoleIO.println("Invalid state. Please enter one of the listed states.")
        }

        try {
            val updated = editTaskUseCase.editTask(
                taskId = selectedTask.id.toString(),
                newName = newName,
                newState = newState
            )

            if (updated) {
                consoleIO.println("Task updated successfully.")
            } else {
                consoleIO.println("No changes were made.")
            }

            refreshProjectTasks()

        } catch (e: Exception) {
            consoleIO.println(getErrorMessageByException(e))
        }

        showTaskOptions(currentProject)
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

    private suspend fun refreshProjectTasks() {
        val allTasks = getAllTasksByProjectIdUseCase(currentProject.id.toString())
        currentTasks = allTasks.filter { currentProject.states.contains(it.state) }

        currentProject = currentProject.copy(tasks = currentTasks)
    }

}