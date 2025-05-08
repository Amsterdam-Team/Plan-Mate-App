package data.statemanagement

import logic.entities.Project
import logic.entities.Task
import logic.entities.User
import logic.exception.PlanMateException

object StateManager: StateManagerInterface {
    private var currentUser: User? = null
    private var currentProject: Project? = null
    private var currentTask: Task? = null

    override fun getCurrentUser(): User {
        return currentUser ?: throw PlanMateException.AuthorizationException.UserNotFoundException
    }

    override fun setCurrentUser(user: User) {
        currentUser = user
    }

    override fun getCurrentProject(): Project {
        return currentProject ?: throw PlanMateException.NotFoundException.ProjectNotFoundException
    }

    override fun setCurrentProject(project: Project) {
        currentProject = project
    }

    override fun getCurrentTask(): Task {
        return currentTask ?: throw PlanMateException.NotFoundException.TaskNotFoundException

    }

    override fun setCurrentTask(task: Task) {
        currentTask = task
    }

    override fun logOut() {
        currentUser = null
        currentProject = null
        currentTask = null
    }
}