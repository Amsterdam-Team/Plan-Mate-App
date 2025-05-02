package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.ValidationException.InvalidTaskIDException
import logic.repository.ProjectRepository
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.dummyProject
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.EXISTING_PROJECT_ID
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.INVALID_PROJECT_ID
import logic.usecases.state.testFactory.GetProjectStatesUseCaseTestFactory.NOT_EXISTING_PROJECT_ID
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
        every { repository.getProject(any()) } returns dummyProject

        //When
        val states = useCase.execute(EXISTING_PROJECT_ID)

        //Then
        assertThat(states).containsAtLeastElementsIn(dummyProject.states)
    }

    @Test
    fun `should throw InvalidProjectIDException when the project ID is invalid`() {
        //Given & When & Then
        assertThrows<InvalidTaskIDException> { useCase.execute(INVALID_PROJECT_ID) }
    }

    @Test
    fun `should throw ProjectNotFoundException when the project is not exists`() {
        //Given
        every { repository.getProject(any()) } throws  ProjectNotFoundException

        //When & Then
        assertThrows<ProjectNotFoundException> { useCase.execute(NOT_EXISTING_PROJECT_ID) }
    }
}