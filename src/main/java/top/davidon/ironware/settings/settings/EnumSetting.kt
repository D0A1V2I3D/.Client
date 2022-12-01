package top.davidon.ironware.settings.settings

import top.davidon.ironware.modules.Module
import top.davidon.ironware.settings.Setting
import top.davidon.ironware.settings.SettingType

class EnumSetting<T : Enum<*>>(override var name: String, override var id: Int, override var value: T) :
    Setting<T>(name, id, SettingType.ENUM, value) {}