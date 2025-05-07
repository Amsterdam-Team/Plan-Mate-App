package ui.project


  import console.ConsoleIO
  import io.mockk.*
  import logic.usecases.project.EditProjectUseCase
  import org.junit.jupiter.api.BeforeEach
  import org.junit.jupiter.api.Test
  import java.io.ByteArrayOutputStream
  import java.io.PrintStream
  import java.util.*
  import kotlin.test.assertTrue

  class EditProjectUIControllerTest {

   private lateinit var useCase: EditProjectUseCase
   private lateinit var controller: EditProjectUIController
   private lateinit var consoleIo: ConsoleIO
   private lateinit var outContent: ByteArrayOutputStream

   private val validProjectId = UUID.randomUUID()
   private val newProjectName = "New Project Name"

   @BeforeEach
   fun setUp() {
    useCase = mockk()
    consoleIo = mockk()
    controller = EditProjectUIController(useCase, consoleIo)
    outContent = ByteArrayOutputStream()
    System.setOut(PrintStream(outContent))

    every { consoleIo.println(any()) } just Runs
   }

   @Test
   fun `should call use case once when input is valid`() {
    every { useCase.editProjectName(any(), any(), any()) } returns true
    every { consoleIo.readFromUser() } returnsMany listOf(validProjectId.toString(), newProjectName)

    controller.execute()

    verify(exactly = 1) {
     useCase.editProjectName(any(), validProjectId, newProjectName)
    }
   }

   @Test
   fun `should show success message when project name is updated`() {
    every { useCase.editProjectName(any(), any(), any()) } returns true
    every { consoleIo.readFromUser() } returnsMany listOf(validProjectId.toString(), newProjectName)

    controller.execute()

    assertTrue(outContent.toString().contains("✅ Project name updated successfully!"))
   }

   @Test
   fun `should show error message when invalid UUID is entered`() {
    every { consoleIo.readFromUser() } returnsMany listOf("invalid-uuid", newProjectName)

    controller.execute()

    assertTrue(outContent.toString().contains("❌ Please Inter All Inputs Correctly"))
   }

   @Test
   fun `should show error message when use case throws exception`() {
    every { consoleIo.readFromUser() } returnsMany listOf(validProjectId.toString(), newProjectName)
    every { useCase.editProjectName(any(), any(), any()) } throws RuntimeException("Something went wrong")
    every { consoleIo.readFromUser() } returns "cancel"

    controller.execute()

    assertTrue(outContent.toString().contains("❌ Please Inter All Inputs Correctly"))
   }


  }

