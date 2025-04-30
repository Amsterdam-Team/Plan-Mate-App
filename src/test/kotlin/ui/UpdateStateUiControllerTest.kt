package ui

import console.ConsoleIO
import io.mockk.*
import logic.usecases.state.UpdateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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
        every { usecase.editState(UUID.fromString(projectID),oldState,newState) } just runs
        //When
        uiController.execute(projectID,oldState,newState)
        //Then
        verify { consoleIO.println("State Updated Successfully") }
    }

    @Test
    fun `should show error message when new state is blank`() {
        //Given
        val newState = " "
        every { consoleIO.println(any()) } just Runs

        //When
        uiController.isValidStateName(newState)
        //Then
        verify { consoleIO.println("new State name is blank") }
    }
    @Test
    fun `should show error message when old state is blank`() {
        //Given
        val oldState = " "
        every { consoleIO.println(any()) } just Runs

        //When
        uiController.isValidStateName(oldState)
        //Then
        verify { consoleIO.println("old state name is blank") }
    }

    @ParameterizedTest
    @CsvSource(
        " ",
        "old",
        "db373589-b656-4e68 a7c0-2ccc705ca169",
       " db373589-b656#4e68@a7c0-2ccc705ca169"
    )
    fun `should show invalid ID error when project ID is not a valid UUID`(invalidID:String) {
        //Given
        every { consoleIO.println(any()) } just Runs
        //When
        uiController.isValidProjectID(invalidID)
        //Then
        verify { consoleIO.println("Invalid Project ID") }

    }

    @Test
    fun `should show no change message when old state is equal to new state`() {
        //Given
        val oldState = "Done"
        val newState = "Done"
        every { consoleIO.println(any()) } just Runs
        //When
        uiController.isSameState(oldState,newState)
        //Then
        verify { consoleIO.println("Old state and new state are identical. No changes applied.") }
    }



}