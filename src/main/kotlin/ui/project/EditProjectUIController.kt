package ui.project

import console.ConsoleIO
import logic.entities.User
import logic.usecases.project.EditProjectUseCase
import ui.controller.BaseUIController
import ui.utils.getErrorMessageByException
import ui.utils.tryToExecute
import java.util.*

//    class EditProjectUIController(
//        private val editProjectUseCase: EditProjectUseCase,
//        private val consoleIO: ConsoleIO
//    ): BaseUIController {
//        fun handleEditProject(user: User) {
//            try {
//                println("🔧 Edit Project Name")
//
//                print("📌 Enter project ID: ")
//                val projectId = readln().trim().let { UUID.fromString(it) }
//
//                print("✏️ Enter new project name: ")
//                val newName = readln().trim()
//
//                editProjectUseCase.editProjectName(user, projectId, newName)
//
//                println("✅ Project name updated successfully!")
//
//            } catch (e: Exception) {
//                val message = getErrorMessageByException(e)
//                println("❌ $message")
//            }
//        }
//
//        override fun execute() {
//
//        }
//    }

class EditProjectUIController(
    private val editProjectUseCase: EditProjectUseCase,
    private val consoleIO: ConsoleIO
) : BaseUIController {

    override fun execute() {
        consoleIO.println(EDIT_PROJECT_NAME_PROMPT_MESSAGE)

        consoleIO.println(PROJECT_ID_PROMPT_MESSAGE)
        val projectId = consoleIO.readFromUser().trim().let { UUID.fromString(it) }

        consoleIO.println(NEW_PROJECT_NAME_PROMPT_MESSAGE)
        val newName = consoleIO.readFromUser().trim()

        tryToExecute(
            action = {
                editProjectUseCase.editProjectName(user =
                    projectId = projectId, newName = newName)
            },
            onSuccess = ::onEditProjectSuccess,
            onError = ::onEditProjectFail
        )
    }

    private fun onEditProjectSuccess() {
        consoleIO.println(PROJECT_UPDATED_SUCCESSFULLY_MESSAGE)
    }

    private fun onEditProjectFail(exception: Exception) {
        consoleIO.println("❌ ${getErrorMessageByException(exception)}")
        val input = consoleIO.readFromUser().trim().uppercase()

        when (input) {
            RETRY.uppercase() -> execute()  // إعادة المحاولة
            CANCEL.uppercase() -> return    // التوقف
            else -> onEditProjectFail(exception)  // إعادة المحاولة في حالة الإدخال غير الصحيح
        }
    }

    private fun getErrorMessageByException(exception: Exception): String {
        return when (exception) {
            is IllegalArgumentException -> "Invalid input, please try again."
            else -> "Something went wrong, please try again."
        }
    }

    private companion object {
        // الرسائل الثابتة
        const val EDIT_PROJECT_NAME_PROMPT_MESSAGE = "Edit Project Name"
        const val PROJECT_ID_PROMPT_MESSAGE = "Enter project ID: "
        const val NEW_PROJECT_NAME_PROMPT_MESSAGE = "Enter new project name: "
        const val PROJECT_UPDATED_SUCCESSFULLY_MESSAGE = "✅ Project name updated successfully!"
        const val RETRY = "retry"
        const val CANCEL = "cancel"
    }
}


