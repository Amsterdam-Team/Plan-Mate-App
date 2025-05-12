package logic.usecases.task

import helper.ConstantsFactory.INVALID_PROJECT_ID
import helper.ConstantsFactory.INVALID_STATE
import helper.ConstantsFactory.INVALID_TASK_NAME
import helper.ConstantsFactory.NON_EXISTENT_STATE
import helper.TaskFactory.existingStates
import helper.TaskFactory.taskWithUnExistingProjectID
import helper.TaskFactory.validTask
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.NotFoundException.StateNotFoundException
import logic.repository.IProjectRepository
import logic.repository.ITaskRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CreateTaskUseCaseTest {

    private lateinit var taskRepository: ITaskRepository
    private lateinit var projectRepository: IProjectRepository
    private lateinit var useCase: CreateTaskUseCase
    private val validateInputUseCase = ValidateInputUseCase()
    private lateinit var loggerUseCase: LoggerUseCase

    @BeforeEach
    fun setup() =runTest {
        taskRepository = mockk(relaxed = true)
        projectRepository = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        useCase = CreateTaskUseCase(taskRepository, projectRepository, validateInputUseCase,loggerUseCase)
    }

    @Test
    fun `should create task when all input are valid`() =runTest {
        //Given
        coEvery { projectRepository.getProjectById(validTask.projectId) } returns mockk {
            every { id } returns validTask.projectId
            every { states } returns listOf(validTask.state)
        }
        coEvery { taskRepository.createTask(validTask) } returns false

        //When & Then
        assertDoesNotThrow {
            useCase.createTask(
                validTask.name,
                validTask.projectId.toString(),
                validTask.state
            )
        }
    }

    @Test
    fun `should throw ProjectNotFoundException when project does not exist`() =runTest {
        // Given
        coEvery { projectRepository.getProjectById(taskWithUnExistingProjectID.projectId) } throws ProjectNotFoundException

        //When & Then
        assertThrows<ProjectNotFoundException> {
            useCase.createTask(
                taskWithUnExistingProjectID.name,
                taskWithUnExistingProjectID.projectId.toString(),
                taskWithUnExistingProjectID.state
            )
        }
    }

    @Test
    fun `should throw InvalidTaskNameException when task name is invalid`() =runTest {
        // Given
        coEvery { projectRepository.getProjectById(validTask.projectId) } returns mockk {
            every { id } returns validTask.projectId
            every { states } returns listOf(validTask.state)
        }

        // When & Then
        assertThrows<InvalidTaskNameException> {
            useCase.createTask(INVALID_TASK_NAME, validTask.projectId.toString(), validTask.state)
        }
    }

    @Test
    fun `should throw InvalidProjectIDException when project ID is invalid`() =runTest {
        // Given
        coEvery { projectRepository.getProjectById(UUID.fromString(validTask.projectId.toString())) } returns mockk {
            every { id } returns validTask.projectId
            every { states } returns listOf(validTask.state)
        }

        // When & Then
        assertThrows<InvalidProjectIDException> {
            useCase.createTask(validTask.name, INVALID_PROJECT_ID, validTask.state)
        }
    }

    @Test
    fun `should throw InvalidStateNameException when state name is invalid`() =runTest {
        // Given
        coEvery { projectRepository.getProjectById(validTask.projectId) } returns mockk {
            every { id } returns validTask.projectId
            every { states } returns listOf(validTask.state)
        }

        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidStateNameException> {
            useCase.createTask(validTask.name, validTask.projectId.toString(), INVALID_STATE)
        }
    }

    @Test
    fun `should throw StateNotFoundException when state is not found in project`() =runTest {
        // Given
        coEvery { projectRepository.getProjectById(validTask.projectId) } returns mockk {
            every { id } returns validTask.projectId
            every { states } returns existingStates
        }

        // When & Then
        assertThrows<StateNotFoundException> {
            useCase.createTask(validTask.name, validTask.projectId.toString(), NON_EXISTENT_STATE)
        }
    }
}