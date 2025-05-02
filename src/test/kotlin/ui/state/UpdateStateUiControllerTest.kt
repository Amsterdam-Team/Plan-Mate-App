package ui.state

import console.ConsoleIO
import io.mockk.*
import logic.usecases.state.UpdateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ui.UpdateStateUiController
import java.util.UUID

class UpdateStateUiControllerTest {

    private lateinit var usecase: UpdateStateUseCase
    private lateinit var uiController: UpdateStateUiController
    private lateinit var consoleIO: ConsoleIO

    @BeforeEach
    fun setup() {
        consoleIO = mockk(relaxed = true)
        usecase = mockk(relaxed = true)
        uiController = UpdateStateUiController(usecase, consoleIO)
    }

    @Test
    fun `should execute edit state successfully when all inputs are valid`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = "Completed"
        every { consoleIO.println(any()) } just Runs
        every { usecase.updateState(UUID.fromString(projectID), oldState, newState) } just runs
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("State Updated Successfully") }
    }


    @Test
    fun `should show error message when updateStateUseCase throws InvalidStateName`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = " "
        val newState = "Done"
        every { consoleIO.println(any()) } just Runs
        every { usecase.updateState(UUID.fromString(projectID), oldState, newState) }
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("State name is blank") }
    }

    @ParameterizedTest
    @CsvSource(
        " ",
        "old",
        "db373589-b656-4e68 a7c0-2ccc705ca169",
        " db373589-b656#4e68@a7c0-2ccc705ca169"
    )
    fun `should show invalid Id format message when updateStateUseCase throws InvalidProjectIDException`(invalidID: String) {
        //Given
        val oldState = "Done"
        val newState = "Done"
        every { consoleIO.println(any()) } just Runs
        every { usecase.updateState(UUID.fromString(invalidID), oldState, newState) }
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("Invalid Project ID format") }

    }

    @Test
    fun `should show no change message when updateStateUseCase throws SameStateNameException`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = "Done"
        every { consoleIO.println(any()) } just Runs
        every { usecase.updateState(UUID.fromString(projectID), oldState, newState) }
        //When
        uiController.execute()
        //Then
        verify { consoleIO.println("Old state and new state are identical. No changes applied.") }
    }


}