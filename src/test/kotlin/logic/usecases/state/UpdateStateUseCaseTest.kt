package logic.usecases.state

import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test

class UpdateStateUseCaseTest{
 private lateinit var repository: ProjectRepository
 private lateinit var useCase: UpdateStateUseCase

 @BeforeEach
 fun setup() {
  repository = mockk(relaxed = true)
  useCase = UpdateStateUseCase(repository)
 }

 @Test
 fun `should edit state when state is valid`() {
  // Given
  val projectID = UUID.fromString("db373589-b656-4e68-a7c0-2ccc705ca169")
  val oldState = "In Progress"
  val newState = "In Review"

  // When & Then
  verify (exactly = 1){ useCase.editState(projectID, newState,oldState) }

 }


 @Test
 fun `should throw ProjectNotFoundException when project does not exist`() {
  // Given
  val projectId = UUID.randomUUID()
  val oldState = "Done"
  val newState = "Finished"

  // When & Then
  assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
   useCase.editState(projectId, oldState,newState)
  }
 }

}