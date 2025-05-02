package ui

import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.task.EditTaskUseCase
import ui.console.ConsoleIO
import ui.controller.BaseUIController
import ui.utils.tryToExecute

class EditTaskUiController(val editTaskUseCase: EditTaskUseCase, val consoleIO: ConsoleIO): BaseUIController {
    override fun execute() {

        if (isEditNameOnly()){
            val taskId = getTaskId()
            val newName = getNewTaskInfo("name")
            tryToExecute<Boolean>(
                action =  {editTaskName(taskId, newName)},
                onSuccess = {onSuccess()}
            )
        }else {
            val taskId = getTaskId()
            val newName = getNewTaskInfo("name")
            val newState = getNewTaskInfo("state")
            tryToExecute<Boolean>(
                action =  {editTask(taskId, newName,newState)},
                onSuccess = {onSuccess()}
            )
        }

    }



    private fun editTaskName(taskId: String, newName:String): Boolean{
        return editTaskUseCase.editTaskName(taskId,newName)
    }
    private fun editTask(taskId:String, newName:String, newState:String): Boolean{
        return editTaskUseCase.editTaskNameAndState(taskId,newName,newState)
    }
    private fun getTaskId(): String{
        consoleIO.println("please enter task id here: ")
        while (true){
            val taskId = consoleIO.readFromUser()

            if (taskId.isNotBlank() && taskId.isNotEmpty()){
                return taskId
            }
        }
    }
    private fun getNewTaskInfo(type: String): String{
        consoleIO.println("please enter new ${type} here: ")
        while (true){
            val info = consoleIO.readFromUser()
            if (info.isNotBlank() && info.isNotEmpty()){
                return info
            }
        }
    }
    private fun onSuccess(){
        consoleIO.println("Task name updated successfully")
    }

     fun isEditNameOnly():Boolean{  // if not mean edit name and state
        consoleIO.println("enter 1 for updating name and 2 for updating name and state")
        while (true){
            val input = consoleIO.readFromUser()
            when(val choice = input.toIntOrNull()){
                null -> "please enter valid number"
                1-> return true
                2 -> return false
                else -> "please enter 1 for updating name or 2 for updating name and state"
            }
        }
    }

}