package data.repository.project

import data.datasources.CsvDataSource
import io.mockk.mockk
import io.mockk.verify
import logic.entities.Project
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import java.util.UUID.randomUUID

class DeleteProject {

    lateinit var dataSource: CsvDataSource<Project>
    lateinit var repository: ProjectRepositoryImpl
    lateinit var dummyId: UUID

    @BeforeEach
    fun setUp() {
        dataSource = CsvDataSource(mockk(), mockk())
        repository = ProjectRepositoryImpl(dataSource)
        dummyId = randomUUID()

    }

    @Test
    fun `should delete project successfully when project is already exist`() {

        assertDoesNotThrow {
            repository.deleteProject(dummyId)

        }
    }


    @Test
    fun `should call getAll and saveAll function form datasource  when trying to delete project`(){

        repository.deleteProject(dummyId)

        verify(exactly = 1) { dataSource.getAll() }
        verify(exactly = 1) {  dataSource.saveAll(mockk()) }

    }

    @Test
    fun `should throw not found exception when deleting project not exist`() {

        // when & then
        assertThrows<ProjectNotFoundException> {
            repository.deleteProject(dummyId)
        }

    }

    @Test
    fun `should throw Admin Privileges Required Exception when deleting by user not admin`() {

        // when & then
        assertThrows<AdminPrivilegesRequiredException> {
            repository.deleteProject(dummyId)
        }

    }

}