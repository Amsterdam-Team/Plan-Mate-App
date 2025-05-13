package ui.utils

import logic.entities.Project
import logic.entities.Task


fun printSwimlanesView(
    project: Project,
) {


    val lanes = project.states
    val data = project.tasks
    val taskFormatter: (Task) -> String = { it.name }

    val columnWidth = data.maxOfOrNull { taskFormatter(it).length } ?: 10

    val laneMap = lanes.associateWith { lane ->
        data.filter { it.state == lane }
    }

    val maxTasks = laneMap.values.maxOfOrNull { it.size } ?: 0

    println("\nProject: ${project.name} \n")

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
