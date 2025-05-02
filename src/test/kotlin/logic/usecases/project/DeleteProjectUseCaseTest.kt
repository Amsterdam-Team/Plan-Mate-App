package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import data.repository.project.ProjectRepositoryImpl
import io.mockk.*
import logic.entities.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class DeleteProjectUseCaseTest {
    lateinit var repository: ProjectRepositoryImpl
    lateinit var usecase: DeleteProjectUseCase
    lateinit var dummyProjectId: UUID
    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = DeleteProjectUseCase(repository)
        dummyProjectId = UUID.randomUUID()
    }

    @Test
    fun `should return true when deleting project successfully `() {
        // when
        val result = usecase.deleteProject(dummyProjectId.toString())

        // then
        assertThat(result).isTrue()
    }


    @Test
    fun `should call delete project repo function when try to delete project`() {

        verify(exactly = 1) { repository.deleteProject(dummyProjectId) }

    }


}