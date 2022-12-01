package top.davidon.ironware.commands

import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventChat
import top.davidon.ironware.notifications.Notification
import top.davidon.ironware.notifications.NotificationManager
import top.davidon.ironware.notifications.NotificationType
import top.davidon.ironware.commands.commands.*;
import top.davidon.ironware.utils.Utils
import java.util.*

class CommandManager {
    var commands: MutableList<Command> = ArrayList<Command>()
    var prefix = "."

    fun CommandManager() {
        addCommand(BindCommand())
        addCommand(SayCommand())
        addCommand(ToggleCommand())
        addCommand(ClientNameCommand())
    }

    @EventTarget
    fun onOutgoing(e: EventChat) {
        var message = e.message
        if (!message.startsWith(prefix)) return
        e.setCancelled(true)
        message = message.substring(prefix.length)
        var foundCommand = false
        if (message.split(" ").isNotEmpty()) {
            val commandName = message.split(" ")[0]
            for (c in commands) {
                Utils.logger.info("Cycled")
                if (c.aliases.contains(commandName)) {
                    c.onCommand(
                        message.split(" ").toTypedArray().copyOfRange(1, message.split(" ").toTypedArray().size), message)
                    foundCommand = true
                    break
                }
            }
        }
        if (!foundCommand) {
            NotificationManager.show(Notification(NotificationType.ERROR, "Command not found", "Cant find command", 3))
        }
    }

    fun addCommand(command: Command) {
        commands.add(command)
    }
}