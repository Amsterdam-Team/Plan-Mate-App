package ui.project

import console.ConsoleIO
import logic.entities.User
import ui.controller.BaseUIController
import ui.utils.getErrorMessageByException
import java.util.*

    class EditProjectUIController(
        private val editProjectUseCase: EditProjectUseCase,
        private val consoleIO: ConsoleIO
    ): BaseUIController {
        fun handleEditProject(user: User) {
            try {
                println("🔧 Edit Project Name")

                print("📌 Enter project ID: ")
                val projectId = readln().trim().let { UUID.fromString(it) }

                print("✏️ Enter new project name: ")
                val newName = readln().trim()

                editProjectUseCase.editProjectName(user, projectId, newName)

                println("✅ Project name updated successfully!")

            } catch (e: Exception) {
                val message = getErrorMessageByException(e)
                println("❌ $message")
            }
        }

        override fun execute() {

        }
    }

