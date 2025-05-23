package logic.usecases.state

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.IProjectRepository
import helper.ProjectFactory.dummyProject
import helper.ConstantsFactory.EXISTING_PROJECT_ID
import helper.ConstantsFactory.INVALID_PROJECT_ID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetProjectStatesUseCaseTest{

    private lateinit var repository: IProjectRepository
    private lateinit var useCase: GetProjectStatesUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetProjectStatesUseCase(repository)
    }

    @Test
    fun `should return the correct states when the project exists`() = runTest {
        //Given
        coEvery { repository.getProjectById(any()) } returns dummyProject

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
        coEvery { repository.getProjectById(any()) } throws  ProjectNotFoundException

        //When & Then
        assertThrows<InvalidProjectIDException> { useCase.execute(INVALID_PROJECT_ID) }
    }
}