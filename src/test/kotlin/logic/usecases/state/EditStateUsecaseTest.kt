package logic.usecases.state

import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test

class EditStateUsecaseTest{
 private lateinit var repository: ProjectRepository
 private lateinit var useCase: EditStateUsecase

 @BeforeEach
 fun setup() {
  repository = mockk(relaxed = true)
  useCase = EditStateUsecase(repository)
 }

 @Test
 fun `should edit state when state is valid`() {
  // Given
  val projectID = UUID.fromString("")
  val oldState = "In Progress"
  val newState = "In Review"


  // When & Then
  verify (exactly = 1){ useCase.editState(projectID, newState,oldState) }

 }

 @Test
 fun `should throw InvalidStateNameException when new state name is blank`() {
  // Given
  val projectID = UUID.fromString("")
  val oldState = "In Progress"
  val newState = " "

  // When & Then
  assertThrows<PlanMateException.ValidationException.InvalidStateNameException> {
   useCase.editState(projectID, oldState, newState)
  }
 }

 @Test
 fun `should throw InvalidStateNameException when old state name is blank`() {
  // Given
  val projectID = UUID.fromString("")
  val oldState = " "
  val newState = "In Progress"

  // When & Then
  assertThrows<PlanMateException.ValidationException.InvalidStateNameException> {
   useCase.editState(projectID,oldState,newState)
  }
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