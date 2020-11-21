package com.juul.kommand

import platform.Foundation.NSString
import platform.Foundation.stringWithContentsOfFile

actual fun read(
    path: String
): String? = NSString.stringWithContentsOfFile(path) as? String
