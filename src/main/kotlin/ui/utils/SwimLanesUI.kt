package ui.utils

import logic.entities.Project
import logic.entities.Task


fun printSwimlanesView(
    projects: List<Project>,
) {
    for ((projectIndex, project) in projects.withIndex()) {
        val lanes = project.states
        val data = project.tasks
        val taskFormatter: (Task, Int) -> String = { task, taskIndex -> "${taskIndex + 1} - ${task.name}" }

        val columnWidth = data.maxOfOrNull { taskFormatter(it, 0).length } ?: 10

        val laneMap = lanes.associateWith { lane ->
            data.filter { it.state == lane }
        }

        val maxTasks = laneMap.values.maxOfOrNull { it.size } ?: 0

        println("\nProject: ${project.name} [Index: ${projectIndex + 1}]\n")

        println(lanes.joinToString(" | ") { it.padEnd(columnWidth) })
        println("-".repeat((columnWidth + 3) * lanes.size - 3))

        for (i in 0 until maxTasks) {
            val row = lanes.joinToString(" | ") { lane ->
                laneMap[lane]?.getOrNull(i)?.let { taskFormatter(it, data.indexOf(it)) }?.padEnd(columnWidth)
                    ?: "".padEnd(columnWidth)
            }
            println(row)
        }

        println("\n")
    }
}
