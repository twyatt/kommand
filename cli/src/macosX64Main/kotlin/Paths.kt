package com.juul.kommand

import platform.Foundation.NSFileManager
import platform.Foundation.homeDirectoryForCurrentUser

actual val currentDirectoryPath: String = NSFileManager.defaultManager.currentDirectoryPath

actual val homePath: String = NSFileManager.defaultManager.homeDirectoryForCurrentUser.path
    ?: error("Home directory unavailable")
