package top.davidon.ironware.modules.render

import org.lwjgl.input.Keyboard
import top.davidon.ironware.Client
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.settings.settings.NumberRangeSetting

class ClickGui : Module("ClickGui", ModuleCategory.RENDER) {
    var outlines = BooleanSetting("Outlines", 0, true)
    var seperator = BooleanSetting("Separator", 1, true)
    var borderWidth = NumberRangeSetting("Outline width", 2, 2.0, 0.0, 10.0, 1.0)
    var separatorHeight = NumberRangeSetting("Separator height", 3, 2.0, 0.0, 10.0, 1.0)

    init {
        this.bind(Keyboard.KEY_RSHIFT)
        settings.addSetting(outlines)
        settings.addSetting(seperator)
        settings.addSetting(borderWidth)
        settings.addSetting(separatorHeight)
    }

    override fun onEnable() {
        mc.displayGuiScreen(Client.clickGui)
        this.toggle()
    }

    override fun onDisable() {
    }
}