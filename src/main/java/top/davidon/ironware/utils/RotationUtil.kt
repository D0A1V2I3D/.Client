package top.davidon.ironware.utils

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.BlockPos
import net.minecraft.util.MathHelper
import net.minecraft.util.Vec3
import java.util.concurrent.ThreadLocalRandom

class RotationUtil {
    companion object {
        private val serverAngles = FloatArray(2)
        private val mc = Minecraft.getMinecraft()

        fun getVectorForRotation(pitch: Float, yaw: Float): Vec3 {
            val f = MathHelper.cos((Math.toRadians(-yaw.toDouble()) - Math.PI.toFloat()).toFloat())
            val f1 = MathHelper.sin((Math.toRadians(-yaw.toDouble()) - Math.PI.toFloat()).toFloat())
            val f2 = -MathHelper.cos(Math.toRadians(-pitch.toDouble()).toFloat())
            val f3 = MathHelper.sin(Math.toRadians(-pitch.toDouble()).toFloat())
            return Vec3((f1 * f2).toDouble(), f3.toDouble(), (f * f2).toDouble())
        }

        fun getPositionEyes(partialTicks: Float): Vec3 {
            return Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.eyeHeight.toDouble(), mc.thePlayer.posZ)
        }

        fun getYawChange(posX: Double, posZ: Double): Float {
            val player = mc.thePlayer
            val deltaX = posX - player.posX
            val deltaZ = posZ - player.posZ
            val yawToEntity: Double
            val v1 = Math.toDegrees(Math.atan(deltaZ / deltaX))
            yawToEntity = if (deltaZ < 0.0 && deltaX < 0.0) {
                90.0 + v1
            } else if (deltaZ < 0.0 && deltaX > 0.0) {
                -90.0 + v1
            } else {
                Math.toDegrees(-Math.atan(deltaX / deltaZ))
            }
            return MathHelper.wrapAngleTo180_float(-(player.rotationYaw - yawToEntity.toFloat()))
        }

