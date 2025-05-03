package data.repository.project

import data.datasources.CsvDataSource
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
    lateinit var dataSource: CsvDataSource<Project>
    lateinit var repository: ProjectRepositoryImpl
    lateinit var dummyId: UUID

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = ProjectRepositoryImpl(mockk())
        dummyId = randomUUID()

    }

    @Test
    fun `should delete project successfully when project is already exist`() {
        // given
        every {dataSource.getAll()} returns someProjects
        every { dataSource.saveAll(any()) } just runs
        assertDoesNotThrow {
            repository.deleteProject(someProjects[0].id)

        }
    }


    @Test
    fun `should call getAll and saveAll function form datasource  when trying to delete project`(){
        every {dataSource.getAll()} returns someProjects
        every { dataSource.saveAll(any()) } just runs
        repository.deleteProject(someProjects[0].id)

        verify(exactly = 1) { dataSource.getAll() }
        verify(exactly = 1) {  dataSource.saveAll(any()) }

    }

    @Test
    fun `should throw not found exception when deleting project not exist`() {
        every {dataSource.getAll()} returns someProjects
        every { dataSource.saveAll(any()) } just runs
        // when & then
        assertThrows<ProjectNotFoundException> {
            repository.deleteProject(dummyId)
        }

    }
    @Test
    fun `should throw empty data exception when deleting project and there are not any project yet`() {
        every {dataSource.getAll()} returns emptyList()
        every { dataSource.saveAll(any()) } just runs
        // when & then
        assertThrows<EmptyDataException> {
            repository.deleteProject(dummyId)
        }

    }



}