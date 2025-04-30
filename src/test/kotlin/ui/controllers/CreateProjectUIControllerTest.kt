package ui.controllers

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecases.project.CreateProjectUseCase
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectNameTest
import logic.usecases.testFactories.CreateProjectTestFactory.emptyProjectStateTest
import logic.usecases.testFactories.CreateProjectTestFactory.inValidProjectNameTest
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

        every { useCase.createProject(emptyProjectNameTest) }

        //when
        controller.createProjectUIController()

        //then
        val output = outContent.toString()
        assertThat(output).contains("project name shouldn't be empty")
    }

    @Test
    fun `should print invalid project name when the name is invalid`() {
        //given
        val inputProjectName = "13515#$%#$"
        System.setIn(ByteArrayInputStream(inputProjectName.toByteArray()))

        every { useCase.createProject(inValidProjectNameTest) }

        //when
        controller.createProjectUIController()

        //then
        val output = outContent.toString()
        assertThat(output).contains("invalid project name")
    }

    @Test
    fun `should print project states shouldn't be empty when the state is empty`() {
        //given
        val inputProjectState = "\n"
        System.setIn(ByteArrayInputStream(inputProjectState.toByteArray()))

        every { useCase.createProject(emptyProjectStateTest) }

        //when
        controller.createProjectUIController()

        //then
        val output = outContent.toString()
        assertThat(output).contains("project states shouldn't be empty")
    }

}