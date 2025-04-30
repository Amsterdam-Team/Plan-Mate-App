package logic.usecases

import helpers.DeleteTaskTestFactory.EMPTY_TASKS_PROJECT
import helpers.DeleteTaskTestFactory.PROJECT_1
import helpers.DeleteTaskTestFactory.TASK_1
import helpers.DeleteTaskTestFactory.TASK_3
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.NotFoundException.TaskNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var projectRepository: ProjectRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        projectRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(projectRepository,taskRepository)
    }
//region success
    @Test
    fun `should call deleteTask when task is found`() {
        // Given
        val task = TASK_1
        every { projectRepository.getProject(task.projectId)} returns PROJECT_1
        every { taskRepository.getTaskById(task.id) } returns task

        // When
        deleteTaskUseCase.execute(task.projectId.toString(),task.id.toString())

        // Then
        verify(exactly = 1) { taskRepository.deleteTask(task.id) }
    }
//endregion
//region project validations
    @Test
    fun `should throw InvalidProjectIDException when input is null`() {
        // Given
        val task = TASK_1

        // When & Then
        assertThrows<InvalidProjectIDException> {
            deleteTaskUseCase.execute(null,task.id.toString())
        }
    }

    @Test
    fun `should throw InvalidProjectIDException when input is not UUID`() {
        // Given
        val task = TASK_1

        // When & Then
        assertThrows<InvalidProjectIDException> {
            deleteTaskUseCase.execute("not-uuid",task.id.toString())
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when there are no project found`(){
        //Given
        val task = TASK_1
        every { projectRepository.getProject(task.projectId)} throws ProjectNotFoundException

        // When & Then
        assertThrows<ProjectNotFoundException> {
            deleteTaskUseCase.execute(task.projectId.toString(),task.id.toString())
        }
    }
//endregion
//region task validations
    @Test
    fun `should throw InvalidTaskIDException when input is null`() {
        // Given
        val task = TASK_1
        every { projectRepository.getProject(task.projectId)} returns PROJECT_1
        every { taskRepository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<InvalidTaskIDException> {
            deleteTaskUseCase.execute(TASK_1.projectId.toString(),null)
        }
    }

    @Test
    fun `should throw InvalidTaskIDException when input is not a uuid`() {
        // Given
        val task = TASK_1
        every { projectRepository.getProject(task.projectId)} returns PROJECT_1
        every { taskRepository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<InvalidTaskIDException> {
            deleteTaskUseCase.execute(TASK_1.projectId.toString(),"not-uuid")
        }
    }

    @Test
    fun `should throw TaskNotFoundException when there are no tasks found in the project`(){
        //Given
        val task = TASK_1
        val emptyTasksProject = EMPTY_TASKS_PROJECT
        every { projectRepository.getProject(emptyTasksProject.id)} returns emptyTasksProject

        // When & Then
        assertThrows<TaskNotFoundException> {
            deleteTaskUseCase.execute(emptyTasksProject.id.toString(),task.id.toString())
        }
    }

    @Test
    fun `should throw TaskNotFoundException when the task is not found in the project`() {
        // Given
        val task = TASK_3
        every { projectRepository.getProject(task.projectId)} returns PROJECT_1
        every { taskRepository.getTaskById(task.id) } returns task
        // When & Then
        assertThrows<TaskNotFoundException> {
            deleteTaskUseCase.execute(TASK_1.projectId.toString(),task.id.toString())
        }
    }


//endregion


}
