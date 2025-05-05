package di

import logic.usecases.LoginUseCase
import logic.usecases.ValidateInputUseCase
import logic.usecases.ViewTaskLogsUseCase
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.project.GetProjectsUseCase
import logic.usecases.state.DeleteStateUseCase
import logic.usecases.state.GetProjectStatesUseCase
import logic.usecases.state.GetTaskStateUseCase
import logic.usecases.state.UpdateStateUseCase
import logic.usecases.task.*
import org.koin.dsl.module

val useCaseModule = module {

    single { DeleteProjectUseCase(get()) }
    single { GetProjectsUseCase(get(), get()) }

    single { DeleteStateUseCase(get()) }
    single { UpdateStateUseCase(get()) }
    single { GetProjectStatesUseCase(get()) }
    single { GetTaskStateUseCase(get()) }

    single { CreateTaskUseCase(get(), get(), get()) }
    single { EditTaskUseCase(get()) }
    single { GetAllTasksByProjectIdUseCase(get()) }
    single { GetTaskByIdUseCase(get()) }
    single { DeleteTaskUseCase(get()) }

    single { LoginUseCase(get()) }

    // single { ViewProjectHistoryUseCase(get()) }
    single { ViewTaskLogsUseCase(get(), get()) }

    single { ValidateInputUseCase() }

}