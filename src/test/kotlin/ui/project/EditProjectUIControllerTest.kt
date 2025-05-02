package ui.project

import io.mockk.mockk
import helpers.EditProjectFactory
import logic.usecases.project.EditProjectUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.controller.EditProjectUIController
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class EditProjectUIControllerTest {

    private lateinit var editProjectUseCase: EditProjectUseCase
    private lateinit var controller: EditProjectUIController

    private val outputStreamCaptor = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
        editProjectUseCase = mockk()
        controller = EditProjectUIController(editProjectUseCase)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `should print success message when project is edited successfully`() {

        EditProjectFactory.simulateUserInputs(EditProjectFactory.validProjectId.toString(), "Updated Project").use {
            controller.handleEditProject(EditProjectFactory.adminUser())
        }

        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("✅ Project name updated successfully!"))
    }

    @Test
    fun `should print error when project name is blank`() {

        EditProjectFactory.simulateUserInputs(EditProjectFactory.validProjectId.toString(), "   ").use {
            controller.handleEditProject(EditProjectFactory.adminUser())
        }

        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("❌ Project name cannot be empty. Please provide a valid name."))
    }

    @Test
    fun `should print error when input project ID is invalid`() {
        EditProjectFactory.simulateUserInputs("not-a-uuid", "New Project Name").use {
            controller.handleEditProject(EditProjectFactory.adminUser())
        }

        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("❌ Invalid input format. Please enter a valid UUID."))
    }

    @Test
    fun `should print error when user is not admin`() {

        EditProjectFactory.simulateUserInputs(EditProjectFactory.validProjectId.toString(), "Another Name").use {
            controller.handleEditProject(EditProjectFactory.mateUser)
        }

        val output = outputStreamCaptor.toString().trim()
        assertTrue(output.contains("❌ You don’t have permission to perform this action. Admin privileges required."))
    }

}
