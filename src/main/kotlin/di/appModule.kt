package di

import com.mongodb.kotlin.client.coroutine.MongoCollection
import console.ConsoleIoImpl
import data.datasources.MongoDatabaseFactory
import data.datasources.logDataSource.ILogDataSource
import data.datasources.logDataSource.LogDataSource
import data.datasources.projectDataSource.IProjectDataSource
import data.datasources.projectDataSource.ProjectDataSource
import data.datasources.taskDataSource.ITaskDataSource
import data.datasources.taskDataSource.TaskDataSource
import data.datasources.userDataSource.IUserDataSource
import data.datasources.userDataSource.UserDataSource
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
import logic.usecases.LoggerUseCase
import logic.usecases.LoginUseCase
import logic.usecases.StateManager
import logic.usecases.ValidateInputUseCase
import logic.usecases.ViewTaskLogsUseCase
import logic.usecases.project.*
import logic.usecases.state.*
import logic.usecases.task.*
import logic.usecases.user.CreateUserUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.LoginUIController
import ui.ViewTaskLogsUIController
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import ui.controller.BaseUIController
import ui.controller.CreateTaskUIController
import ui.controllers.AddStateUIController
import ui.controllers.CreateProjectUIController
import ui.controllers.CreateUserUIController
import ui.controllers.UpdateStateUiController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.DeleteProjectUiController
import ui.project.EditProjectUIController
import ui.project.GetProjectUIController
import ui.project.ViewAllProjectsUIController
import ui.project.ViewProjectHistoryUIController
import ui.task.DeleteTaskUIController
import ui.task.EditTaskUiController
import ui.task.ViewAllTaksByProjectIdUIController
import ui.task.ViewTaskDetailsUIController
import java.util.*

