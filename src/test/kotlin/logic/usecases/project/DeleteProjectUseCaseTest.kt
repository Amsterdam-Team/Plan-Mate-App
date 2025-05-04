package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import data.repository.project.ProjectRepositoryImpl
import io.mockk.*
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.UUID

class DeleteProjectUseCaseTest {
    lateinit var repository: ProjectRepositoryImpl
    lateinit var useCase: DeleteProjectUseCase
    lateinit var dummyProjectId: UUID

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteProjectUseCase(repository)
        dummyProjectId = UUID.randomUUID()
    }

    @Test
    fun `should return true when deleting project successfully `() {
        // when
        every { repository.deleteProject(dummyProjectId) } returns true

        val result = useCase.deleteProject(dummyProjectId.toString())

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when deleting project not successfully completed `() {
        // when
        every { repository.deleteProject(dummyProjectId) } returns false

        val result = useCase.deleteProject(dummyProjectId.toString())

        // then
        assertThat(result).isFalse()
    }



    @Test
    fun `should call delete project repo function when try to delete project`() {
        useCase.deleteProject(dummyProjectId.toString())
        verify(exactly = 1) { repository.deleteProject(any()) }

    }

    @Test
    fun `should throw empty data exception when the id entered by user is empty`() {
        // given
        val id = ""
        // when

        // then

    }
    @ParameterizedTest
    @CsvSource(
        "oijwerfj",
        "8585858585",
        "#@#@!@"
    )
    fun `should throw invalid id project exception when the user entered not valid uuid`(projectId:String) {
        every {repository.deleteProject(any())} returns true
        // when
        // then
        assertThrows<InvalidProjectIDException> {
            useCase.deleteProject(projectId)
        }
    }


}