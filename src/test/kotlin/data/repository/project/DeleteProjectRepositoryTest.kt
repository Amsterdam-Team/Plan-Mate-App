package data.repository.project

import com.google.common.truth.Truth.assertThat
import data.datasources.projectDataSource.ProjectDataSourceInterface
import io.mockk.every
import io.mockk.mockk
import logic.exception.PlanMateException.NotFoundException.ProjectNotFoundException
import helper.DeleteProjectTestFactory.someProjects
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
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
    fun `should return true when deleting project complete successfully`() {
        // given
        every { dataSource.deleteProject(someProjects[0].id) } returns true
        // when
        val result = repository.deleteProject(someProjects[0].id)

        // then
        assertThat(result).isTrue()

    }



    @Test
    fun `should throw project not found when deleting project not exist`() {
        // given
        every { dataSource.deleteProject(someProjects[0].id) } throws ProjectNotFoundException

        // when & then
        assertThrows <ProjectNotFoundException> {
            repository.deleteProject(someProjects[0].id)

        }
    }


}