package logic.usecases.state

import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.repository.ProjectRepository
import logic.usecases.project.helper.createProject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class EditStateUsecaseTest(){
    private lateinit var repository: ProjectRepository
    private lateinit var usecase: EditStateUsecase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        usecase = EditStateUsecase(repository)
    }

 /*   @Test
    fun `should update state project when user enter valid name`() {
        //Given
        every { repository.g() } returns projects
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
                "PlanMate", listOf(), listOf(), listOf()
            )
        )
        //Given
        every { repository.getProjects() } returns projects
        //When&Then
        org.junit.jupiter.api.assertThrows<ProjectNotFoundException> {
            usecase.viewProject(projectID)
        }
    }

    @Test
    fun `should throw EmptyDataException when projects is empty`() {
        val projectID = UUID.fromString("2b19ee75-2b4c-430f-bad8-dfa6b14709d9")
        //Given
        every { repository.getProjects() } returns emptyList()
        //When&Then
        org.junit.jupiter.api.assertThrows<EmptyDataException> {
            usecase.viewProject(projectID)
        }
    }*/
}