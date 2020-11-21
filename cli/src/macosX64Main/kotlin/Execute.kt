package com.juul.kommand

import kotlinx.cinterop.autoreleasepool
import platform.Foundation.NSPipe
import platform.Foundation.NSTask
import platform.Foundation.currentDirectoryPath
import platform.Foundation.launch
import platform.Foundation.launchPath
import platform.Foundation.waitUntilExit

actual fun execute(path: String, command: String) {
    autoreleasepool {
        val stdin = NSPipe()
        val task = NSTask()
        task.currentDirectoryPath = path
        task.launchPath = "/bin/sh"
        task.arguments = listOf("-c", command)
        task.standardInput = stdin
        task.launch()
        task.waitUntilExit()
    }
}
