package data.repository.project

import data.datasources.CsvDataSource
import data.datasources.FileManager
import data.repository.task.TaskRepositoryImpl
import io.mockk.mockk
import logic.entities.Project
import logic.exception.PlanMateException
import logic.exception.PlanMateException.ExistException.ProjectAlreadyExistsException

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.*
import java.util.UUID.*

class ProjectRepositoryImplTest {
    lateinit var dataSource: CsvDataSource<Project>
    lateinit var repository: ProjectRepositoryImpl
    lateinit var dummyProject: Project

    @BeforeEach
    fun setUp() {
        dataSource = CsvDataSource(mockk(), mockk())
        repository = ProjectRepositoryImpl(dataSource)
        dummyProject = mockk()
    }

    @Test
    fun `should not throw any exception when create project complete successfully`() {

        assertDoesNotThrow {
            repository.createProject(
                dummyProject
            )
        }
    }

    @Test
    fun `should throw project exist exception when create project already exist`() {

        assertThrows<ProjectAlreadyExistsException> {
            repository.createProject(dummyProject)
        }
    }


}