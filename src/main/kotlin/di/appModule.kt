package di

import data.datasources.CsvDataSource
import data.datasources.DataSource
import data.datasources.FileManager
import data.datasources.logDataSource.LogCsvDataSource
import data.datasources.logDataSource.LogDataSourceInterface
import data.datasources.parser.LogItemCsvParser
import data.datasources.parser.ProjectCsvParser
import data.datasources.parser.TaskCsvParser
import data.datasources.parser.UserCsvParser
import data.datasources.projectDataSource.ProjectCsvDataSource
import data.datasources.projectDataSource.ProjectDataSourceInterface
import data.datasources.taskDataSource.TaskCsvDataSource
import data.datasources.taskDataSource.TaskDataSourceInterface
import data.datasources.userDataSource.UserCsvDataSource
import data.datasources.userDataSource.UserDataSourceInterface
import logic.entities.LogItem
import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import logic.usecases.project.CreateProjectUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import ui.controller.project.CreateProjectUIController
import ui.controller.project.DeleteProjectUiController
import ui.controller.project.GetProjectUIController
import ui.controller.project.ViewProjectHistoryUIController
import ui.controller.state.UpdateStateUiController
import ui.controller.task.*
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import java.util.*

val appModule = module {
    single<ConsoleIO> { ConsoleIOImpl() }
    val task = named("task")
    val user = named("user")
    val project = named("project")
    val log = named("log")

    single { UserCsvParser() }
    single { TaskCsvParser() }
    single { ProjectCsvParser() }
    single { LogItemCsvParser() }

    single(user) { FileManager.create<User>() }
    single(task) { FileManager.create<Task>() }
    single(project) { FileManager.create<Project>() }
    single(log) { FileManager.create<LogItem>() }

    single<DataSource>(user) { CsvDataSource(get<FileManager<User>>(user), get<UserCsvParser>()) }
    single<DataSource>(task) { CsvDataSource(get<FileManager<Task>>(task), get<TaskCsvParser>()) }
    single<DataSource>(project) { CsvDataSource(get<FileManager<Project>>(project), get<ProjectCsvParser>()) }
    single<DataSource>(log) { CsvDataSource(get<FileManager<LogItem>>(log), get<LogItemCsvParser>()) }





    single {
        CreateProjectUseCase(
            get(),
            User(id = UUID.randomUUID(), username = "fsef", password = "fsefs", isAdmin = true)
        )
    }


// DataSourceInterface
    single<TaskDataSourceInterface> { TaskCsvDataSource(get<DataSource>(task) as CsvDataSource<Task>) }
    single<UserDataSourceInterface> { UserCsvDataSource(get<DataSource>(user) as CsvDataSource<User>) }
    single<ProjectDataSourceInterface> { ProjectCsvDataSource(get<DataSource>(project) as CsvDataSource<Project>) }
    single<LogDataSourceInterface> { LogCsvDataSource(get<DataSource>(project) as CsvDataSource<LogItem>) }



    single { CreateProjectUIController(get()) }
    single { DeleteTaskUIController(get(), get()) }
    single { UpdateStateUiController(get(), get()) }
    single { ViewProjectHistoryUIController(get(), get()) }

    single { AdminMenuHandler(get()) }
    single { MateMenuHandler(get()) }
    single { GetProjectUIController(get(), get()) }

    single { DeleteProjectUiController(get(), get()) }
    single { EditTaskUiController(get(), get()) }
    single { CreateTaskUIController(get(), get()) }


    single { ViewTaskLogsUIController(get(), get()) }
    single { ViewTasksByProjectIdUIController(get(), get(), get()) }
    single { ViewTaskDetailsUIController(get(), get()) }

}