        fun getRotations(posX: Double, posY: Double, posZ: Double): FloatArray? {
            val player = mc.thePlayer
            val x = posX - player.posX
            val y = posY - (player.posY + player.eyeHeight.toDouble())
            val z = posZ - player.posZ
            val dist = MathHelper.sqrt_double(x * x + z * z).toDouble()
            val yaw = (Math.atan2(z, x) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = -(Math.atan2(y, dist) * 180.0 / Math.PI).toFloat()
            return floatArrayOf(yaw, pitch)
        }

        fun getRotationsEntity(entity: EntityLivingBase): FloatArray? {
            return if (mc.thePlayer.isMoving()) getRotations(
                entity.posX + ThreadLocalRandom.current().nextDouble(0.03, -0.03),
                entity.posY + entity.eyeHeight.toDouble() - 0.4 + ThreadLocalRandom.current().nextDouble(0.07, -0.07),
                entity.posZ + ThreadLocalRandom.current().nextDouble(0.03, -0.03)
            ) else getRotations(entity.posX, entity.posY + entity.eyeHeight.toDouble() - 0.4, entity.posZ)
        }

        fun getYawToPoint(posX: Double, posZ: Double): Float {
            val instance = mc
            val xDiff =
                posX - (instance.thePlayer.lastTickPosX + (instance.thePlayer.posX - instance.thePlayer.lastTickPosX) * instance.timer.elapsedPartialTicks)
            val zDiff =
                posZ - (instance.thePlayer.lastTickPosZ + (instance.thePlayer.posZ - instance.thePlayer.lastTickPosZ) * instance.timer.elapsedPartialTicks)
            val dist = MathHelper.sqrt_double(
                xDiff * xDiff + zDiff * zDiff
            ).toDouble()
            return (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI).toFloat() - 90.0f
        }

        fun getPitchChange(entity: Entity, posY: Double): Float {
            val deltaX = entity.posX - mc.thePlayer.posX
            val deltaZ = entity.posZ - mc.thePlayer.posZ
            val deltaY = posY - 2.2 + entity.eyeHeight.toDouble() - mc.thePlayer.posY
            val distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ).toDouble()
            val pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ))
            return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - pitchToEntity.toFloat()) - 2.5f
        }

        fun getRotationFromPosition(x: Double, z: Double, y: Double): FloatArray {
            val xDiff = x - mc.thePlayer.posX
            val zDiff = z - mc.thePlayer.posZ
            val yDiff = y - mc.thePlayer.posY - 1.2
            val dist = MathHelper.sqrt_double(
                xDiff * xDiff + zDiff * zDiff
            ).toDouble()
            val yaw = (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = -(Math.atan2(
                yDiff,
                dist
            ) * 180.0 / Math.PI).toFloat()
            return floatArrayOf(yaw, pitch)
        }

        fun aimAtLocation(positionX: Double, positionY: Double, positionZ: Double): FloatArray {
            val x = positionX - mc.thePlayer.posX
            // @off
            val y = positionY - mc.thePlayer.posY
            val z = positionZ - mc.thePlayer.posZ
            val distance = MathHelper.sqrt_double(x * x + z * z).toDouble() // @on
            return floatArrayOf(
                (Math.atan2(z, x) * 180.0 / Math.PI).toFloat() - 90.0f,
                -(Math.atan2(y, distance) * 180.0 / Math.PI).toFloat()
            )
        }

        fun getAngles(entity: EntityLivingBase?): FloatArray? {
            if (entity == null) return null
            val player = mc.thePlayer
            val diffX = entity.posX - player.posX
            val diffY = entity.posY + entity.eyeHeight * 0.9 - (player.posY + player.eyeHeight)
            val diffZ = entity.posZ - player.posZ
            val dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ).toDouble() // @on
            val yaw = (Math.atan2(diffZ, diffX) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = -(Math.atan2(diffY, dist) * 180.0 / Math.PI).toFloat()
            return floatArrayOf(
                player.rotationYaw + MathHelper.wrapAngleTo180_float(
                    yaw - player.rotationYaw
                ), player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch)
            )
        }

        fun getAngles(pos: BlockPos): FloatArray {
            val posX = pos.x.toDouble()
            val posY = pos.y.toDouble()
            val posZ = pos.z.toDouble()
            val player = mc.thePlayer
            val diffX = posX - player.posX
            val diffY = posY - (player.posY + player.eyeHeight)
            val diffZ = posZ - player.posZ
            val dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ).toDouble() // @on
            val yaw = (Math.atan2(diffZ, diffX) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = -(Math.atan2(diffY, dist) * 180.0 / Math.PI).toFloat()
            return floatArrayOf(
                player.rotationYaw + MathHelper.wrapAngleTo180_float(
                    yaw - player.rotationYaw
                ), player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch)
            )
        }

        fun getAngles(vector: Vec3): FloatArray {
            val posX: Double = vector.xCoord
            val posY: Double = vector.yCoord
            val posZ: Double = vector.zCoord
            val player = mc.thePlayer
            val diffX = posX - player.posX
            val diffY = posY - (player.posY + player.eyeHeight)
            val diffZ = posZ - player.posZ
            val dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ).toDouble() // @on
            val yaw = (Math.atan2(diffZ, diffX) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = -(Math.atan2(diffY, dist) * 180.0 / Math.PI).toFloat()
            return floatArrayOf(
                player.rotationYaw + MathHelper.wrapAngleTo180_float(
                    yaw - player.rotationYaw
                ), player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch)
            )
        }

        fun getNewAngle(angle: Float): Float {
            var angle = angle
            angle %= 360.0f
            if (angle >= 180.0f) {
                angle -= 360.0f
            }
            if (angle < -180.0f) {
                angle += 360.0f
            }
            return angle
        }

        fun getDistanceBetweenAngles(angle1: Float, angle2: Float): Float {
            val angle = Math.abs(angle1 - angle2) % 360.0f
            return if (angle > 180.0f) 360.0f - angle else angle
        }

        fun getDistance(x: Double, z: Double, y: Double): DoubleArray {
            val distance = MathHelper.sqrt_double(x * x + z * z).toDouble()
            val yaw = Math.atan2(z, x) * 180.0 / Math.PI - 90.0f
            val pitch = -(Math.atan2(y, distance) * 180.0 / Math.PI)
            return doubleArrayOf(
                (mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((yaw - mc.thePlayer.rotationYaw).toFloat())).toDouble(),
                (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((pitch - mc.thePlayer.rotationPitch).toFloat())).toDouble()
            )
        }
    }
}