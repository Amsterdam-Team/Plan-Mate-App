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

