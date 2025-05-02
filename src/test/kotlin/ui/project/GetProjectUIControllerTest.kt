package ui.project

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.usecases.project.GetProjectUseCase
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
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
    private fun captureSlot(): CapturingSlot<String> {
        val slot = slot<String>()
        every { consoleIO.println(capture(slot)) } just Runs
        return slot
    }

    @Test
    fun `should execute view project successfully when project id is valid`() {
        //Given
        val slot = captureSlot()
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val project = createProject(UUID.fromString(projectID), "", listOf(), listOf())
        every { usecase.getProject(projectID) } returns project
        //When
        uiController.execute()
        //Then
        assertThat(slot.captured == project.toString())

    }

    @Test
    fun `should show project not exist when getProjectUseCase throws ProjectNotFoundException`() {
        //Given
        val projectID = "2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        val slot = captureSlot()
        every { usecase.getProject(projectID) } throws ProjectNotFoundException
        //When
        uiController.execute()
        //Then
        assertThat(slot.captured == "Project not found. It may have been deleted or doesn't exist.")
    }

    @Test
    fun `should show projects is empty when getProjectUseCase throws EmptyDataException`() {
        //Given
        val projectID = "2b19ee75-2b4c-430f-bad8-dfa6b14709d9"
        val slot = captureSlot()
        every { usecase.getProject(projectID) } throws EmptyDataException
        //When
        uiController.execute()
        //Then
        assertThat(slot.captured == "Projects is empty")
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
        //Given
        val slot = captureSlot()
        every { usecase.getProject(invalidID) } throws InvalidProjectIDException
        //When
        uiController.execute()
        // Then
        assertThat(slot.captured == "The project ID is invalid. Please check and try again.")


    }

}