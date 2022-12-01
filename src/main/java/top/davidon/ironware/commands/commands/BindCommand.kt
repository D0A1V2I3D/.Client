package top.davidon.ironware.commands.commands

import org.lwjgl.input.Keyboard
import top.davidon.ironware.Client
import top.davidon.ironware.commands.Command
import top.davidon.ironware.modules.Module
import top.davidon.ironware.notifications.Notification
import top.davidon.ironware.notifications.NotificationManager
import top.davidon.ironware.notifications.NotificationType
import java.util.*

class BindCommand : Command("bind", "Binds module to a key", "bind <module name> <key> | none", listOf("b", "bind")) {
    override fun onCommand(args: Array<String>, command: String) {
        if (args.size == 2) {
            var argkey = Keyboard.getKeyIndex(args[1].uppercase(Locale.getDefault()))
            val mopt: Optional<Module> = Client.moduleManager.getModuleByName(args[0])
            if (!mopt.isPresent()) {
                NotificationManager.show(
                    Notification(
                        NotificationType.ERROR,
                        "Bind failed",
                        "Failed to find module",
                        3
                    )
                )
                return
            }
            val m: Module = mopt.get()
            if (args[1].equals("none", ignoreCase = true)) {
                argkey = 0
            }
            m.bind(argkey)
        } else {
            NotificationManager.show(Notification(NotificationType.ERROR, "Invalid syntax", syntax, 3))
        }
    }
}