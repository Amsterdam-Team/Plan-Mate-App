package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.*
import logic.repository.ProjectRepository
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.EXPECTED_PROJECT_STATES
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.existingProjectID
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.notExistingProjectID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetProjectStatesUseCaseTest{

    private lateinit var repository: ProjectRepository
    private lateinit var useCase: GetProjectStatesUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetProjectStatesUseCase(repository)
    }

    @Test
    fun `should return the correct states when the project exists`() {
        //Given & When
        val states = useCase.getProjectStatesByProjectID(existingProjectID)

        //Then
        assertThat(states).containsExactly(EXPECTED_PROJECT_STATES)
    }

    @Test
    fun `should return sorted states by creation date when the project exists`() {
        //Given & When
        val states = useCase.getProjectStatesByProjectID(existingProjectID)

        //Then
        assertThat(states).containsExactly(EXPECTED_PROJECT_STATES).inOrder()
    }

    @Test
    fun `should throw ProjectNotFoundException when the task is not exists`() {
        //Given & When & Then
        assertThrows<ProjectNotFoundException> { useCase.getProjectStatesByProjectID(notExistingProjectID) }
    }
}