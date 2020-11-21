package com.juul.kommand

fun main(args: Array<String>) {
    if (args.isEmpty()) printCommands()
    Kommand().main(args)
}

private fun printCommands() {
    val projectName = currentDirectoryPath.substringAfterLast("/")
    val project = config.projects[projectName] ?: return
    println("▼ $projectName COMMANDs")
    project.commands.keys.forEach { println("• $it") }
    println()
}
