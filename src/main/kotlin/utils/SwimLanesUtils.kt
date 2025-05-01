package utils

import logic.entities.Project
import logic.entities.Task
import kotlin.collections.filter
import kotlin.collections.getOrNull
import kotlin.collections.maxOf

class SwimLanesUtils {
    fun showTasks(tasks: List<Task>){
        val allStates = tasks.map{
            it.state
        }
        val taskFormatter: (Task) -> String = { "${it.id} - ${it.name}" }
        val columnWidth = tasks.maxOf { taskFormatter(it).length }
        val stateMap = allStates.associateWith { state ->
            tasks.filter { it.state == state }
        }
        val maxTasks = stateMap.values.maxOfOrNull { it.size } ?: 0


        println(allStates.joinToString(" | ") { it.padEnd(columnWidth) })
        println("-".repeat((columnWidth + 3) * allStates.size - 3))

        for (i in 0 until maxTasks) {
            val row = allStates.joinToString(" | ") { lane ->
                stateMap[lane]?.getOrNull(i)?.let { taskFormatter(it) }?.padEnd(columnWidth)
                    ?: "".padEnd(columnWidth)
            }
            println(row)
        }

        println("\n")
    }
    fun showProject(project : Project){
        val lanes = project.states
        val data = project.tasks
        val taskFormatter: (Task) -> String = { "${it.id} - ${it.name}" }
        val columnWidth = data.maxOf { taskFormatter(it).length }
        val laneMap = lanes.associateWith { lane ->
            data.filter { it.state == lane }
        }

        val maxTasks = laneMap.values.maxOfOrNull { it.size } ?: 0

        println("\nProject: ${project.name} [ID : ${project.id} ]\n")

        println(lanes.joinToString(" | ") { it.padEnd(columnWidth) })
        println("-".repeat((columnWidth + 3) * lanes.size - 3))

        for (i in 0 until maxTasks) {
            val row = lanes.joinToString(" | ") { lane ->
                laneMap[lane]?.getOrNull(i)?.let { taskFormatter(it) }?.padEnd(columnWidth)
                    ?: "".padEnd(columnWidth)
            }
            println(row)
        }

        println("\n")
    }

    fun showDetailedProjects(projects: List<Project>){
        projects.forEach {
            showProject(it)
        }
    }

    // TODO: need further improvement to be aligned with swimlanes format
    fun showListOfProjects(projects: List<Project>){
        projects.forEachIndexed {
                index, it  -> println("${index+1}) ${it.name}")
        }
    }
}