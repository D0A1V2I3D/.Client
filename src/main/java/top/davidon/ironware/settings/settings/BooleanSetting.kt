package top.davidon.ironware.settings.settings

import top.davidon.ironware.modules.Module
import top.davidon.ironware.settings.Setting
import top.davidon.ironware.settings.SettingType

class BooleanSetting(override var name: String, override var id: Int, override var value: Boolean) :
    Setting<Boolean>(name, id, SettingType.BOOLEAN, value) {}