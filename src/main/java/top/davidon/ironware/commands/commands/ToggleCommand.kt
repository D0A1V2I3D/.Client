package top.davidon.ironware.commands.commands

import top.davidon.ironware.Client
import top.davidon.ironware.commands.Command
import top.davidon.ironware.modules.Module
import top.davidon.ironware.notifications.Notification
import top.davidon.ironware.notifications.NotificationManager
import top.davidon.ironware.notifications.NotificationType
import java.util.*

class ToggleCommand : Command("Toggle", "Toggles modules", "toggle <module name>", listOf("t", "toggle")) {
    override fun onCommand(args: Array<String>, command: String) {
        if (args.size == 1) {
            val moduleName = args[0].lowercase(Locale.getDefault())
            val mopt: Optional<Module> = Client.moduleManager.getModuleByName(moduleName)
            if (!mopt.isPresent()) {
                NotificationManager.show(
                    Notification(
                        NotificationType.ERROR,
                        "Toggle failed",
                        "Failed to find module",
                        3
                    )
                )
                return
            }
            val m: Module = mopt.get()
            m.toggle()
        } else {
            NotificationManager.show(Notification(NotificationType.ERROR, "Invalid syntax", syntax, 3))
        }
    }
}