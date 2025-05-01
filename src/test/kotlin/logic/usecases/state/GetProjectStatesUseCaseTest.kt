package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.EXPECTED_PROJECT_STATES
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.dummyProject
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.existingProjectID
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.invalidProjectID
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.notExistingProjectID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetProjectStatesUseCaseTest{

    private lateinit var repository: ProjectRepository
    private lateinit var useCase: GetProjectStatesUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetProjectStatesUseCase(repository)
    }

    @Test
    fun `should return the correct states when the project exists`() {
        //Given
        every { repository.getProject(existingProjectID) } returns dummyProject

        //When
        val states = useCase.execute(existingProjectID)

        //Then
        assertThat(states).containsAtLeastElementsIn(EXPECTED_PROJECT_STATES)
    }

    @Test
    fun `should return sorted states by creation date when the project exists`() {
        //Given
        every { repository.getProject(existingProjectID) } returns dummyProject

        //When
        val states = useCase.execute(existingProjectID)

        //Then
        assertThat(states).containsAtLeastElementsIn(EXPECTED_PROJECT_STATES).inOrder()
    }

    @Test
    fun `should throw InvalidProjectIDException when the project ID is invalid`() {
        //Given & When & Then
        assertThrows<InvalidTaskIDException> { useCase.execute(invalidProjectID) }
    }

    @Test
    fun `should throw ProjectNotFoundException when the project is not exists`() {
        //Given
        every { repository.getProject(notExistingProjectID) } throws  ProjectNotFoundException

        //When & Then
        assertThrows<ProjectNotFoundException> { useCase.execute(notExistingProjectID) }
    }
}