package logic.usecases

import logic.entities.User
import logic.exception.PlanMateException
import logic.exception.PlanMateException.AuthorizationException.UnAuthenticatedException

class StateManager {
    private var currentUser : User? = null

    fun login(user: User){
        currentUser = user
    }

    fun getLoggedInUser(): User{
        return currentUser ?: throw UnAuthenticatedException
    }
}