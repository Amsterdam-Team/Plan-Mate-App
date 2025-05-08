package data.statemanagement

import logic.entities.Project
import logic.entities.Task
import logic.entities.User

interface StateManager {
    fun getCurrentUser(): User
    fun setCurrentUser(user:User)
    fun getCurrentProject(): Project
    fun setCurrentProject(project: Project)
    fun getCurrentTask(): Task
    fun setCurrentTask(task: Task)
}