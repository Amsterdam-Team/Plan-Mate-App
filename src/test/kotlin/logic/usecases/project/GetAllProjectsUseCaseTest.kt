package logic.usecases.project

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetAllProjectsUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: GetAllProjectsUseCase
    @BeforeEach
    fun setup(){
        repository = mockk(relaxed = true)
        useCase = GetAllProjectsUseCase(repository)
    }

    @Test
    fun `should call repository when call the use case`()= runTest{
        //When
        useCase.execute()
        //Then
        coVerify (exactly = 1) { repository.getProjects() }
    }
}