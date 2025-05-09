package ui.project

import com.google.common.truth.Truth.assertThat
import helper.ProjectFactory.inValidProjectNameTest
import helper.ProjectFactory.validProjectTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidProjectNameException
import logic.usecases.project.CreateProjectUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.console.ConsoleIO
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CreateProjectUIControllerTest {
    private lateinit var useCase: CreateProjectUseCase
    private lateinit var controller: CreateProjectUIController
    private lateinit var consoleIo: ConsoleIO
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        consoleIo = mockk()
        controller = CreateProjectUIController(useCase, consoleIo)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        every { consoleIo.println(any()) } just Runs
    }

    @Test
    fun `should call create project use case only once when user enter all inputs`() = runTest {
        coEvery { useCase.createProject(any(), any()) } returns true
        every { consoleIo.readFromUser() } returnsMany listOf(
            validProjectTest.name, validProjectTest.states.joinToString(", ")
        )

        controller.execute()

        coVerify(exactly = 1) {
            useCase.createProject(validProjectTest.name, validProjectTest.states)
        }
    }

    @Test
    fun `should show success message when project is created successfully`() = runTest {
        // Given
        every { consoleIo.readFromUser() } returnsMany listOf(
            validProjectTest.name, validProjectTest.states.toString()
        )
        coEvery { useCase.createProject(any(), any()) } returns true

        // When
        controller.execute()

        // Then
        verify { consoleIo.println("✅ Your project has been Created Successfully") }
    }

    @Test
    fun `should show error message when input project name is invalid`() = runTest {
        every { consoleIo.readFromUser() } returnsMany listOf(
            inValidProjectNameTest.name, inValidProjectNameTest.states.toString(), "CANCEL"
        )
        coEvery { useCase.createProject(any(), any()) } throws InvalidProjectNameException


        controller.execute()

        assertThat(outContent.toString()).contains("The project name is not valid. Please enter a valid project name.")
    }

    @Test
    fun `should show error message when input project states is invalid`() = runTest {
        // Given
        every { consoleIo.readFromUser() } returnsMany listOf(
            inValidProjectNameTest.name, inValidProjectNameTest.states.toString(), "CANCEL"
        )
        coEvery {
            useCase.createProject(any(), any())
        } throws PlanMateException.ValidationException.InvalidStateNameException

        // When
        controller.execute()

        // Then
        verify {
            consoleIo.println(match { it.contains("❌ Please Enter All Inputs Correctly, inter retry or cancel") })
        }
    }
}