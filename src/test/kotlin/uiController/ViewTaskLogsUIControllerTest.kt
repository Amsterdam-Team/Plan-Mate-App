package uiController

import io.mockk.mockk
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.usecases.ViewTaskLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import ui.ViewTaskLogsUIController
import java.io.ByteArrayInputStream
import java.util.UUID
import kotlin.test.assertEquals

class ViewTaskLogsUIControllerTest {
    private lateinit var useCase : ViewTaskLogsUseCase
    private lateinit var uiController : ViewTaskLogsUIController

    @BeforeEach
    fun setup(){
        useCase = mockk()
        uiController = ViewTaskLogsUIController(useCase)
    }

    @Test
    fun `should return UUID object when input string is in correct UUID format`(){
        val id = "123e4567-e89b-12d3-a456-426614174000"
        System.setIn(ByteArrayInputStream(id.toByteArray()))

        val idInUUIDFormat = uiController.validateTaskId(id)
        assertEquals(UUID.fromString(id),idInUUIDFormat)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "123e4567-e89b-12d3-a456" ,               //incomplete id
            " ",
            "123e4567-e89b-12d3-a456-426614174%@0" ,  //has special character
            "kahdjshuffl123"                          //invalid format
        ]
    )
    fun `should throw InvalidTaskIdException when input string is in invalid UUID format`(id:String){
        System.setIn(ByteArrayInputStream(id.toByteArray()))

        assertThrows<InvalidTaskIDException> {
            uiController.validateTaskId(id)
        }
    }
}