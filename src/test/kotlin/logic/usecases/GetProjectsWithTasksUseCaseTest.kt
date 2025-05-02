package logic.usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.entities.Task
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.BeforeEach
import java.util.UUID
import logic.usecases.task.testFactory.CreateTaskTestFactory
import org.junit.jupiter.api.Test

class GetProjectsWithTasksUseCaseTest{
    private lateinit var projectRepository: ProjectRepository
    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetProjectsWithTasksUseCase

    private val projectOneId = UUID.randomUUID()
    private val projectTwoId = UUID.randomUUID()

    private val projects = listOf(
        createProject(
            id = projectOneId,
            name = "project 1",
            states = listOf("to do", "in progress"),
            tasks = emptyList()
        ),
        createProject(
            id = projectTwoId,
            name = "project 2",
            states = listOf("to do", "in progress"),
            tasks = emptyList()
        )
    )

    private val tasksForProjectOne = listOf(
        CreateTaskTestFactory.createTaskWithProjectID(projectOneId),
        CreateTaskTestFactory.createTaskWithProjectID(projectOneId),
        CreateTaskTestFactory.createTaskWithProjectID(projectOneId),
        CreateTaskTestFactory.createTaskWithProjectID(projectOneId),
    )

    @BeforeEach
    fun setup(){
        projectRepository = mockk(relaxed = true)
        taskRepository = mockk(relaxed = true)
        useCase = GetProjectsWithTasksUseCase(projectRepository, taskRepository)
        every { projectRepository.getProjects() } returns projects
    }


    @Test
    fun `should link projectOne with matching tests when given tasks with projectOneId`(){
        // Given
        every { taskRepository.getAllTasksByProjectId(projectOneId) } returns tasksForProjectOne

        // When
        val result = useCase()

        // Then
        val projectOne = result.find { it.id == projectOneId }
        assertThat(tasksForProjectOne.toSet()).isEqualTo(projectOne?.tasks?.toSet())
    }

    @Test
    fun `should link projectTwo with empty list when no tasks exist with this project id`(){
        // Given
        every { taskRepository.getAllTasksByProjectId(projectTwoId) } returns emptyList()

        // When
        val result = useCase()

        // Then
        val projectTwo = result.find { it.id == projectTwoId }
        assertThat(emptyList<Task>()).isEqualTo(projectTwo?.tasks)
    }

    @Test
    fun `should return all projects even when no tasks can be linked`(){
        // When
        val result = useCase()

        // Then
        val returnedIds = result.map { it.id }.toSet()
        val expectedIds = projects.map { it.id }.toSet()
        assertThat(expectedIds).isEqualTo(returnedIds)
    }

}