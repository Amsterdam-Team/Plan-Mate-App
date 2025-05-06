package logic.usecases.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.PlanMateException
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.usecases.ValidateInputUseCase
import logic.usecases.testFactory.EditProjectFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class EditProjectUseCaseTest{

   private lateinit var projectRepository: ProjectRepository
   private lateinit var validateInputUseCase: ValidateInputUseCase
   private lateinit var logRepository: LogRepository
   private lateinit var editProjectNameUseCase: EditProjectUseCase

   @BeforeEach
   fun setup() {
    projectRepository = mockk(relaxed = true)
    validateInputUseCase = mockk(relaxed = true)
    logRepository = mockk(relaxed = true)

    editProjectNameUseCase = EditProjectUseCase(
     projectRepository,
     validateInputUseCase,
     logRepository
    )
   }

    @Test
    fun `should update project name when user is admin, name valid and not taken`() {
        val newName = "Updated Project Name"
        every { validateInputUseCase.isValidName(newName) } returns true
        every { projectRepository.getProject(EditProjectFactory.validProjectId) } returns EditProjectFactory.validProject()
        every { projectRepository.getProjects() } returns listOf(EditProjectFactory.validProject())
        every { projectRepository.updateProjectNameById(EditProjectFactory.validProjectId, newName) } returns true

        val result = editProjectNameUseCase.editProjectName(EditProjectFactory.adminUser(), EditProjectFactory.validProjectId, newName)

        assertTrue(result)
        verify { projectRepository.updateProjectNameById(EditProjectFactory.validProjectId, newName) }
    }


    @Test
   fun `should throw AdminPrivilegesRequiredException when user is not admin`() {
    val newName = "Updated Project Name"

    assertThrows<PlanMateException.AuthorizationException.AdminPrivilegesRequiredException> {
     editProjectNameUseCase.editProjectName(EditProjectFactory.mateUser, EditProjectFactory.validProjectId, newName)
    }
   }


   @Test
   fun `should throw InvalidProjectNameException when new name is invalid`() {
    val newName = "    "
    every { validateInputUseCase.isValidName(newName) } returns false

    assertThrows<PlanMateException.ValidationException.InvalidProjectNameException> {
     editProjectNameUseCase.editProjectName(EditProjectFactory.adminUser(), EditProjectFactory.validProjectId, newName)
    }
   }

   @Test
   fun `should throw ProjectNameAlreadyExistException when project name already exists`() {
    val newName = "Duplicate Project"
    val project1 = EditProjectFactory.validProject()
    val duplicateProject = project1.copy(id = UUID.randomUUID(), name = newName)

    every { validateInputUseCase.isValidName(newName) } returns true
    every { projectRepository.getProject(project1.id) } returns project1
    every { projectRepository.getProjects() } returns listOf(project1, duplicateProject)

    assertThrows<PlanMateException.ValidationException.ProjectNameAlreadyExistException> {
     editProjectNameUseCase.editProjectName(EditProjectFactory.adminUser(), project1.id, newName)
    }
   }

    @Test
    fun `should add log entry when project name is updated`() {
        val newName = "Updated Project Name"
        val project = EditProjectFactory.validProject()

        every { validateInputUseCase.isValidName(newName) } returns true
        every { projectRepository.getProject(project.id) } returns project
        every { projectRepository.getProjects() } returns listOf(project)
        every { projectRepository.updateProjectNameById(project.id, newName) } returns true

        val result = editProjectNameUseCase.editProjectName(EditProjectFactory.adminUser(), project.id, newName)

        assertTrue(result)

        verify {
            logRepository.addLog(
                withArg {
                    assert(it.message.contains("Project name was changed"))
                    assert(it.entityId == project.id)
                }
            )
        }
    }

}

