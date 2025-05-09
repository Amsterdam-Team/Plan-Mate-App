package ui.task

import logic.usecases.task.DeleteTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class DeleteTaskUIController(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val consoleIO: ConsoleIO
): BaseUIController {
    override suspend  fun execute() {
        tryToExecute(
            action = {
                consoleIO.println("Please Enter Task ID:")
                deleteTaskUseCase.execute(consoleIO.readFromUser())
            },
            onSuccess = { isTaskDeleted ->
                if (isTaskDeleted){
                    consoleIO.println("✅ Task deleted successfully.")
                }else{
                    consoleIO.println("❌ Failed to delete task. Please try again.")
                }
            }
        )
    }
}