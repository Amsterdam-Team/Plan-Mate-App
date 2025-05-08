package data.statemanagement

import logic.entities.Project
import logic.entities.Task
import logic.entities.User

object StateManager: StateManagerInterface {
    private var currentUser: User? = null
    private var currentProject: Project? = null
    private var currentTask: Task? = null

    override fun getCurrentUser(): User {
        return currentUser ?: throw Exception("No current user")
    }

    override fun setCurrentUser(user: User) {
        currentUser = user
    }

    override fun getCurrentProject(): Project {
        return currentProject ?: throw Exception("No current user")
    }

    override fun setCurrentProject(project: Project) {
        currentProject = project
    }

    override fun getCurrentTask(): Task {
        return currentTask ?: throw Exception("No current user")

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