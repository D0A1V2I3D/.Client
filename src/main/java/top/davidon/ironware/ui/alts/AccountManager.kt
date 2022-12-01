package top.davidon.ironware.ui.alts

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import top.davidon.ironware.ui.menu.Menu

class AccountManager {
    var accounts: MutableList<Account> = ArrayList()
    var altScreen: AltScreen = AltScreen(Menu())
    var pwd = ""

}