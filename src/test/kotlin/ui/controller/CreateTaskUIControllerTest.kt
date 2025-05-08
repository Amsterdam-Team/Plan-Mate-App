package ui.controller

import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.ValidationException.InvalidTaskNameException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.NotFoundException.StateNotFoundException
import logic.usecases.task.CreateTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import helper.CreateTaskUIControllerTestFactory.INVALID_PROJECT_ID_ERROR_MESSAGE
import helper.CreateTaskUIControllerTestFactory.INVALID_STATE_NAME_ERROR_MESSAGE
import helper.CreateTaskUIControllerTestFactory.INVALID_TASK_NAME_ERROR_MESSAGE
import helper.CreateTaskUIControllerTestFactory.IN_VALID_PROJECT_ID
import helper.CreateTaskUIControllerTestFactory.IN_VALID_STATE
import helper.CreateTaskUIControllerTestFactory.IN_VALID_TASK_NAME
import helper.CreateTaskUIControllerTestFactory.STATE_NOT_FOUND_ERROR_MESSAGE
import helper.CreateTaskUIControllerTestFactory.TASK_CREATED_SUCCESSFULLY_MESSAGE
import helper.CreateTaskUIControllerTestFactory.VALID_PROJECT_ID
import helper.CreateTaskUIControllerTestFactory.VALID_STATE
import helper.CreateTaskUIControllerTestFactory.VALID_TASK_NAME
import helper.CreateTaskUIControllerTestFactory.getDummyInputs
import ui.task.CreateTaskUIController
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CreateTaskUIControllerTest {

    private lateinit var useCase: CreateTaskUseCase
    private lateinit var controller: CreateTaskUIController
    private lateinit var consoleIo: ConsoleIO
    private lateinit var outContent:ByteArrayOutputStream


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
        every { consoleIo.readFromUser() } returnsMany listOf(VALID_TASK_NAME, VALID_PROJECT_ID, VALID_STATE)

        //When
        controller.execute()

        //Then
        coVerify(exactly = 1) {
            useCase.createTask(name = VALID_TASK_NAME, projectId = VALID_PROJECT_ID, state = VALID_STATE)
        }
    }

    @Test
    fun `should show success message when task is created`()= runTest {
        // Given
        every { consoleIo.readFromUser() } returnsMany listOf(
            VALID_TASK_NAME, VALID_PROJECT_ID, VALID_STATE
        )
        coEvery { useCase.createTask(any(), any(), any()) } returns true

        // When
        controller.execute()

        // Then
        verify { consoleIo.println(TASK_CREATED_SUCCESSFULLY_MESSAGE) }
    }

    @Test
    fun `should show correct error message when input invalid task name`()= runTest {
        every { consoleIo.readFromUser() } returnsMany getDummyInputs(projectId = IN_VALID_TASK_NAME)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                InvalidTaskNameException

        controller.execute()

        assertThat(outContent.toString()).contains(INVALID_TASK_NAME_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input invalid project id`()= runTest {
        every { consoleIo.readFromUser() } returnsMany getDummyInputs(projectId = IN_VALID_PROJECT_ID)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                InvalidProjectIDException

        controller.execute()

        assertThat(outContent.toString()).contains(INVALID_PROJECT_ID_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input invalid task state`() = runTest{
        every { consoleIo.readFromUser() } returnsMany getDummyInputs(taskState = IN_VALID_STATE)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                InvalidStateNameException

        controller.execute()

        assertThat(outContent.toString()).contains(INVALID_STATE_NAME_ERROR_MESSAGE)
    }

    @Test
    fun `should show correct error message when input state name does not exist in the target project`() = runTest {
        every { consoleIo.readFromUser() } returnsMany getDummyInputs(taskState = VALID_STATE)

        coEvery { useCase.createTask(name = any(), projectId = any(), state = any()) } throws
                StateNotFoundException

        controller.execute()

        assertThat(outContent.toString()).contains(STATE_NOT_FOUND_ERROR_MESSAGE)
    }

}