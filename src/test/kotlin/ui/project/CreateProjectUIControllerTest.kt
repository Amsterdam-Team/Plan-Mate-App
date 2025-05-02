package ui.controllers

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.ValidationException.*
import logic.usecases.project.CreateProjectUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CreateProjectUIControllerTest {
    private lateinit var useCase: CreateProjectUseCase
    private lateinit var controller: CreateProjectUIController
    private val standardOut = System.out
    private val outContent = ByteArrayOutputStream()

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        controller = CreateProjectUIController(useCase)
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `should print project name shouldn't be empty when the name is empty`() {
        //given
        every { useCase.createProject(any()) } throws EmptyProjectNameException

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("Project name shouldn't be empty,Please enter the project name")
    }

    @Test
    fun `should print The project name is not valid when the name is invalid`() {
        //given
        every { useCase.createProject(any()) } throws InvalidProjectNameException

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("The project name is not valid. Please enter a valid project name.")
    }

    @Test
    fun `should print project states shouldn't be empty when the state is empty`() {
        //given
        every { useCase.createProject(any()) } throws EmptyProjectStatesException

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("Project states shouldn't be empty,Please enter the project states")
    }

    @Test
    fun `should print The state name is not valid Please enter a valid name when the state is invalid`() {
        //given
        every { useCase.createProject(any()) } throws InvalidStateNameException

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("The state name is not valid. Please enter a valid name.")
    }
}