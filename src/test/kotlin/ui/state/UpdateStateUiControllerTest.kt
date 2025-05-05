package ui.state

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidStateNameException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.ValidationException.SameStateNameException
import logic.usecases.state.UpdateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.console.ConsoleIO
import ui.controllers.UpdateStateUiController
import ui.utils.getErrorMessageByException

class UpdateStateUiControllerTest {

    private lateinit var usecase: UpdateStateUseCase
    private lateinit var uiController: UpdateStateUiController
    private lateinit var consoleIO: ConsoleIO
    private lateinit var slot: MutableList<String>

    @BeforeEach
    fun setup() {
        mockkStatic("ui.utils.UIHelperFunctionsKt")
        consoleIO = mockk(relaxed = true)
        usecase = mockk(relaxed = true)
        slot = mutableListOf()
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
        every { consoleIO.println(capture(slot)) } just Runs
        every { usecase.updateState(projectID, oldState, newState) } returns true
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
        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returnsMany listOf(projectID, oldState, newState)
        every { usecase.updateState(projectID, oldState, newState) } throws InvalidStateNameException
        //When
        uiController.execute()
        //Then
        assertThat(slot.containsAll(listOf("Enter New State :)","Enter Current State :)","Enter project ID :)")))
        verify (exactly = 3){ consoleIO.readFromUser() }
        verify { getErrorMessageByException(InvalidStateNameException) }
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
        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returnsMany listOf(invalidID, oldState, newState)
        every { usecase.updateState(invalidID, oldState, newState) } throws InvalidProjectIDException
        //When
        uiController.execute()
        //Then
        assertThat(slot.containsAll(listOf("Enter New State :)","Enter Current State :)","Enter project ID :)")))
        verify (exactly = 3){ consoleIO.readFromUser() }
        verify { getErrorMessageByException(InvalidProjectIDException) }

    }

    @Test
    fun `should show no change message when updateStateUseCase throws SameStateNameException`() {
        //Given
        val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"
        val oldState = "Done"
        val newState = "Done"
        every { consoleIO.println(capture(slot)) } just Runs
        every { consoleIO.readFromUser() } returnsMany listOf(projectID, oldState, newState)
        every { usecase.updateState(projectID, oldState, newState) } throws SameStateNameException
        //When
        uiController.execute()
        //Then
        assertThat(slot.containsAll(listOf("Enter New State :)","Enter Current State :)","Enter project ID :)")))
        verify (exactly = 3){ consoleIO.readFromUser() }
        verify { getErrorMessageByException(SameStateNameException) }    }


}