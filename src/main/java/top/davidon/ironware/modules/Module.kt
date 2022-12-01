package top.davidon.ironware.modules

import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard
import top.davidon.ironware.notifications.Notification
import top.davidon.ironware.notifications.NotificationManager
import top.davidon.ironware.notifications.NotificationType
import top.davidon.ironware.settings.SettingList

abstract class Module(var name: String, var category: ModuleCategory) {
    var enabled = false
    var key = Keyboard.KEY_NONE
    var mc = Minecraft.getMinecraft()
    var hidden = false
    var settings = SettingList()

    protected abstract fun onEnable()

    private fun onEnableP() {
        NotificationManager.show(Notification(NotificationType.INFO, "Enabled", "Module $name", 1))
        onEnable()
    }

    protected abstract fun onDisable()

    private fun onDisableP() {
        NotificationManager.show(Notification(NotificationType.INFO, "Disabled", "Module $name", 1))
        onDisable()
    }

    fun bind(keyCode: Int) {
        this.key = keyCode
        NotificationManager.show(Notification(NotificationType.INFO, "Bound", "Module $name bound to $key", 1))
    }

    fun setState(enabled: Boolean) {
        this.enabled = enabled
        if (this.enabled) {
            onEnableP()
        } else {
            onDisableP()
        }
    }

    fun toggle() {
        enabled = !enabled
        if (enabled) {
            onEnableP()
        } else {
            onDisableP()
        }
    }
}