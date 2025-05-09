package logic.usecases.project

import helper.ProjectFactory.adminUser
import helper.ProjectFactory.mateUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.PlanMateException
import logic.repository.ProjectRepository
import logic.usecases.logs.LoggerUseCase
import logic.usecases.utils.StateManager
import logic.usecases.utils.ValidateInputUseCase
import helper.ProjectFactory.validProject
import helper.ProjectFactory.validProjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class EditProjectUseCaseTest{

   private lateinit var projectRepository: ProjectRepository
   private lateinit var validateInputUseCase: ValidateInputUseCase
   private lateinit var editProjectNameUseCase: EditProjectUseCase
   private lateinit var loggerUseCase: LoggerUseCase
   private lateinit var stateManager: StateManager

   @BeforeEach
   fun setup() {
    projectRepository = mockk(relaxed = true)
    validateInputUseCase = mockk(relaxed = true)
    loggerUseCase = mockk(relaxed = true)
    stateManager = mockk(relaxed = true)
    editProjectNameUseCase = EditProjectUseCase(projectRepository,validateInputUseCase,stateManager, loggerUseCase )
   }

    @Test
    fun `should update project name when user is admin, name valid and not taken`() = runTest{
        val newName = "Updated Project Name"
        every { validateInputUseCase.isValidName(newName) } returns true
        coEvery { projectRepository.getProject(validProjectId) } returns validProject()
        coEvery { projectRepository.getProjects() } returns listOf(validProject())
        coEvery { projectRepository.updateProjectNameById(validProjectId, newName) } returns true
        every { stateManager.getLoggedInUser() } returns adminUser()
        val result = editProjectNameUseCase.editProjectName(validProjectId, newName)

        assertTrue(result)
        coVerify { projectRepository.updateProjectNameById(validProjectId, newName) }
    }


    @Test
   fun `should throw AdminPrivilegesRequiredException when user is not admin`() = runTest{
    val newName = "Updated Project Name"
        every { stateManager.getLoggedInUser() } returns mateUser

    assertThrows<PlanMateException.AuthorizationException.AdminPrivilegesRequiredException> {
     editProjectNameUseCase.editProjectName( validProjectId, newName)
    }
   }


   @Test
   fun `should throw InvalidProjectNameException when new name is invalid`() = runTest{
    val newName = "    "
    every { validateInputUseCase.isValidName(newName) } returns false
       every { stateManager.getLoggedInUser() } returns adminUser()

    assertThrows<PlanMateException.ValidationException.InvalidProjectNameException> {
     editProjectNameUseCase.editProjectName( validProjectId, newName)
    }
   }

   @Test
   fun `should throw ProjectNameAlreadyExistException when project name already exists`() = runTest{
    val newName = "Duplicate Project"
    val project1 = validProject()
    val duplicateProject = project1.copy(id = UUID.randomUUID(), name = newName)

    every { validateInputUseCase.isValidName(newName) } returns true
       coEvery { projectRepository.getProject(project1.id) } returns project1
       coEvery { projectRepository.getProjects() } returns listOf(project1, duplicateProject)
       every { stateManager.getLoggedInUser() } returns adminUser()

    assertThrows<PlanMateException.ValidationException.ProjectNameAlreadyExistException> {
     editProjectNameUseCase.editProjectName(project1.id, newName)
    }
   }

    @Test
    fun `should add log entry when project name is updated`() =runTest{
        val newName = "Updated Project Name"
        val project = validProject()

        every { validateInputUseCase.isValidName(newName) } returns true
        coEvery { projectRepository.getProject(project.id) } returns project
        coEvery { projectRepository.getProjects() } returns listOf(project)
        coEvery { projectRepository.updateProjectNameById(project.id, newName) } returns true
        every { stateManager.getLoggedInUser() } returns adminUser()

        val result = editProjectNameUseCase.editProjectName(project.id, newName)

        assertTrue(result)

        coVerify {
            loggerUseCase.createLog(any(),project.id)
        }
    }

}

