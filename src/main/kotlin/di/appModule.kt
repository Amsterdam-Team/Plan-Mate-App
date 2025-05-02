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
import org.koin.core.qualifier.named
import org.koin.dsl.module

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


//    single { AuthRepositoryImpl(get(user)) }
//    single { TaskRepositoryImpl(get(task)) }
//    single { ProjectRepositoryImpl(get(project)) }
//    single { LogRepositoryImpl(get(log)) }


}