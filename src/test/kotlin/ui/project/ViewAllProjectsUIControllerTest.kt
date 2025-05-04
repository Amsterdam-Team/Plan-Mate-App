package ui.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecases.project.GetAllProjectsUseCase
import logic.usecases.project.helper.ViewProjectHistoryTestFactory.ALL_PROJECTS
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import utils.printSwimlanesView
import kotlin.test.Test

class ViewAllProjectsUIControllerTest {
    private lateinit var useCase: GetAllProjectsUseCase
    private lateinit var controller: ViewAllProjectsUIController

    @BeforeEach
    fun setup(){
        useCase = mockk(relaxed = true)
        controller = ViewAllProjectsUIController(useCase)
    }

    @Test
    fun `should call useCase when execute the controller`(){
        //When
        controller.execute()
        //Then
        verify(exactly = 1) { useCase.execute() }
    }

}