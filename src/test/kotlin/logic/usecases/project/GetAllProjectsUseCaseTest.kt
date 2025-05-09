package logic.usecases.project

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.ProjectRepository
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetAllProjectsUseCaseTest {
    private lateinit var repository: ProjectRepository
    private lateinit var useCase: GetAllProjectsUseCase
    private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
    @BeforeEach
    fun setup(){
        repository = mockk(relaxed = true)
        getAllTasksByProjectIdUseCase = mockk(relaxed = true)
        useCase = GetAllProjectsUseCase(repository, getAllTasksByProjectIdUseCase)
    }

    @Test
    fun `should call repository when call the use case`()= runTest{
        //When
        useCase.execute()
        //Then
        coVerify (exactly = 1) { repository.getProjects() }
    }
}