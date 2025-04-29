package ui

import logic.usecases.ViewTaskLogsUseCase
import java.util.UUID

class ViewTaskLogsUIController(
    private val viewTaskLogsUseCase: ViewTaskLogsUseCase
) {

    fun viewTaskLogsUIController(){

    }

    fun validateTaskId(taskID : String): UUID{
        return UUID.randomUUID()
    }
}