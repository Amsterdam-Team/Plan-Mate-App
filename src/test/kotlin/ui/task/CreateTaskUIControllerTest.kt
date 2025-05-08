package ui.task

import com.google.common.truth.Truth
import helper.CreateTaskUIControllerTestFactory
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.usecases.task.CreateTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CreateTaskUIControllerTest {

    private lateinit var useCase: CreateTaskUseCase
    private lateinit var controller: CreateTaskUIController
    private lateinit var consoleIo: ConsoleIO
    private lateinit var outContent: ByteArrayOutputStream


    @BeforeEach
    fun setUp() {
        useCase = mockk()
        consoleIo = mockk()
        controller = CreateTaskUIController(useCase, consoleIo)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        every { consoleIo.println(any()) } just Runs
    }

    @Test
    fun `should call use case function exactly once when execute takes the input`() = runTest {
        //Given
        coEvery { useCase.createTask(any(), any(), any()) } returns true
        every { consoleIo.readFromUser() } returnsMany listOf(
            CreateTaskUIControllerTestFactory.VALID_TASK_NAME,
            CreateTaskUIControllerTestFactory.VALID_PROJECT_ID,
            CreateTaskUIControllerTestFactory.VALID_STATE
        )

        //When
        controller.execute()

        //Then
        coVerify(exactly = 1) {
            useCase.createTask(
                name = CreateTaskUIControllerTestFactory.VALID_TASK_NAME,
                projectId = CreateTaskUIControllerTestFactory.VALID_PROJECT_ID,
                state = CreateTaskUIControllerTestFactory.VALID_STATE
            )
        }
    }

    @Test
    fun `should show success message when task is created`()= runTest {
        // Given
        every { consoleIo.readFromUser() } returnsMany listOf(
            CreateTaskUIControllerTestFactory.VALID_TASK_NAME,
            CreateTaskUIControllerTestFactory.VALID_PROJECT_ID,
            CreateTaskUIControllerTestFactory.VALID_STATE
        )
        coEvery { useCase.createTask(any(), any(), any()) } returns true

        // When
        controller.execute()

        // Then
        verify { consoleIo.println(CreateTaskUIControllerTestFactory.TASK_CREATED_SUCCESSFULLY_MESSAGE) }
    }

    @Test
    fun `should show correct error message when input invalid task name`()= runTest {
        every { consoleIo.readFromUser() } returnsMany CreateTaskUIControllerTestFactory.getDummyInputs(projectId = CreateTaskUIControllerTestFactory.IN_VALID_TASK_NAME)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                PlanMateException.ValidationException.InvalidTaskNameException

        controller.execute()

        Truth.assertThat(outContent.toString())
            .contains(CreateTaskUIControllerTestFactory.INVALID_TASK_NAME_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input invalid project id`()= runTest {
        every { consoleIo.readFromUser() } returnsMany CreateTaskUIControllerTestFactory.getDummyInputs(projectId = CreateTaskUIControllerTestFactory.IN_VALID_PROJECT_ID)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                PlanMateException.ValidationException.InvalidProjectIDException

        controller.execute()

        Truth.assertThat(outContent.toString())
            .contains(CreateTaskUIControllerTestFactory.INVALID_PROJECT_ID_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input invalid task state`() = runTest {
        every { consoleIo.readFromUser() } returnsMany CreateTaskUIControllerTestFactory.getDummyInputs(taskState = CreateTaskUIControllerTestFactory.IN_VALID_STATE)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                PlanMateException.ValidationException.InvalidStateNameException

        controller.execute()

        Truth.assertThat(outContent.toString())
            .contains(CreateTaskUIControllerTestFactory.INVALID_STATE_NAME_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input state name does not exist in the target project`() = runTest {
        every { consoleIo.readFromUser() } returnsMany CreateTaskUIControllerTestFactory.getDummyInputs(taskState = CreateTaskUIControllerTestFactory.VALID_STATE)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                PlanMateException.NotFoundException.StateNotFoundException

        controller.execute()

        Truth.assertThat(outContent.toString())
            .contains(CreateTaskUIControllerTestFactory.STATE_NOT_FOUND_ERROR_MESSAGE)
    }

}