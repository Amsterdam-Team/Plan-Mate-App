package ui.project

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.InvalidUUIDFormatException
import logic.usecases.project.GetProjectDetailsUseCase
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.console.ConsoleIO
import ui.controller.project.GetProjectUIController
import ui.utils.getErrorMessageByException
import ui.utils.printSwimlanesView
import java.util.*
import kotlin.test.Test

class GetProjectUIControllerTest {
    private lateinit var usecase: GetProjectDetailsUseCase
    private lateinit var uiController: GetProjectUIController
    private lateinit var consoleIO: ConsoleIO
    private lateinit var slot: CapturingSlot<String>
    private val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"

    @BeforeEach
    fun setup() {
        mockkStatic("utils.SwimLanesUIKt")
        mockkStatic("ui.utils.UIHelperFunctionsKt")
        consoleIO = mockk(relaxed = true)
        usecase = mockk(relaxed = true)
        uiController = GetProjectUIController(usecase, consoleIO)
        slot = CapturingSlot()
        every { consoleIO.println(capture(slot)) } just Runs

    }


    @Test
    fun `should execute view project successfully when project id is valid`() {
        //Given

        val project = createProject(UUID.fromString(projectID), "", listOf(), listOf())
        every { usecase(any()) } returns project
        every { consoleIO.readFromUser() } returns projectID
        every { printSwimlanesView(project) } just Runs

        //When
        uiController.execute()

        //Then
        assertThat(slot.captured == "Enter Project ID :)")
        verify(exactly = 1) { consoleIO.readFromUser() }
        verify { printSwimlanesView(project) }

    }

    @Test
    fun `should show project not exist when getProjectUseCase throws ProjectNotFoundException`() {
        //Given
        every { usecase(projectID) } throws ProjectNotFoundException
        every { consoleIO.readFromUser() } returns projectID
        every { getErrorMessageByException(ProjectNotFoundException) } returns "Project not found. It may have been deleted or doesn't exist."

        //When
        uiController.execute()

        //Then
        assertThat(slot.captured == "Enter Project ID:)")
        verify(exactly = 1) { consoleIO.readFromUser() }
        verify(exactly = 1) { getErrorMessageByException(ProjectNotFoundException) }

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
    fun `should show invalid uuid format when getProjectUseCase throws InvalidUUIDFormat`(invalidID: String) {
        //Given
        every { usecase(invalidID) } throws InvalidUUIDFormatException
        every { getErrorMessageByException(InvalidUUIDFormatException) } returns "Invalid UUID Format"
        every { consoleIO.readFromUser() } returns invalidID

        //When
        uiController.execute()

        //Then

        assertThat(slot.captured == "Enter Project ID :)")
        verify { consoleIO.readFromUser() }
        verify { getErrorMessageByException(InvalidUUIDFormatException) }

    }


}