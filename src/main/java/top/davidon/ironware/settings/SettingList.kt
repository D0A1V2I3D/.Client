package top.davidon.ironware.settings

import java.util.*

class SettingList: Iterable<Setting<*>> {
    private val settings: MutableList<Setting<*>> = ArrayList()

    fun addSetting(setting: Setting<*>) {
        settings.add(setting)
    }

    fun getSettingByName(name: String): Optional<Setting<*>> {
        for (s in settings) {
            if (s.name == name) {
                return Optional.of(s)
            }
        }
        return Optional.empty()
    }

    fun getSettingById(id: Int): Optional<Setting<*>> {
        for (s in settings) {
            if (s.id == id) {
                return Optional.of(s)
            }
        }
        return Optional.empty()
    }

    fun isEmpty(): Boolean {
        return settings.isEmpty()
    }

    override fun iterator(): MutableIterator<Setting<*>> {
        return settings.iterator()
    }
}