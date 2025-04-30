package ui.controllers

import com.google.common.truth.Truth.assertThat
import helpers.DeleteTaskTestFactory.ALL_PROJECTS
import helpers.DeleteTaskTestFactory.PROJECT_1
import helpers.DeleteTaskTestFactory.TASK_1
import helpers.DeleteTaskTestFactory.TASK_2
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
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
        assertThrows<ProjectNotFoundException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should print all project IDs`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS

        // When
        controller.deleteTaskUI()

        // Then
        assertThat(outContent.toString()).contains(PROJECT_1.id.toString())
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        provideInput("not-a-uuid")

        // When & Then
        assertThrows<InvalidProjectIDException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should throw NoTasksFoundException when there are no tasks in the selected project`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(PROJECT_1.id) } returns emptyList()
        provideInput(PROJECT_1.id.toString())

        // When & Then
        assertThrows<TaskNotFoundException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should print task IDs when tasks exist for the selected project`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(PROJECT_1.id) } returns
                listOf(TASK_1, TASK_2)
        provideInput(PROJECT_1.id.toString())

        // When
        controller.deleteTaskUI()

        // Then
        assertThat(outContent.toString()).contains(TASK_1.id.toString())
    }

    @Test
    fun `should call deleteTaskUseCase with correct params when task is selected`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(PROJECT_1.id) } returns listOf(TASK_1, TASK_2)
        provideInput(PROJECT_1.id.toString())
        provideInput(TASK_1.id.toString())

        // When
        controller.deleteTaskUI()

        // Then
        verify (exactly = 1){ deleteTaskUseCase.execute(TASK_1.id) }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not UUID`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every {
            taskRepository.getAllTasksByProjectId(PROJECT_1.id)
        } returns listOf(TASK_1)
        provideInput(PROJECT_1.id.toString())
        provideInput("not-a-uuid")

        // When & Then
        assertThrows<InvalidTaskIDException> {
            controller.deleteTaskUI()
        }
    }

    @Test
    fun `should throw TaskNotFoundException when task is not found`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every {
            taskRepository.getAllTasksByProjectId(PROJECT_1.id)
        } returns  listOf(TASK_1)
        provideInput(PROJECT_1.id.toString())
        provideInput(TASK_2.id.toString())

        // When & Then
        assertThrows<TaskNotFoundException> {
            controller.deleteTaskUI()
        }
    }

    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
