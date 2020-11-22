package com.juul.kommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class Kommand : CliktCommand() {

    private val command by argument()
    private val dryRun by option("-d", "--dry-run").flag()

    override fun run() {
        val projectName = currentDirectoryPath.substringAfterLast("/")
        val project = config.projects[projectName]
        val globalCommand = config.global?.get(command)

        if (project != null) {
            project.execute(
                command.let {
                    project.commands[it]
                        ?: globalCommand
                        ?: error("Command '$it' not found in project")
                },
                dryRun
            )
        } else {
            // Attempt to run as global command
            globalCommand
                // Global command not found, so we assume user was trying to run a project command.
                ?: error("Project '$projectName' not found in config")

            execute(globalCommand, dryRun)
        }
    }
}

fun Config.path(project: Project) = "$homeDirectory/${project.name}"

/** Execute `global` [Command]. */
fun execute(command: Command, dryRun: Boolean) {
    command.dependencies?.forEach { execute(it, dryRun) }
    if (!dryRun) println()
    println("🏃 ${command.run}")
    if (!dryRun) execute(currentDirectoryPath, command.run)
}

fun Project.execute(command: Command, dryRun: Boolean) {
    command.dependencies?.forEach { execute(it, dryRun) }
    if (!dryRun) println()
    println("🏃 $name ▶ ${command.run}")
    if (!dryRun) execute(config.path(this), command.run)
}

fun execute(dependency: Dependency, dryRun: Boolean) {
    val projectName = dependency.project
    val commandName = dependency.command

    val project = config.projects[projectName]
        ?: error("Project '$projectName' not found in config")
    val command = project.commands[commandName]
        ?: error("Command '$commandName' not found in project '$projectName'")

    project.execute(command, dryRun)
}
