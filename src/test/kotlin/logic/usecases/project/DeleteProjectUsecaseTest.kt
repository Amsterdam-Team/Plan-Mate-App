package logic.usecases.project

import logic.repository.ProjectRepository
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import data.repository.project.ProjectRepositoryImpl
import io.mockk.*
import logic.exception.PlanMateException
import logic.exception.PlanMateException.NotFoundException.*
import logic.exception.PlanMateException.AuthorizationException.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class DeleteProjectUsecaseTest{
  lateinit var projectRepository: ProjectRepositoryImpl
  lateinit var deleteProjectUsecase: DeleteProjectUsecase

  @BeforeEach
  fun setUp() {
   projectRepository = mockk(relaxed = true)
   deleteProjectUsecase = DeleteProjectUsecase(projectRepository)
  }

     @Test
     fun `should not throw any exception when deleting project successfully `(){


      // when & then
         assertDoesNotThrow {
             deleteProjectUsecase.deleteProject("er0uj34")
         }
      }


     @Test
     fun `should throw not found exception when deleting project not exist`(){

         // when & then
         assertThrows<ProjectNotFoundException> {
             deleteProjectUsecase.deleteProject("")
         }

     }

    @Test
    fun `should throw Admin Privileges Required Exception when deleting by user not admin`(){

        // when & then
        assertThrows<AdminPrivilegesRequiredException> {
            deleteProjectUsecase.deleteProject("")
        }

    }


 }