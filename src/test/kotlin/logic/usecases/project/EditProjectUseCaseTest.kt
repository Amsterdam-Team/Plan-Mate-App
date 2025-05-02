package logic.usecases.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import helpers.EditProjectFactory
import logic.logging.LogEvent
import logic.logging.toLogItem
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EditProjectUseCaseTest {

    private lateinit var projectRepository: ProjectRepository
    private lateinit var editProjectUseCase: EditProjectUseCase
    private lateinit var logRepository: LogRepository


    @BeforeEach
    fun setup() {
        projectRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        editProjectUseCase = EditProjectUseCase(projectRepository, logRepository)
    }

    @Test
    fun `should update project name when user is admin and project exists`() {
        // Given
        val newName = "Updated Project Name"
        every { projectRepository.getProject(EditProjectFactory.validProjectId) } returns EditProjectFactory.validProject()

        // When
        editProjectUseCase.editProjectName(EditProjectFactory.adminUser(), EditProjectFactory.validProjectId, newName)

        // Then
        verify { projectRepository.updateProjectNameById(EditProjectFactory.validProjectId, newName) }
    }

    @Test
    fun `should throw AdminPrivilegesRequiredException when user is not admin`() {
        // Given
        val newName = "Updated Project Name"

        // When & Then
        assertThrows<PlanMateException.AuthorizationException.AdminPrivilegesRequiredException>
        { editProjectUseCase.editProjectName(EditProjectFactory.mateUser, EditProjectFactory.validProjectId, newName) }
    }

    @Test
    fun `should throw ProjectNotFoundException when project with id does not exist`() {
        // Given
        val newName = "Updated Project Name"

        every { projectRepository.getProjects() } returns EditProjectFactory.projectList()

        // When & Then
        assertThrows<PlanMateException.NotFoundException.ProjectNotFoundException> {
            editProjectUseCase.editProjectName(
                EditProjectFactory.adminUser(),
                EditProjectFactory.validProjectId,
                newName
            )
        }
    }

    @Test
    fun `should throw InvalidProjectNameException when new name is blank`() {
        // Given
        val newName = "   "
        every { projectRepository.getProject(EditProjectFactory.validProjectId) } returns EditProjectFactory.validProject()

        // When & Then
        assertThrows<PlanMateException.ValidationException.InvalidProjectNameException> {
            editProjectUseCase.editProjectName(
                EditProjectFactory.adminUser(),
                EditProjectFactory.validProjectId,
                newName
            )
        }
    }

    @Test
    fun `should add log entry when project name is updated`() {
        // Given
        every { projectRepository.getProject(EditProjectFactory.validProjectId) } returns EditProjectFactory.validProject()

        val newName = "Updated Project Name"

        // When
        editProjectUseCase.editProjectName(EditProjectFactory.adminUser(), EditProjectFactory.validProjectId, newName)

        val event = LogEvent.ProjectNameUpdated(
            projectId = EditProjectFactory.validProjectId,
            oldName = EditProjectFactory.validProject().name,
            newName = newName
        )
        val logItem = event.toLogItem(EditProjectFactory.adminUser().id)

        // Then
        verify {
            logRepository.addLog(logItem)
        }
    }
}




