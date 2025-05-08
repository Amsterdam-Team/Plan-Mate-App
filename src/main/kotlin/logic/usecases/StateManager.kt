package logic.usecases

import logic.entities.User
import logic.exception.PlanMateException.AuthorizationException.UnAuthenticatedException

object StateManager {
    private var currentUser : User? = null

    fun setLoggedInUser(user: User){
        currentUser = user
    }

    fun getLoggedInUser(): User{
        return currentUser ?: throw UnAuthenticatedException
    }

    fun logOut() {
        currentUser = null
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}