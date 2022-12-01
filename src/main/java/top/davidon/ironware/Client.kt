@file:JvmName("Client")
package top.davidon.ironware

import com.darkmagician6.eventapi.EventManager
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator
import org.lwjgl.opengl.Display
import top.davidon.ironware.commands.CommandManager
import top.davidon.ironware.modules.ModuleManager
import top.davidon.ironware.ui.alts.AccountManager
import top.davidon.ironware.ui.clicgui.ClickGuiScreen
import top.davidon.ironware.utils.Utils
import viamcp.ViaMCP

class Client {
    companion object {
        @JvmStatic var name = ".Client"
        @JvmStatic var version = "0.0.1"
        @JvmStatic lateinit var moduleManager: ModuleManager
        @JvmStatic lateinit var commandManager: CommandManager
        @JvmStatic lateinit var microshit: MicrosoftAuthenticator
        @JvmStatic lateinit var clickGui: ClickGuiScreen
        @JvmStatic lateinit var accountManager: AccountManager

        @JvmStatic
        fun init() {
            //Utils.acceptsJavaAgentsAttachingToThisJvm()
            Utils.logger.info("Pre menu init: Starting $name-$version")

            microshit = MicrosoftAuthenticator()
            accountManager = AccountManager()

            Utils.logger.info("Loading ViaMCP-Reborn")
            try {
                ViaMCP.getInstance().start()
                ViaMCP.getInstance().initAsyncSlider()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Utils.logger.info("Loaded ViaMCP-Reborn")

            Utils.logger.info("Pre menu init: Finished $name")
        }

        @JvmStatic
        fun postMenuInit() {
            Utils.logger.info("Post menu init: Loading $name bits")

            clickGui = ClickGuiScreen()
            moduleManager = ModuleManager()
            clickGui.moduleInit()
            commandManager = CommandManager()

            registerNeededEvents()
            Display.setTitle("$name v$version")

            Utils.logger.info("Post menu init: Finished $name bits")
        }

        private fun registerNeededEvents() {
            EventManager.register(moduleManager)
            EventManager.register(commandManager)
        }
    }
}