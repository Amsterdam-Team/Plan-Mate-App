package logic.usecases.project

import logic.entities.Project
import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.AdminPrivilegesRequiredException
import logic.exception.PlanMateException.ValidationException.*
import logic.repository.ProjectRepository

class CreateProjectUseCase(private val repository: ProjectRepository, private val user: User) {
    fun createProject(project: Project) {
        if (!user.isAdmin) throw AdminPrivilegesRequiredException
        isValidProjectName(project.name)
        isValidProjectStates(project.states)
        repository.createProject(project)
    }

    fun isValidProjectName(name: String): String {
        val validNameRegex = Regex("^(?=.*[a-zA-Z]).*$")
        when {
            name.isEmpty() -> throw EmptyProjectNameException
            !validNameRegex.matches(name) -> throw InvalidProjectNameException
            else -> return name
        }
    }

    fun isValidProjectStates(states: List<String>): List<String> {
        val validStateRegex = Regex("^[a-zA-Z]+( [a-zA-Z]+)*$")

        if (states.isEmpty()) throw EmptyProjectStatesException
        states.forEach { state ->
            if (!validStateRegex.matches(state)) {
                throw InvalidStateNameException
            }
        }
        return states
    }
}