package di
import data.datasources.*
import data.datasources.parser.*
import data.repository.auth.AuthRepositoryImpl
import data.repository.log.LogRepositoryImpl
import data.repository.project.ProjectRepositoryImpl
import data.repository.task.TaskRepositoryImpl
import logic.entities.*
import logic.repository.*
import logic.usecases.*
import logic.usecases.project.*
import logic.usecases.state.*
import logic.usecases.task.*
import logic.usecases.user.CreateUserUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.*
import ui.console.*
import ui.controllers.*
import ui.menuHandler.*
import ui.project.*
import ui.task.*
import java.util.*

val appModule = module {

    val task = named("task")
    val user = named("user")
    val project = named("project")
    val log = named("log")

    // Parsers
    single { UserCsvParser() }
    single { TaskCsvParser() }
    single { ProjectCsvParser() }
    single { LogItemCsvParser() }

    // File Managers
    single(user) { FileManager.create<User>() }
    single(task) { FileManager.create<Task>() }
    single(project) { FileManager.create<Project>() }
    single(log) { FileManager.create<LogItem>() }

    // Data Sources
    single<DataSource>(user) { CsvDataSource(get<FileManager<User>>(user), get<UserCsvParser>()) }
    single<DataSource>(task) { CsvDataSource(get<FileManager<Task>>(task), get<TaskCsvParser>()) }
    single<DataSource>(project) { CsvDataSource(get<FileManager<Project>>(project), get<ProjectCsvParser>()) }
    single<DataSource>(log) { CsvDataSource(get<FileManager<LogItem>>(log), get<LogItemCsvParser>()) }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(user)) }
    single<TaskRepository> { TaskRepositoryImpl(get(task)) }
    single<ProjectRepository> { ProjectRepositoryImpl(get(project), get(log)) }
    single<LogRepository> { LogRepositoryImpl(get(log)) }

    // Use Cases
    single { ValidateInputUseCase() }
    single { CreateProjectUseCase(get(), User(UUID.randomUUID(), true, "admin", "admin"), get()) }
    single { DeleteProjectUseCase(get(), get(), get()) }
    single { GetProjectDetailsUseCase(get(), get(), get()) }
    single { GetProjectHistoryUseCase(get()) }
    single { CreateTaskUseCase(get(), get(), get()) }
    single { EditTaskUseCase(get(), get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }
    single { GetTaskByIdUseCase(get()) }
    single { DeleteStateUseCase(get()) }
    single { UpdateStateUseCase(get(), get(), get()) }
    single { GetProjectStatesUseCase(get()) }
    single { GetTaskStateUseCase(get()) }
    single { ViewTaskLogsUseCase(get(), get()) }
    single { LoginUseCase(get()) }

    single { CreateUserUseCase(get(), get()) }
    single { CreateStateUseCase(get()) }

    // Console IO
    single<ConsoleIO> { ConsoleIOImpl() }

    // UI Controllers
    single { CreateProjectUIController(get(), get()) }
    single { DeleteProjectUiController(get(), get()) }
    single { GetProjectUIController(get(), get()) }
    single { ViewProjectHistoryUIController(get(), get()) }
    single { DeleteTaskUIController(get(), get()) }
    single { EditTaskUiController(get(), get()) }
    single { UpdateStateUiController(get(), get()) }
    single { CreateTaskUIController(get(), get()) }
    single { CreateUserUIController(get(), get()) }
    single { CreateStateUIController(get(), get()) }
    single { ViewTaskLogsUIController(get(), get()) }

    // Menu Handlers
    single { AdminMenuHandler(get()) }
    single { MateMenuHandler(get()) }

    // Login Controller
    single { LoginUIController(get(), get(), get(), get()) }
}
