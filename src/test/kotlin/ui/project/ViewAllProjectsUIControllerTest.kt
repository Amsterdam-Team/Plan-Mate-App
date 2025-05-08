package ui.project

import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.usecases.project.GetAllProjectsUseCase
import org.junit.jupiter.api.BeforeEach
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
    fun `should call useCase when execute the controller`() = runTest{
        //When
        controller.execute()
        //Then
        coVerify(exactly = 1) { useCase.execute() }
    }

}