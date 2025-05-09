package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.InvalidProjectIDException
import logic.repository.ProjectRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
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
    lateinit var validateInputUseCase: ValidateInputUseCase
    lateinit var stateManager: StateManager
    lateinit var loggerUseCase: LoggerUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        stateManager = mockk(relaxed = true)
        validateInputUseCase = mockk(relaxed = true)
        loggerUseCase = mockk(relaxed = true)
        useCase =
            DeleteProjectUseCase(repository, stateManager, validateInputUseCase, loggerUseCase)
        dummyProjectId = UUID.randomUUID()

        coEvery {
            stateManager.getLoggedInUser()
        } returns User(
            id = UUID.randomUUID(),
            username = "admin",
            password = "pass",
            isAdmin = true
        )
    }

    @Test
    fun `should return true when deleting project successfully `() = runTest {
        // when
        coEvery { validateInputUseCase.isValidUUID(dummyProjectId.toString()) } returns true
        coEvery { repository.deleteProject(dummyProjectId) } returns true

        val result = useCase.deleteProject(dummyProjectId.toString())

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `should throw admin Privileges required when non admin user try to e a project`() =
        runTest {
            // given
            coEvery { validateInputUseCase.isValidUUID(dummyProjectId.toString()) } returns true
            coEvery { stateManager.getLoggedInUser() } returns
                    User(
                        id = UUID.randomUUID(),
                        username = "ahmed faris",
                        password = "858585",
                        isAdmin = false
                    )
            // when

            assertThrows<AdminPrivilegesRequiredException> {
                useCase.deleteProject(dummyProjectId.toString())
            }
        }

    @Test
    fun `should return false when deleting project not successfully completed `() = runTest {
        // when
        coEvery { validateInputUseCase.isValidUUID(dummyProjectId.toString()) } returns true
        coEvery { repository.deleteProject(dummyProjectId) } returns false

        val result = useCase.deleteProject(dummyProjectId.toString())

        // then
        assertThat(result).isFalse()
    }


    @Test
    fun `should call delete project repo function when try to delete project`() = runTest {
        coEvery { validateInputUseCase.isValidUUID(dummyProjectId.toString()) } returns true
        useCase.deleteProject(dummyProjectId.toString())
        coVerify(exactly = 1) { repository.deleteProject(dummyProjectId) }

    }

    @ParameterizedTest
    @CsvSource(
        "oijwerfj",
        "8585858585",
        "#@#@!@"
    )
    fun `should throw invalid id project exception when the user entered not valid uuid`(projectId: String) =
        runTest {
            coEvery { validateInputUseCase.isValidUUID(projectId) } returns false

            // when & then
            assertThrows<InvalidProjectIDException> {
                useCase.deleteProject(projectId)
            }
        }


}