package top.davidon.ironware.modules

import com.darkmagician6.eventapi.EventManager
import com.darkmagician6.eventapi.EventTarget
import top.davidon.ironware.events.EventKey
import top.davidon.ironware.modules.combat.*
import top.davidon.ironware.modules.ghost.*
import top.davidon.ironware.modules.movement.*
import top.davidon.ironware.modules.player.*
import top.davidon.ironware.modules.render.*
import top.davidon.ironware.utils.Utils
import java.util.*

class ModuleManager {
    var modules: MutableList<Module> = ArrayList()

    init {
        addModule(NoMissDelay())
        addModule(Velocity())
        addModule(Sprint())
        addModule(AutoJump())
        addModule(FastPlace())
        addModule(NoFall())
        addModule(Crosshair())
        addModule(Fullbright())
        addModule(TabGui())
        addModule(Watermark())
        addModule(ClickGui())
        addModule(Tracers())
        addModule(ChestESP())
        addModule(PlayerESP())
        addModule(Autoclicker())
        addModule(AimAssist())

        registerModules()
    }

    private fun registerModules() {
        for (m in modules) {
            EventManager.register(m)
        }
    }

    fun addModule(m: Module) {
        modules.add(m)
    }

    @EventTarget
    fun keyEventToggle(event: EventKey) {
        for (m in modules) {
            if (m.key == event.key) {
                m.toggle()
            }
        }
    }

    fun getModulesByCategory(c: ModuleCategory): List<Module> {
        val modules1: MutableList<Module> = java.util.ArrayList()
        for (m in modules) {
            if (m.category === c) {
                modules1.add(m)
            }
        }
        return modules1
    }

    fun getModuleByName(name: String): Optional<Module> {
        for (m in modules) {
            if (m.name == name) {
                return Optional.of(m)
            }
        }
        return Optional.empty()
    }
}