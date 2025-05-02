package ui.project

import io.mockk.*
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.usecases.project.GetProjectUseCase
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import ui.console.ConsoleIO
import java.util.*
import kotlin.test.Test

class GetProjectUIControllerTest {
    private lateinit var usecase: GetProjectUseCase
    private lateinit var uiController: GetProjectUIController
    private lateinit var consoleIO: ConsoleIO

    @BeforeEach
    fun setup() {
        consoleIO = mockk(relaxed = true)
        usecase = mockk(relaxed = true)
        uiController = GetProjectUIController(usecase, consoleIO)
    }

    @Test
    fun `should execute view project successfully when project id is valid`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val project = createProject(UUID.fromString(projectID), "Plan Mate App", listOf(), listOf())
        every { consoleIO.println(any()) } just Runs
        every { usecase.getProject(UUID.fromString(projectID)) } returns project
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println(project.toString()) }
    }

    @Test
    fun `should show project not exist when getProjectUseCase throws InvalidProjectIDException`() {
        //Given
        val projectID = "2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        every { consoleIO.println(any()) } just Runs
        every { usecase.getProject(UUID.fromString(projectID)) } throws InvalidProjectIDException
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("Project not exist") }
    }

    @Test
    fun `should show projects is empty when getProjectUseCase throws EmptyDataException`() {
        //Given
        val projectID = "2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        every { consoleIO.println(any()) } just Runs
        every { usecase.getProject(UUID.fromString(projectID)) } throws EmptyDataException
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("Projects is empty") }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            " ",
            "id",
            "db373589-b656-4e68 a7c0-2ccc705ca169",
            " db373589-b656#4e68@a7c0-2ccc705ca169"
        ]
    )
    fun `should show invalid id format when getProjectUseCase throws InvalidProjectIDException`(invalidID: String) {
        val projectID = UUID.fromString(invalidID)
        //Given
        every { consoleIO.println(any()) } just Runs
        every { usecase.getProject(projectID) } throws InvalidProjectIDException
        //When
        uiController.execute()
        // Then
        verify { consoleIO.println("Projects is empty") }


    }

}