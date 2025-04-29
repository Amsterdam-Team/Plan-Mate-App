package ui.controllers

import com.google.common.truth.Truth.assertThat
import helpers.DeleteTaskTestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class DeleteTaskUIControllerTest {

    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    private lateinit var taskRepository: TaskRepository
    private lateinit var projectRepository: ProjectRepository
    private lateinit var controller: DeleteTaskUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        deleteTaskUseCase = mockk(relaxed = true)
        taskRepository = mockk()
        projectRepository = mockk()
        controller = DeleteTaskUIController(deleteTaskUseCase, projectRepository,taskRepository)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should throw NoProjectsFoundException when there are no projects`() {
        // Given
        every { projectRepository.getProjects() } returns emptyList()

        // When & Then
        assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should print all project IDs`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS

        // When
        controller.deleteTaskUI()

        // Then
        assertThat(outContent.toString()).contains(DeleteTaskTestFactory.PROJECT_1.id.toString())
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS

        // When & Then
        provideInput("not-a-uuid")

        assertThrows<PlanMateException.ValidationException.InvalidProjectIDException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should throw NoTasksFoundException when there are no tasks in the selected project`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(DeleteTaskTestFactory.PROJECT_1.id) } returns emptyList()

        // When & Then
        provideInput(DeleteTaskTestFactory.PROJECT_1.id.toString())
        assertThrows<PlanMateException.NotFoundException.TaskNotFoundException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should print task IDs when tasks exist for the selected project`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(DeleteTaskTestFactory.PROJECT_1.id) } returns
                listOf(DeleteTaskTestFactory.TASK_1, DeleteTaskTestFactory.TASK_2)

        // When
        provideInput(DeleteTaskTestFactory.PROJECT_1.id.toString())
        controller.deleteTaskUI()

        // Then
        assertThat(outContent.toString()).contains(DeleteTaskTestFactory.TASK_1.id.toString())
    }

    @Test
    fun `should call deleteTaskUseCase with correct params when task is selected`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(DeleteTaskTestFactory.PROJECT_1.id) } returns listOf(DeleteTaskTestFactory.TASK_1, DeleteTaskTestFactory.TASK_2)
        provideInput(DeleteTaskTestFactory.PROJECT_1.id.toString())
        provideInput(DeleteTaskTestFactory.TASK_1.id.toString())

        // When
        controller.deleteTaskUI()

        // Then
        verify (exactly = 1){ deleteTaskUseCase.execute(DeleteTaskTestFactory.TASK_1.id) }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not UUID`() {
        // Given
        every { projectRepository.getProjects() } returns DeleteTaskTestFactory.ALL_PROJECTS
        every {
            taskRepository.getAllTasksByProjectId(DeleteTaskTestFactory.PROJECT_1.id)
        } returns listOf(DeleteTaskTestFactory.TASK_1)

        // When & Then
        provideInput(DeleteTaskTestFactory.PROJECT_1.id.toString())
        provideInput("not-a-uuid")

        assertThrows<PlanMateException.ValidationException.InvalidTaskIDException> {
            controller.deleteTaskUI()
        }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
