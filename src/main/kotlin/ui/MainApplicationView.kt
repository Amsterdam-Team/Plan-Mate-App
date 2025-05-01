package ui

import logic.entities.User
import java.util.UUID

class MainApplicationView(
    val authenticationView: AuthenticationView,
    val adminView: AdminView,
    val userView: UserView
) {


    fun start() {
        authenticationView.showAuthCommands()
        val mainAppUser = authenticationView.handleAuthentication()
        if (mainAppUser == null) return

        if (mainAppUser.isAdmin) {
            adminView.showAdminCommands()
            adminView.handleAdminCommands()
        } else {
            userView.showUserCommands()
            userView.handleUserCommands()
        }


    }








}