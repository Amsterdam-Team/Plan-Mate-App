package di

import com.mongodb.kotlin.client.coroutine.MongoCollection
import data.datasources.logDataSource.ILogDataSource
import data.datasources.logDataSource.LogDataSource
import data.datasources.mongoUtils.MongoDatabaseFactory
import data.datasources.mongoUtils.MongoTransactionManager
import data.datasources.projectDataSource.IProjectDataSource
import data.datasources.projectDataSource.ProjectDataSource
import data.datasources.taskDataSource.ITaskDataSource
import data.datasources.taskDataSource.TaskDataSource
import data.datasources.userDataSource.IUserDataSource
import data.datasources.userDataSource.UserDataSource
import data.proxy.ProjectRepositoryProxy
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
import logic.usecases.login.LoginUseCase
import logic.usecases.logs.GetProjectHistoryUseCase
import logic.usecases.logs.LoggerUseCase
import logic.usecases.logs.ViewTaskLogsUseCase
import logic.usecases.project.*
import logic.usecases.state.*
import logic.usecases.task.*
import logic.usecases.user.CreateUserUseCase
import logic.usecases.utils.StateManager
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.console.ConsoleIO
import ui.console.ConsoleIOImpl
import ui.controller.BaseUIController
import ui.login.LoginUIController
import ui.logs.ViewProjectHistoryUIController
import ui.logs.ViewTaskLogsUIController
import ui.menuHandler.AdminMenuHandler
import ui.menuHandler.MateMenuHandler
import ui.project.*
import ui.state.AddStateUIController
import ui.state.UpdateStateUiController
import ui.task.*
import ui.user.CreateUserUIController

val appModule = module {

    val userMap = named("userMap")
    val adminMap = named("adminMap")

    // Collection names
    val usersCollection = named("usersCollection")
    val projectsCollection = named("projectsCollection")
    val logsCollection = named("logsCollection")
    val tasksCollection = named("tasksCollection")

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

    // Repository Implementation Names
    val userRepositoryImpl = named("userRepository")
    val taskRepositoryImpl = named("taskRepository")
    val logRepositoryImpl = named("logRepository")
    val projectRepositoryImpl = named("projectRepository")

    // Repository Proxies Names
    val userRepositoryProxy = named("userRepositoryProxy")
    val taskRepositoryProxy = named("taskRepositoryProxy")
    val projectRepositoryProxy = named("projectRepositoryProxy")

    // MongoCollections
    single<MongoCollection<User>>(usersCollection){ MongoDatabaseFactory.db.getCollection<User>("users")}
    single<MongoCollection<Project>>(projectsCollection){ MongoDatabaseFactory.db.getCollection<Project>("projects")}
    single<MongoCollection<Task>>(tasksCollection){ MongoDatabaseFactory.db.getCollection<Task>("tasks")}
    single<MongoCollection<LogItem>>(logsCollection){ MongoDatabaseFactory.db.getCollection<LogItem>("logs")}

    // DataSources
    single<ILogDataSource> { LogDataSource(get(logsCollection)) }
    single<IProjectDataSource> { ProjectDataSource(get(projectsCollection)) }
    single<IUserDataSource> { UserDataSource(get(usersCollection)) }
    single<ITaskDataSource> { TaskDataSource(get(tasksCollection)) }

    single { MongoTransactionManager(MongoDatabaseFactory.client) }

    // Repositories
    single<AuthRepository>(userRepositoryImpl) { AuthRepositoryImpl(get()) }
    single<TaskRepository>(taskRepositoryImpl) { TaskRepositoryImpl(get()) }
    single<LogRepository>(logRepositoryImpl) { LogRepositoryImpl(get()) }
    single<ProjectRepository>(projectRepositoryImpl) { ProjectRepositoryImpl(get()) }
    single<StateManager>{ StateManager }

    // Repository Proxies
    single<ProjectRepository>(projectRepositoryProxy) { ProjectRepositoryProxy(get(projectRepositoryImpl), get(logRepositoryImpl), get()) }


    //logger use Case
    single{ LoggerUseCase(get(),get()) }

    // Project UseCases
    single { CreateProjectUseCase(get(projectRepositoryProxy), get(), get()) }
    single { DeleteProjectUseCase(get(projectRepositoryProxy), get(), get()) }
    single { EditProjectUseCase(get(projectRepositoryProxy), get(), get()) }
    single { GetAllProjectsUseCase(get(projectRepositoryProxy), get()) }
    single { GetProjectDetailsUseCase(get(projectRepositoryProxy),get(), get()) }
    single { GetProjectHistoryUseCase(get(logRepositoryImpl)) }


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
    single<BaseUIController>(viewAllTasksByProjectIdUiController) { ViewAllTasksByProjectIdUIController(get(), get(), get()) }


    // User Menus
    single<Map<Int, BaseUIController>>(userMap) {
        mapOf(
            1 to get(viewAllProjectsUiController),
            2 to get(getProjectUiController),
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
            2 to get(getProjectUiController),
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