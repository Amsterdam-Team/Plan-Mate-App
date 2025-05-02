package ui.controllers

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.usecases.project.CreateProjectUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
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
        val inputProjectName = "\n"
        System.setIn(ByteArrayInputStream(inputProjectName.toByteArray()))

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("Project name shouldn't be empty,Please enter the project name")
    }

    @Test
    fun `should print The project name is not valid when the name is invalid`() {
        //given
        val inputProjectName = "13515#$%#$"
        System.setIn(ByteArrayInputStream(inputProjectName.toByteArray()))

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("The project name is not valid. Please enter a valid project name.")
    }

    @Test
    fun `should print project states shouldn't be empty when the state is empty`() {
        //given
        val inputProjectState = "\n"
        System.setIn(ByteArrayInputStream(inputProjectState.toByteArray()))

        //when
        controller.execute()

        //then
        val output = outContent.toString()
        assertThat(output).contains("Project states shouldn't be empty,Please enter the project states")
    }

}