package top.davidon.ironware.modules.ghost

import com.darkmagician6.eventapi.EventTarget
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import org.lwjgl.input.Mouse
import top.davidon.ironware.events.EventMotion
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.settings.settings.NumberRangeSetting
import top.davidon.ironware.utils.RotationUtil
import top.davidon.ironware.utils.Timer
import java.util.stream.Collectors

class AimAssist : Module("AimAssist", ModuleCategory.GHOST) {
    var clickAim = BooleanSetting("Click Aim", 0, true)
    var aimDelay = NumberRangeSetting("Aim delay (MS)", 1, 10.0, 0.0, 100.0, 5.0)
    var horizontalSpeed = NumberRangeSetting("Horizontal speed", 2,  5.0, 0.0, 15.0, 1.0)
    var verticalSpeed = NumberRangeSetting("Vertical speed", 3,  0.0, 0.0, 15.0, 1.0)

    var yaw: Float? = null
    var pitch: Float? = null
    var target: Entity? = null
    var targets: MutableList<Entity> = ArrayList()
    var angleComparator: Comparator<Entity> = Comparator.comparingDouble {entity: Entity -> getRotationsNeeded(entity)?.get(0) ?: 0.0 }
    var index: Int = 0
    var timer = Timer()


    init {
        settings.addSetting(clickAim)
        settings.addSetting(aimDelay)
        settings.addSetting(horizontalSpeed)
        settings.addSetting(verticalSpeed)
    }

    @EventTarget
    fun onEvent(e: EventMotion) {
        if (enabled) {
            targets = this.loadTargets()
            targets.sortedWith(angleComparator)

            if (target is EntityMob || target is EntityAnimal) {
                target = null
            }

            if (this.mc.thePlayer.ticksExisted % 50 == 0 && targets.size > 1) ++this.index
            if (clickAim.get() && !Mouse.isButtonDown(0)) return

            if (!targets.isEmpty()) {
                if (this.index >= targets.size) this.index = 0

                target = targets[this.index]
                val rotationPosition = getRotationsNeeded(target)

                if (this.timer.delay(aimDelay.get())) {
                    pitch = ((rotationPosition?.get(1))?.plus(0))?.toFloat() ?: mc.thePlayer.rotationPitch
                    yaw = rotationPosition?.get(0)?.toFloat() ?: mc.thePlayer.rotationYaw

                    this.timer.reset()
                }

                //final boolean notOnlyAxeSword = !this.onlyAxeSword.get();

//                if (this.rayTrace.get()) {
//                    if (this.mc.player.canEntityBeSeen(target) && (notOnlyAxeSword || this.mc.player
//                            .getCurrentEquippedItem().getItem() instanceof ItemSword || this.mc.player
//                            .getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
//                        aim();
//                    }
//                } else if (notOnlyAxeSword) {
//                    aim();
//                } else if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword || this.mc.thePlayer
//                        .getCurrentEquippedItem().getItem() instanceof ItemAxe) {
//                    aim();

                if (mc.currentScreen == null) {
                    aim()
                }

            }
        }
    }

    private fun aim() {
        val yaw = mc.thePlayer.rotationYaw
        val pitch = mc.thePlayer.rotationPitch
        if (yaw < yaw) {
            mc.thePlayer.rotationYaw += horizontalSpeed.get().toFloat()
        } else if (mc.thePlayer.rotationYaw > yaw) {
            mc.thePlayer.rotationYaw -= horizontalSpeed.get().toFloat()
        }
        if (pitch < pitch) {
            mc.thePlayer.rotationPitch += verticalSpeed.get().toFloat()
        } else if (mc.thePlayer.rotationPitch > pitch) {
            mc.thePlayer.rotationPitch -= verticalSpeed.get().toFloat()
        }
    }

    private fun loadTargets(): ObjectArrayList<Entity> {
        return mc.theWorld.getLoadedEntityList().stream()
            .filter { e -> this.mc.thePlayer.getDistanceToEntity(e) <= 3.2 && this.qualifies(e) }
            .sorted(Comparator.comparingDouble<Entity> { o -> o.getDistanceToEntity(this.mc.thePlayer).toDouble() }.reversed())
            .collect(Collectors.toCollection { ObjectArrayList() })
    }

    private fun qualifies(entity: Entity): Boolean {
        return entity !== mc.thePlayer && entity.isEntityAlive && entity is EntityPlayer
    }

    private fun getRotationsNeeded(entity: Entity?): DoubleArray? {
        if (entity == null) return null
        val player = mc.thePlayer
        val diffX = entity.posX - player.posX
        val diffY = (entity as EntityLivingBase).posY + entity.getEyeHeight() * 0.9 - (player.posY + player.eyeHeight)
        val diffZ = entity.posZ - player.posZ
        return RotationUtil.getDistance(diffX, diffZ, diffY)
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}