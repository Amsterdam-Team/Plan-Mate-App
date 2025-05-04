package logic.usecases.project

import io.mockk.mockk
import io.mockk.verify
import logic.repository.ProjectRepository
import org.junit.jupiter.api.Assertions.*
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
    fun `should call repository when call the use case`(){
        //When
        useCase.execute()
        //Then
        verify (exactly = 1) { repository.getProjects() }
    }
}