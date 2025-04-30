package ui.project

import com.google.common.base.Verify.verify
import io.mockk.*
import logic.entities.Project
import logic.usecases.project.ViewProjectUseCase
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ui.console.ConsoleIO
import java.util.*
import kotlin.test.Test

class ViewProjectUIControllerTest{
  private lateinit var usecase: ViewProjectUseCase
  private lateinit var uiController: ViewProjectUIController
  private lateinit var consoleIO: ConsoleIO

  @BeforeEach
  fun setup() {
   consoleIO = mockk(relaxed = true)
   usecase = mockk(relaxed = true)
   uiController = ViewProjectUIController(usecase, consoleIO)
  }

  @Test
  fun `should execute view project successfully when project id is valid`() {
   //Given
   val projectID = "db373589-b656-4e68-a7c0-2ccc705ca169"

   val project = createProject(UUID.fromString(projectID),"Plan Mate App", listOf(), listOf())
   every { consoleIO.println(any()) } just Runs
   every { usecase.viewProject(UUID.fromString(projectID)) } returns  project
   //When
   uiController.execute(projectID)
   //Then
   verify { consoleIO.println(project.toString()) }
  }



  @ParameterizedTest
  @CsvSource(
   " ",
   "id",
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

 }