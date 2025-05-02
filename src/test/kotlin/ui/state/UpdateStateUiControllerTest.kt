package ui.state

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.usecases.state.UpdateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import ui.console.ConsoleIO
import ui.controllers.UpdateStateUiController

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

    private fun captureSlot(): CapturingSlot<String> {
        val slot = slot<String>()
        every { consoleIO.println(capture(slot)) } just Runs
        return slot
    }
    @Test
    fun `should execute edit state successfully when all inputs are valid`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = "Completed"
        val slot = captureSlot()
        every { consoleIO.println(capture(slot)) } just Runs
        every { usecase.updateState(projectID, oldState, newState) } just runs
        //When
        uiController.execute()
        //Then
        assertThat(slot.equals("State Updated Successfully"))

    }


    @Test
    fun `should show error message when updateStateUseCase throws InvalidStateName`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = " "
        val newState = "Done"
        val slot = captureSlot()
        every { consoleIO.println(capture(slot)) } just Runs
        every { usecase.updateState(projectID, oldState, newState) }
        //When
        uiController.execute()
        //Then
       assertThat(slot.equals("The state name is not valid. Please enter a valid name."))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
        " ",
        "old",
        "db373589-b656-4e68 a7c0-2ccc705ca169",
        " db373589-b656#4e68@a7c0-2ccc705ca169"
        ]
    )
    fun `should show invalid Id format message when updateStateUseCase throws InvalidProjectIDException`(invalidID: String) {
        //Given
        val oldState = "Done"
        val newState = "Finished"
        val slot = captureSlot()

        every { consoleIO.println(capture(slot)) } just Runs
        every { usecase.updateState(invalidID, oldState, newState) }
        //When
        uiController.execute()
        //Then
        assertThat(slot.equals("The project ID is invalid. Please check and try again."))

    }

    @Test
    fun `should show no change message when updateStateUseCase throws SameStateNameException`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = "Done"
        val slot = captureSlot()
        every { consoleIO.println(capture(slot)) } just Runs
        every { usecase.updateState(projectID, oldState, newState) }
        //When
        uiController.execute()
        //Then
        assertThat(slot.equals("Current state and new state are identical. No changes applied."))
    }


}