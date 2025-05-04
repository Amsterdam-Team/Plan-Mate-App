package data.repository.project

import data.datasources.CsvDataSource
import data.datasources.projectDataSource.ProjectDataSourceInterface
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import logic.entities.Project
import logic.exception.PlanMateException.DataSourceException.EmptyDataException
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import logic.usecases.project.helper.DeleteProjectTestFactory.someProjects
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import java.util.UUID.randomUUID

class DeleteProjectRepositoryTest {
    lateinit var dataSource: ProjectDataSourceInterface
    lateinit var repository: ProjectRepositoryImpl
    lateinit var dummyId: UUID

    @BeforeEach
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = ProjectRepositoryImpl(dataSource)
        dummyId = randomUUID()

    }

    @Test
    fun `should delete project successfully when project is already exist`() {
        // given
        every { dataSource.deleteProject(someProjects[0].id) } returns true
        assertDoesNotThrow {
            repository.deleteProject(someProjects[0].id)

        }
    }


}