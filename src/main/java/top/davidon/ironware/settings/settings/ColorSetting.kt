package top.davidon.ironware.settings.settings

import top.davidon.ironware.modules.Module
import top.davidon.ironware.settings.Setting
import top.davidon.ironware.settings.SettingType
import java.awt.Color

class ColorSetting(override var name: String, override var id: Int, override var value: Color) :
    Setting<Color>(name, id, SettingType.COLOR, value) {
}