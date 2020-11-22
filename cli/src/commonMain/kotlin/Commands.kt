package com.juul.kommand

fun printCommands() {
    config.projects[currentDirectoryPath.substringAfterLast("/")]?.run {
        println("▼ $name COMMANDs")
        commands.keys.forEach { println("• $it") }
    }
    config.global?.keys?.run {
        println("▼ Global COMMANDs")
        forEach { println("• $it") }
    }
    println()
}
