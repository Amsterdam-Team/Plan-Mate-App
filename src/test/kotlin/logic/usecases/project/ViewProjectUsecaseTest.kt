package logic.usecases.project

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.exception.PlanMateException.ValidationException.EmptyDataException
import logic.repository.ProjectRepository
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class ViewProjectUsecaseTest() {

    private lateinit var repository: ProjectRepository
    private lateinit var usecase: ViewProjectUsecase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = ViewProjectUsecase(repository)
    }

    @Test
    fun `should return correct project when project id exists in projects`() {
        val projectID = UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d9")
        val projects = listOf(
            createProject(
                projectID,
                "PlanMate", listOf(), listOf()
            )
        )
        //Given
        every { repository.getProjects() } returns projects
        //When
        val result = usecase.viewProject(projectID)
        //Then
        assertThat(result).isIn(projects)
    }

    @Test
    fun `should throw InvalidProjectIDException when projectID does not exist in Projects`() {
        val projectID = UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d9")
        val projects = listOf(
            createProject(
                projectID,
                "PlanMate", listOf(), listOf()
            )
        )
        //Given
        every { repository.getProjects() } returns projects
        //When&Then
        assertThrows<ProjectNotFoundException> {
            usecase.viewProject(projectID)
        }
    }

    @Test
    fun `should throw EmptyDataException when projects is empty`() {
        val projectID = UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d9")
        //Given
        every { repository.getProjects() } returns emptyList()
        //When&Then
        assertThrows<EmptyDataException> {
            usecase.viewProject(projectID)
        }
    }
}