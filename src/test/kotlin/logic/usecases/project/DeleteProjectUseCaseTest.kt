package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import data.repository.project.ProjectRepositoryImpl
import io.mockk.*
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.AuthorizationException.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class DeleteProjectUseCaseTest {
    lateinit var repository: ProjectRepositoryImpl
    lateinit var usecase: DeleteProjectUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = DeleteProjectUseCase(repository)
    }

    @Test
    fun `should return true when deleting project successfully `() {
        // when
        val result = usecase.deleteProject("er0uj34")

        // then
        assertThat(result).isTrue()
    }


    @Test
    fun `should throw not found exception when deleting project not exist`() {

        // when & then
        assertThrows<ProjectNotFoundException> {
            usecase.deleteProject("")
        }

    }

    @Test
    fun `should throw Admin Privileges Required Exception when deleting by user not admin`() {

        // when & then
        assertThrows<AdminPrivilegesRequiredException> {
            usecase.deleteProject("")
        }

    }


}