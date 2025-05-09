package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import helper.GetProjectStatesUseCaseTestFactory.dummyProject
import helper.GetProjectStatesUseCaseTestFactory.EXISTING_PROJECT_ID
import helper.GetProjectStatesUseCaseTestFactory.INVALID_PROJECT_ID
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
    fun `should return the correct states when the project exists`() = runTest {
        //Given
        coEvery { repository.getProject(any()) } returns dummyProject

        //When
        val states = useCase.execute(EXISTING_PROJECT_ID)

        //Then
        assertThat(states).containsAtLeastElementsIn(dummyProject.states)
    }

    @Test
    fun `should throw InvalidProjectIDException when the project ID is invalid`() = runTest{
        //Given & When & Then
        assertThrows<InvalidProjectIDException> { useCase.execute(INVALID_PROJECT_ID) }
    }

    @Test
    fun `should throw ProjectNotFoundException when the project is not exists`() = runTest{
        //Given
        coEvery { repository.getProject(any()) } throws  ProjectNotFoundException

        //When & Then
        assertThrows<InvalidProjectIDException> { useCase.execute(INVALID_PROJECT_ID) }
    }
}