val appModule = module {

    val userMap = named("userMap")
    val adminMap = named("adminMap")


    // AdminUIController names
    val updateStateUiController = named("updateStateUiController")
    val addStateUiController = named("addStateUiController")
    val createUserUiController = named("createUserUiController")

    // ProjectUIController names
    val createProjectUiController = named("createProjectUiController")
    val deleteProjectUiController = named("deleteProjectUiController")
    val viewProjectHistoryUiController = named("viewProjectHistoryUiController")
    val getProjectUiController = named("getProjectUiController")
    val viewAllProjectsUiController = named("viewAllProjectsUiController")
    val editProjectUiController = named("editProjectUiController")

    // TaskUIController names
    val createTaskUiController = named("createTaskUiController")
    val deleteTaskUiController = named("deleteTaskUiController")
    val viewTaskLogsUiController = named("viewTaskLogsUiController")
    val editTaskUiController = named("editTaskUiController")
    val viewTaskDetailsUiController = named("viewTaskDetailsUiController")
    val viewAllTasksByProjectIdUiController = named("viewAllTasksByProjectIdUiController")

    // MongoCollections
    single<MongoCollection<User>>{MongoDatabaseFactory.db.getCollection<User>("users")}
    single<MongoCollection<Project>>{MongoDatabaseFactory.db.getCollection<Project>("projects")}
    single<MongoCollection<Task>>{MongoDatabaseFactory.db.getCollection<Task>("tasks")}
    single<MongoCollection<LogItem>>{MongoDatabaseFactory.db.getCollection<LogItem>("logs")}

    // DataSources
    single<ILogDataSource> { LogDataSource(get()) }
    single<IProjectDataSource> { ProjectDataSource(get()) }
    single<IUserDataSource> { UserDataSource(get()) }
    single<ITaskDataSource> { TaskDataSource(get()) }


    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single<LogRepository> { LogRepositoryImpl(get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
    single<StateManager>{ StateManager }


    single<User> { User(id = UUID.randomUUID(), username = "fsef", password = "fsefs", isAdmin = true) }

    single { ValidateInputUseCase() }

    //logger use Case
    single{ LoggerUseCase(get(),get()) }

    // Project UseCases
    single { CreateProjectUseCase(get(), get(), get(), get()) }
    single { DeleteProjectUseCase(get(), get(), get(), get()) }
    single { EditProjectUseCase(get(), get(), get(),get()) }
    single { GetAllProjectsUseCase(get()) }
    single { GetProjectDetailsUseCase(get(),get(), get()) }
    single { GetProjectHistoryUseCase(get()) }


    // StateUseCase
    single { AddStateUseCase(get(), get(), get()) }
    single { DeleteStateUseCase(get()) }
    single { GetProjectStatesUseCase(get()) }
    single { UpdateStateUseCase(get(), get(), get()) }
    single { GetTaskStateUseCase(get()) }

    // TaskUseCase
    single { CreateTaskUseCase(get(), get(), get(),get()) }
    single { DeleteTaskUseCase(get(),get()) }
    single { EditTaskUseCase(get(), get(),get()) }
    single { GetAllTasksByProjectIdUseCase(get(), get()) }
    single { GetTaskByIdUseCase(get(), get()) }
    single { ViewTaskLogsUseCase(get(),get()) }

    // UserUseCase
    single { LoginUseCase(get(),get()) }
    single { CreateUserUseCase(get(), get(),get()) }

    // ConsoleIO
    single<ConsoleIO> { ConsoleIOImpl() }
    single<console.ConsoleIO> { ConsoleIoImpl() }

    // AdminUIController
    single<BaseUIController>(updateStateUiController) { UpdateStateUiController(get(), get()) }
    single<BaseUIController>(addStateUiController) { AddStateUIController(get(), get()) }
    single<BaseUIController>(createUserUiController) { CreateUserUIController(get(), get()) }

    // ProjectUIController
    single<BaseUIController>(createProjectUiController) { CreateProjectUIController(get(), get()) }
    single<BaseUIController>(deleteProjectUiController) { DeleteProjectUiController(get(), get()) }
    single<BaseUIController>(viewProjectHistoryUiController) { ViewProjectHistoryUIController(get(), get()) }
    single<BaseUIController>(getProjectUiController) { GetProjectUIController(get(), get()) }
    single<BaseUIController>(viewAllProjectsUiController) { ViewAllProjectsUIController(get()) }
    single<BaseUIController>(editProjectUiController) { EditProjectUIController(get(), get()) }

    // TaskUIController
    single<BaseUIController>(createTaskUiController) { CreateTaskUIController(get(), get()) }
    single<BaseUIController>(deleteTaskUiController) { DeleteTaskUIController(get(), get()) }
    single<BaseUIController>(viewTaskLogsUiController) { ViewTaskLogsUIController(get(), get()) }
    single<BaseUIController>(editTaskUiController) { EditTaskUiController(get(), get()) }
    single<BaseUIController>(viewTaskDetailsUiController) { ViewTaskDetailsUIController(get(), get(), get(), get()) }
    single<BaseUIController>(viewAllTasksByProjectIdUiController) { ViewAllTaksByProjectIdUIController(get(), get(), get()) }


    // User Menus
    single<Map<Int, BaseUIController>>(userMap) {
        mapOf(
            1 to get(viewAllProjectsUiController),
            2 to get(getProjectUiController),                 // warning: no UI Controller for "View State"
            3 to get(createTaskUiController),
            4 to get(editTaskUiController),
            5 to get(deleteTaskUiController),
            6 to get(viewAllTasksByProjectIdUiController),
            7 to get(viewProjectHistoryUiController),
            8 to get(viewTaskLogsUiController)
        )
    }

    // Admin Menus
    single<Map<Int, BaseUIController>>(adminMap) {
        mapOf(
            1 to get(viewAllProjectsUiController),
            2 to get(getProjectUiController),                 // warning: no UI Controller for "View State"
            3 to get(createTaskUiController),
            4 to get(editTaskUiController),
            5 to get(deleteTaskUiController),
            6 to get(viewAllTasksByProjectIdUiController),
            7 to get(viewProjectHistoryUiController),
            8 to get(viewTaskLogsUiController),
            9 to get(createUserUiController),
            10 to get(createProjectUiController),
            11 to get(editProjectUiController),
            12 to get(deleteProjectUiController),
            13 to get(addStateUiController),
            14 to get(updateStateUiController),
        )
    }


    // Menu Handlers
    single { AdminMenuHandler(get(adminMap)) }
    single { MateMenuHandler(get(userMap)) }

    // LoginUiController
    single { LoginUIController(get(), get(), get(), get()) }
}