package top.davidon.ironware.notifications

import java.util.concurrent.LinkedBlockingQueue

class NotificationManager {
    companion object {
        private val pendingNotifications = LinkedBlockingQueue<Notification>()
        private var currentNotification: Notification? = null
        var disableShowing = false

        fun show(notification: Notification) {
//        if (Minecraft.getMinecraft().currentScreen != null)
            if (disableShowing) {
                return
            }
            pendingNotifications.add(notification)
        }

        fun update() {
            if (currentNotification != null && !currentNotification!!.isShown()) {
                currentNotification = null
            }
            if (currentNotification == null && !pendingNotifications.isEmpty()) {
                currentNotification = pendingNotifications.poll()
                currentNotification?.show()
            }
        }

        @JvmStatic
        fun render() {
            update()
            if (currentNotification != null) currentNotification!!.render()
        }
    }
}