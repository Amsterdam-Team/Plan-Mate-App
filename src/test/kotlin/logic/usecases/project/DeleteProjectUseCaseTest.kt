package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import logic.usecases.validation.ValidateInputUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.UUID

class DeleteProjectUseCaseTest {
    lateinit var repository: ProjectRepository
    lateinit var useCase: DeleteProjectUseCase
    lateinit var dummyProjectId: UUID
    lateinit var currentUser: User

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        currentUser = User(id = UUID.randomUUID(), username = "omer faris", isAdmin = true, password = "7584848")
        useCase = DeleteProjectUseCase(repository, currentUser, ValidateInputUseCase())
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
    fun `should throw admin Privileges required when non admin user try to e a project`() {
        // given
        val nonAdminUser = User(id = UUID.randomUUID(), username = "ahmed faris", password = "858585", isAdmin = false)
        useCase = DeleteProjectUseCase(repository,nonAdminUser, ValidateInputUseCase())
        currentUser = nonAdminUser
        // when

        assertThrows<AdminPrivilegesRequiredException> {
            useCase.deleteProject(dummyProjectId.toString())
        }
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
        verify(exactly = 1) { repository.deleteProject(dummyProjectId) }

    }

    @ParameterizedTest
    @CsvSource(
        "oijwerfj",
        "8585858585",
        "#@#@!@"
    )
    fun `should throw invalid id project exception when the user entered not valid uuid`(projectId:String) {
        // when & then
        assertThrows<InvalidProjectIDException> {
            useCase.deleteProject(projectId)
        }
    }


}