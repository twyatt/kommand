package com.juul.kommand

fun main(args: Array<String>) {
    if (args.isEmpty()) printCommands()
    Kommand().main(args)
}
