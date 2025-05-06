package di


import logic.usecases.auth.LoginUseCase
import logic.usecases.project.DeleteProjectUseCase
import logic.usecases.project.GetProjectDetailsUseCase
import logic.usecases.state.DeleteStateUseCase
import logic.usecases.state.GetProjectStatesUseCase
import logic.usecases.state.GetTaskStateUseCase
import logic.usecases.state.UpdateStateUseCase
import logic.usecases.task.*
import logic.usecases.validation.ValidateInputUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //  single { GetProjectsUseCase(get(), get()) }

    single { DeleteStateUseCase(get()) }
    single { UpdateStateUseCase(get(), get()) }
    single { GetProjectStatesUseCase(get()) }
    single { GetTaskStateUseCase(get()) }

    single { CreateTaskUseCase(get(), get(), get()) }
    single { EditTaskUseCase(get(), get()) }
    single { GetAllTasksByProjectIdUseCase(get(), get()) }
    single { GetTaskByIdUseCase(get(), get()) }
    single { DeleteTaskUseCase(get()) }

    single { LoginUseCase(get()) }

    // single { ViewProjectHistoryUseCase(get()) }
    single { ViewTaskLogsUseCase(get(), get()) }

    single { ValidateInputUseCase() }



    single { GetProjectDetailsUseCase(get(), get(), get()) }
    single { DeleteProjectUseCase(get(), get()) }
    single { ValidateInputUseCase() }

}