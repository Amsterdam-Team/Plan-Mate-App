package ui.menuHandler

import ui.controller.BaseUIController


class AdminMenuHandler(private val adminFeatureControllers: Map<Int, BaseUIController>) : MainMenuHandler() {

    public override suspend fun start() {
        super.start()
        baseMenuStart(
            showMenu = ::showAdminMenu,
            featureControllers = adminFeatureControllers
        )
    }

    private fun showAdminMenu() {
        println(
            """
    ================================
    Admin Main Menu
    ================================
    1. View All Projects
    2. Create Mate User
    0. Exit
    """.trimIndent()
        )
//        println(
//            """
//    ================================
//    Admin Main Menu
//    ================================
//    1. View All Projects
////    2. View Project Audit History
////    3. View Task Audit History
////    4. Create Mate User
////    5. View State
////    6. Create State
////    7. Edit State
////    8. Delete State
//    0. Exit
//    """.trimIndent()
//        )
    }

}