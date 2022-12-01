package top.davidon.ironware.commands

abstract class Command(var name: String, var description: String, var syntax: String, var aliases: List<String>) {

    abstract fun onCommand(args: Array<String>, command: String)
}