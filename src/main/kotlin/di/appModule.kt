package di

import data.datasources.CsvDataSource
import data.datasources.DataSource
import data.datasources.FileManager
import data.datasources.parser.LogItemCsvParser
import data.datasources.parser.ProjectCsvParser
import data.datasources.parser.TaskCsvParser
import data.datasources.parser.UserCsvParser
import data.repository.auth.AuthRepositoryImpl
import data.repository.log.LogRepositoryImpl
import data.repository.project.ProjectRepositoryImpl
import data.repository.task.TaskRepositoryImpl
import logic.entities.LogItem
import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import logic.repository.AuthRepository
import logic.repository.LogRepository
import logic.repository.ProjectRepository
import logic.repository.TaskRepository
import logic.usecases.task.DeleteTaskUseCase
import logic.usecases.auth.LoginUseCase
import logic.usecases.validation.ValidateInputUseCase
import logic.usecases.task.ViewTaskLogsUseCase
import logic.usecases.project.CreateProjectUseCase
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.project.GetProjectDetailsUseCase
import logic.usecases.project.GetProjectHistoryUseCase
import logic.usecases.state.DeleteStateUseCase
import logic.usecases.state.GetProjectStatesUseCase
import logic.usecases.state.GetTaskStateUseCase
import logic.usecases.state.UpdateStateUseCase
import logic.usecases.task.CreateTaskUseCase
import logic.usecases.task.EditTaskUseCase
import logic.usecases.task.GetAllTasksByProjectIdUseCase
import logic.usecases.task.GetTaskByIdUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.controller.task.ViewTaskLogsUIController
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import ui.controller.project.CreateProjectUIController
import ui.controller.task.DeleteTaskUIController
import ui.controller.state.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.controller.project.DeleteProjectUiController
import ui.controller.project.GetProjectUIController
import ui.controller.project.ViewProjectHistoryUIController
import ui.controller.task.EditTaskUiController
import java.util.*

val appModule = module {

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

    single<AuthRepository> { AuthRepositoryImpl(get(user)) }
    single<TaskRepository> { TaskRepositoryImpl(get(task)) }
    single<ProjectRepository> { ProjectRepositoryImpl(get(project)) }
    single<LogRepository> { LogRepositoryImpl(get(log)) }

    single { ValidateInputUseCase() }

    single { CreateProjectUseCase(get(), User(id = UUID.randomUUID(), username = "fsef", password = "fsefs", isAdmin = true)) }
    single { DeleteProjectUseCase(get(), get(), get()) }
    single { GetProjectDetailsUseCase(get(),get(), get()) }

    single { DeleteStateUseCase(get()) }
    single { UpdateStateUseCase(get(), get(), get()) }
    single { GetProjectStatesUseCase(get()) }
    single { GetTaskStateUseCase(get()) }

    single { CreateTaskUseCase(get(), get(), get()) }
    single { EditTaskUseCase(get(), get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }
    single { GetTaskByIdUseCase(get()) }
    single { DeleteTaskUseCase(get()) }

    single { LoginUseCase(get()) }
    single { GetProjectHistoryUseCase(get()) }
    single { ViewTaskLogsUseCase(get(),get()) }

    single<ConsoleIO> { ConsoleIOImpl() }

    single { CreateProjectUIController(get()) }
    single { DeleteTaskUIController(get(), get()) }
    single { UpdateStateUiController(get(), get()) }
    single { ViewProjectHistoryUIController(get(), get()) }

    single { AdminMenuHandler(get()) }
    single { MateMenuHandler(get()) }
    single { GetProjectUIController(get(), get()) }

    single { DeleteProjectUiController(get(), get()) }
    single { EditTaskUiController(get(), get()) }


    single { ViewTaskLogsUIController(get(),get()) }

}