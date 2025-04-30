package ui.controllers

import com.google.common.truth.Truth.assertThat
import helpers.DeleteTaskTestFactory.ALL_PROJECTS
import helpers.DeleteTaskTestFactory.PROJECT_1
import helpers.DeleteTaskTestFactory.TASK_1
import helpers.DeleteTaskTestFactory.TASK_2
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

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

    }


    @Test
    fun `should print all project IDs`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS

        // When
        controller.start()

        // Then
        assertThat(outContent.toString()).contains(PROJECT_1.id.toString())
    }

    @Test
    fun `should print task IDs in the selected project`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(PROJECT_1.id) } returns
                listOf(TASK_1, TASK_2)
        provideInput(PROJECT_1.id.toString())

        // When
        controller.start()

        // Then
        assertThat(outContent.toString()).contains(TASK_1.id.toString())
    }

    @Test
    fun `should call deleteTaskUseCase with params when task is selected`() {
        // Given
        every { projectRepository.getProjects() } returns ALL_PROJECTS
        every { taskRepository.getAllTasksByProjectId(PROJECT_1.id) } returns listOf(TASK_1, TASK_2)
        provideInput(PROJECT_1.id.toString())
        provideInput(TASK_1.id.toString())

        // When
        controller.start()

        // Then
        verify (exactly = 1){ deleteTaskUseCase.execute(PROJECT_1.id.toString(),TASK_1.id.toString()) }
    }


    private fun provideInput(input: String) {
        val stream = input.byteInputStream()
        System.setIn(stream)
    }
}
