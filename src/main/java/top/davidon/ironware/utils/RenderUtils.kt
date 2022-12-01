package top.davidon.ironware.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraft.util.MathHelper
import org.lwjgl.opengl.GL11
import java.awt.Color

class RenderUtils {
    companion object {
        var healthPercent = 0.0
        private val mc = Minecraft.getMinecraft()
        private const val lastP = 0.0
        private var diffP:kotlin.Double = 0.0

        private fun RenderUtils() {
            throw UnsupportedOperationException("This is a utility class and cannot be instantiated")
        }

        fun setupRender(start: Boolean) {
            if (start) {
                GlStateManager.enableBlend()
                GL11.glEnable(2848)
                GlStateManager.disableDepth()
                GlStateManager.disableTextures()
                GlStateManager.blendFunc(770, 771)
                GL11.glHint(3154, 4354)
            } else {
                GlStateManager.disableBlend()
                GlStateManager.enableTextures()
                GL11.glDisable(2848)
                GlStateManager.enableDepth()
            }
            GlStateManager.depthMask(!start)
        }


        fun drawSolidBlockESP(x: Double, y: Double, z: Double, color: Int) {
            val xPos: Double = x - mc.getRenderManager().renderPosX
            val yPos: Double = y - mc.getRenderManager().renderPosY
            val zPos: Double = z - mc.getRenderManager().renderPosZ
            val f = (color shr 16 and 0xFF).toFloat() / 255.0f
            val f2 = (color shr 8 and 0xFF).toFloat() / 255.0f
            val f3 = (color and 0xFF).toFloat() / 255.0f
            val f4 = (color shr 24 and 0xFF).toFloat() / 255.0f
            GL11.glPushMatrix()
            GL11.glEnable(3042)
            GL11.glBlendFunc(770, 771)
            GL11.glDisable(3553)
            GL11.glDisable(2929)
            GL11.glDepthMask(false)
            GL11.glLineWidth(1.0f)
            GL11.glColor4f(f, f2, f3, f4)
            drawOutlinedBoundingBox(
                AxisAlignedBB(
                    xPos,
                    yPos,
                    zPos,
                    xPos + 1.0,
                    yPos + 1.0,
                    zPos + 1.0
                )
            )
            GL11.glColor3f(1.0f, 1.0f, 1.0f)
            GL11.glEnable(3553)
            GL11.glEnable(2929)
            GL11.glDepthMask(true)
            GL11.glDisable(3042)
            GL11.glPopMatrix()
        }

        fun drawSolidBlockESP(pos: BlockPos, color: Int) {
            val xPos: Double = pos.x - mc.getRenderManager().renderPosX
            val yPos: Double = pos.y - mc.getRenderManager().renderPosY
            val zPos: Double = pos.z - mc.getRenderManager().renderPosZ
            val height: Double = mc.theWorld.getBlockState(pos).getBlock()
                .getBlockBoundsMaxY() - mc.theWorld.getBlockState(pos).getBlock()
                .getBlockBoundsMinY()
            val f = (color shr 16 and 0xFF).toFloat() / 255.0f
            val f2 = (color shr 8 and 0xFF).toFloat() / 255.0f
            val f3 = (color and 0xFF).toFloat() / 255.0f
            val f4 = (color shr 24 and 0xFF).toFloat() / 255.0f
            GL11.glPushMatrix()
            GL11.glEnable(3042)
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(2929)
            GL11.glEnable(3042)
            GL11.glBlendFunc(770, 771)
            GL11.glDisable(3553)
            GL11.glDisable(2929)
            GL11.glDepthMask(false)
            GL11.glLineWidth(1.0f)
            GL11.glColor4f(f, f2, f3, f4)
            drawOutlinedBoundingBox(
                AxisAlignedBB(
                    xPos,
                    yPos,
                    zPos,
                    xPos + 1.0,
                    yPos + height,
                    zPos + 1.0
                )
            )
            GL11.glColor3f(1.0f, 1.0f, 1.0f)
            GL11.glEnable(3553)
            GL11.glEnable(2929)
            GL11.glDepthMask(true)
            GL11.glDisable(3042)
            GL11.glDisable(3042)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glEnable(2929)
            GlStateManager.disableBlend()
            GL11.glPopMatrix()
        }

        fun drawOutlinedBoundingBox(axisAlignedBB: AxisAlignedBB) {
            val tessellator = Tessellator.getInstance()
            val worldRenderer = tessellator.worldRenderer
            worldRenderer.begin(3, DefaultVertexFormats.POSITION)
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
            tessellator.draw()
            worldRenderer.begin(3, DefaultVertexFormats.POSITION)
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
            tessellator.draw()
            worldRenderer.begin(1, DefaultVertexFormats.POSITION)
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
            worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
            tessellator.draw()
        }


        fun drawFilledBox(mask: AxisAlignedBB) {
            val worldRenderer = Tessellator.getInstance().worldRenderer
            val tessellator = Tessellator.getInstance()
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                tessellator.draw()
            }
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                tessellator.draw()
            }
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                tessellator.draw()
            }
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                tessellator.draw()
            }
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                tessellator.draw()
            }
            run {
                worldRenderer.startDrawingQuads()
                worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.minX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.minZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ).endVertex()
                worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ).endVertex()
                tessellator.draw()
            }
        }

        fun enableGL2D() {
            GL11.glDisable(2929)
            GL11.glEnable(3042)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glDepthMask(true)
            GL11.glEnable(2848)
            GL11.glHint(3154, 4354)
            GL11.glHint(3155, 4354)
        }

        fun disableGL2D() {
            GL11.glEnable(3553)
            GL11.glDisable(3042)
            GL11.glEnable(2929)
            GL11.glDisable(2848)
            GL11.glHint(3154, 4352)
            GL11.glHint(3155, 4352)
        }

        fun glColor(hex: Int) {
            val alpha = (hex shr 24 and 0xFF) / 255.0f
            val red = (hex shr 16 and 0xFF) / 255.0f
            val green = (hex shr 8 and 0xFF) / 255.0f
            val blue = (hex and 0xFF) / 255.0f
            GL11.glColor4f(red, green, blue, alpha)
        }


        fun drawOutlinedEntityESP(
            x: Double,
            y: Double,
            z: Double,
            width: Double,
            height: Double,
            red: Float,
            green: Float,
            blue: Float,
            alpha: Float
        ) {
            GL11.glPushMatrix()
            GL11.glEnable(3042)
            GL11.glBlendFunc(770, 771)
            GL11.glDisable(3553)
            GL11.glEnable(2848)
            GL11.glDisable(2929)
            GL11.glDepthMask(false)
            GL11.glColor4f(red, green, blue, alpha)
            drawOutlinedBoundingBox(AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width))
            drawFilledBox(AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width))
            GL11.glDisable(2848)
            GL11.glEnable(3553)
            GL11.glEnable(2929)
            GL11.glDepthMask(true)
            GL11.glDisable(3042)
            GL11.glPopMatrix()
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glColor4f(1f, 1f, 1f, 1f)
        }

        fun drawEntityOnScreen(posX: Int, posY: Int, scale: Float, ent: EntityLivingBase?) {
            GlStateManager.enableColorMaterial()
            GlStateManager.pushMatrix()
            GlStateManager.color(255f, 255f, 255f)
            GlStateManager.translate(posX.toFloat(), posY.toFloat(), 50.0f)
            GlStateManager.scale(-scale, scale, scale)
            GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f)
            GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f)
            RenderHelper.enableStandardItemLighting()
            GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f)
            GlStateManager.translate(0.0f, 0.0f, 0.0f)
            val rendermanager = Minecraft.getMinecraft().renderManager
            rendermanager.setPlayerViewY(1f)
            rendermanager.isRenderShadow = false
            rendermanager.renderEntityWithPosYaw(ent, 0.0, 0.0, 0.0, 0.0f, 1.0f)
            rendermanager.isRenderShadow = true
            GlStateManager.popMatrix()
            RenderHelper.disableStandardItemLighting()
            GlStateManager.disableRescaleNormal()
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit)
            GlStateManager.disableTexture2D()
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit)
        }

        fun drawBorderedBox(x: Double, y: Double, x2: Double, y2: Double, color: Color, bordered: Boolean) {
            start2D()
            GL11.glPushMatrix()
            if (bordered) {
                GL11.glLineWidth(2f)
                setColor(Color(-0x1000000))
                GL11.glBegin(GL11.GL_LINE_STRIP)
                GL11.glVertex2d(x, y)
                GL11.glVertex2d(x + (x2 - x), y)
                GL11.glVertex2d(x + (x2 - x), y + (y2 - y))
                GL11.glVertex2d(x, y + (y2 - y))
                GL11.glVertex2d(x, y)
                GL11.glEnd()
            }
            GL11.glLineWidth(1f)
            setColor(color)
            GL11.glBegin(GL11.GL_LINE_STRIP)
            GL11.glVertex2d(x, y)
            GL11.glVertex2d(x + (x2 - x), y)
            GL11.glVertex2d(x + (x2 - x), y + (y2 - y))
            GL11.glVertex2d(x, y + (y2 - y))
            GL11.glVertex2d(x, y)
            GL11.glEnd()
            GL11.glPopMatrix()
            stop2D()
        }

        fun drawBorderedRect(x: Float, y: Float, x2: Float, y2: Float, l1: Float, col1: Int, col2: Int) {
            Gui.drawRect(x.toInt(), y.toInt(), x2.toInt(), y2.toInt(), col2)
            val f = (col1 shr 24 and 0xFF) / 255.0f
            // @off
            val f1 = (col1 shr 16 and 0xFF) / 255.0f
            val f2 = (col1 shr 8 and 0xFF) / 255.0f
            val f3 = (col1 and 0xFF) / 255.0f // @on
            GL11.glEnable(3042)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glEnable(2848)
            GL11.glPushMatrix()
            GL11.glColor4f(f1, f2, f3, f)
            GL11.glLineWidth(l1)
            GL11.glBegin(1)
            GL11.glVertex2d(x.toDouble(), y.toDouble())
            GL11.glVertex2d(x.toDouble(), y2.toDouble())
            GL11.glVertex2d(x2.toDouble(), y2.toDouble())
            GL11.glVertex2d(x2.toDouble(), y.toDouble())
            GL11.glVertex2d(x.toDouble(), y.toDouble())
            GL11.glVertex2d(x2.toDouble(), y.toDouble())
            GL11.glVertex2d(x.toDouble(), y2.toDouble())
            GL11.glVertex2d(x2.toDouble(), y2.toDouble())
            GL11.glEnd()
            GL11.glColor4f(1f, 1f, 1f, 1f)
            GL11.glPopMatrix()
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glColor4f(1f, 1f, 1f, 255f)
            GL11.glEnable(3553)
            GL11.glDisable(3042)
            GL11.glDisable(2848)
        }


        fun drawBorderedRect(x: Double, y: Double, x2: Double, y2: Double, l1: Float, col1: Int, col2: Int) {
            Gui.drawRect(x.toFloat().toInt(), y.toFloat().toInt(), x2.toFloat().toInt(), y2.toFloat().toInt(), col2)
            val f = (col1 shr 24 and 0xFF) / 255.0f
            // @off
            val f1 = (col1 shr 16 and 0xFF) / 255.0f
            val f2 = (col1 shr 8 and 0xFF) / 255.0f
            val f3 = (col1 and 0xFF) / 255.0f // @on
            GL11.glEnable(3042)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glEnable(2848)
            GL11.glPushMatrix()
            GL11.glColor4f(f1, f2, f3, f)
            GL11.glLineWidth(l1)
            GL11.glBegin(1)
            GL11.glVertex2d(x, y)
            GL11.glVertex2d(x, y2)
            GL11.glVertex2d(x2, y2)
            GL11.glVertex2d(x2, y)
            GL11.glVertex2d(x, y)
            GL11.glVertex2d(x2, y)
            GL11.glVertex2d(x, y2)
            GL11.glVertex2d(x2, y2)
            GL11.glEnd()
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glPopMatrix()
            GL11.glColor4f(255f, 1f, 1f, 255f)
            GL11.glEnable(3553)
            GL11.glDisable(3042)
            GL11.glDisable(2848)
        }

        fun drawBorderedRect(x: Int, y: Int, x2: Int, y2: Int, li: Float, col1: Int, col2: Int) {
            drawBorderedRect(x.toDouble(), y.toDouble(), x2.toDouble(), y2.toDouble(), li, col1, col2)
        }

        fun drawRoundedRect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Int) {
            var x = x
            var y = y
            var x1 = x + width
            // @off
            var y1 = y + height
            val f = (color shr 24 and 0xFF) / 255.0f
            val f1 = (color shr 16 and 0xFF) / 255.0f
            val f2 = (color shr 8 and 0xFF) / 255.0f
            val f3 = (color and 0xFF) / 255.0f // @on
            GL11.glPushAttrib(0)
            GL11.glScaled(0.5, 0.5, 0.5)
            x *= 2f
            y *= 2f
            x1 *= 2f
            y1 *= 2f
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glColor4f(f1, f2, f3, f)
            GlStateManager.enableBlend()
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glBegin(GL11.GL_POLYGON)
            val v = Math.PI / 180
            run {
                var i = 0
                while (i <= 90) {
                    GL11.glVertex2d(
                        (x + radius + MathHelper.sin((i * v).toFloat()) * (radius * -1)).toDouble(),
                        (y + radius + MathHelper.cos((i * v).toFloat()) * (radius * -1)).toDouble()
                    )
                    i += 3
                }
            }
            run {
                var i = 90
                while (i <= 180) {
                    GL11.glVertex2d(
                        (x + radius + MathHelper.sin((i * v).toFloat()) * (radius * -1)).toDouble(),
                        (y1 - radius + MathHelper.cos((i * v).toFloat()) * (radius * -1)).toDouble()
                    )
                    i += 3
                }
            }
            run {
                var i = 0
                while (i <= 90) {
                    GL11.glVertex2d(
                        (x1 - radius + MathHelper.sin((i * v).toFloat()) * radius).toDouble(),
                        (y1 - radius + MathHelper.cos((i * v).toFloat()) * radius).toDouble()
                    )
                    i += 3
                }
            }
            var i = 90
            while (i <= 180) {
                GL11.glVertex2d(
                    (x1 - radius + MathHelper.sin((i * v).toFloat()) * radius).toDouble(),
                    (y + radius + MathHelper.cos((i * v).toFloat()) * radius).toDouble()
                )
                i += 3
            }
            GL11.glEnd()
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glScaled(2.0, 2.0, 2.0)
            GL11.glPopAttrib()
            GL11.glColor4f(1f, 1f, 1f, 1f)
        }
        fun drawCircle(cx: Float, cy: Float, r: Float, num_segments: Float, c: Int) {
            var cx = cx
            var cy = cy
            GL11.glPushMatrix()
            cx *= 2.0f
            cy *= 2.0f
            val f0 = (c shr 24 and 0xFF) / 255.0f
            // @off
            val f1 = (c shr 16 and 0xFF) / 255.0f
            val f2 = (c shr 8 and 0xFF) / 255.0f
            val f3 = (c and 0xFF) / 255.0f
            val theta = 6.2831852f / num_segments
            val p = MathHelper.cos(theta)
            val s = MathHelper.sin(theta) // @on
            var x = r * 2.0f
            var y = 0.0f
            GL11.glScalef(0.5f, 0.5f, 0.5f)
            GL11.glColor4f(f1, f2, f3, f0)
            GL11.glBegin(2)
            var i = 0
            while (i < num_segments) {
                GL11.glVertex2f(x + cx, y + cy)
                val t = x
                x = p * x - s * y
                y = s * t + p * y
                i++
            }
            GL11.glEnd()
            GL11.glScalef(2.0f, 2.0f, 2.0f)
            disableGL2D()
            GlStateManager.color(1f, 1f, 1f, 1f)
            GL11.glPopMatrix()
        }

        fun drawFilledCircle(x: Float, y: Float, r: Float, c: Int) {
            val f0 = (c shr 24 and 0xff) / 255f
            // @off
            val f1 = (c shr 16 and 0xff) / 255f
            val f2 = (c shr 8 and 0xff) / 255f
            val f3 = (c and 0xff) / 255f // @on
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glColor4f(f1, f2, f3, f0)
            GL11.glBegin(GL11.GL_TRIANGLE_FAN)
            val pi = Math.PI.toFloat()
            for (i in 0..450) {
                GL11.glVertex2d(
                    (x + MathHelper.sin(i * pi / 180.0f) * r).toDouble(),
                    (y + MathHelper.cos(i * pi / 180.0f) * r).toDouble()
                )
            }
            GL11.glEnd()
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_BLEND)
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glColor4f(1f, 1f, 1f, 1f)
        }

        //
        //    public static void pre3D() {
        //        GL11.glPushMatrix();
        //        glEnable(GL11.GL_BLEND);
        //        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //        GL11.glShadeModel(GL11.GL_SMOOTH);
        //        glDisable(GL11.GL_TEXTURE_2D);
        //        glEnable(GL11.GL_LINE_SMOOTH);
        //        glDisable(GL11.GL_DEPTH_TEST);
        //        glDisable(GL11.GL_LIGHTING);
        //        GL11.glDepthMask(false);
        //        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        //    }
        //
        //    public static void post3D() {
        //        GL11.glDepthMask(true);
        //        glEnable(GL11.GL_DEPTH_TEST);
        //        glDisable(GL11.GL_LINE_SMOOTH);
        //        glEnable(GL11.GL_TEXTURE_2D);
        //        glDisable(GL11.GL_BLEND);
        //        GL11.glPopMatrix();
        //        GL11.glColor4f(1, 1, 1, 1);
        //    }
        //
        //
        //    public static void dr(double i, double j, double k, double l, int i1) {
        //        if (i < k) {
        //            final double tmp = i;
        //            i = k;
        //            k = tmp;
        //        }
        //
        //        if (j < l) {
        //            final double tmp = j;
        //            j = l;
        //            l = tmp;
        //        }
        //
        //        final float f0 = (i1 >> 24 & 0xff) / 255F, // @off
        //                f1 = (i1 >> 16 & 0xff) / 255F,
        //                f2 = (i1 >> 8 & 0xff) / 255F,
        //                f3 = (i1 & 0xff) / 255F; // @on
        //
        //        final Tessellator tessellator = Tessellator.getInstance();
        //        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        //
        //        glEnable(GL11.GL_BLEND);
        //        glDisable(GL11.GL_TEXTURE_2D);
        //        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //        GL11.glColor4f(f1, f2, f3, f0);
        //
        //        worldRenderer.startDrawingQuads();
        //        worldRenderer.pos(i, l, 0.0D);
        //        worldRenderer.pos(k, l, 0.0D);
        //        worldRenderer.pos(k, j, 0.0D);
        //        worldRenderer.pos(i, j, 0.0D);
        //        tessellator.draw();
        //
        //        glEnable(GL11.GL_TEXTURE_2D);
        //        glDisable(GL11.GL_BLEND);
        //    }
        //
        //    public static void renderTHUD(KillAura aura, @NotNull EntityPlayer e) {
        //        GL11.glPushMatrix();
        //        Minecraft mc = Minecraft.getInstance();
        //        ScaleUtils.scale(mc);
        //        float hp = e.getHealth() + e.getAbsorptionAmount();
        //        final float maxHP = e.getMaxHealth() + e.getAbsorptionAmount() - 0.05f;
        //        int i = 0;
        //
        //        for (int b = 0; b < e.inventory.armorInventory.length; b++) {
        //            final ItemStack armor = e.inventory.armorInventory[b];
        //
        //            if (armor != null) {
        //                i++;
        //            }
        //        }
        //
        //        if (e.getCurrentEquippedItem() != null) {
        //            i++;
        //        }
        //
        //        float rectLength = 35 + mc.fontRendererCrack.getStringWidth(e.getName()) + 40, health = (float) (Math.round(hp * 100.0) / 100.0);
        //
        //        if (health > maxHP) {
        //            health *= maxHP / health;
        //        }
        //
        //        float amplifier = 100 / maxHP, percent = health * amplifier, space = (rectLength - 50) / 100; //
        //        ScaledResolution sr = new ScaledResolution(mc);
        //
        //        if (aura.getThx().get() > sr.getScaledWidthStatic(mc) - 50) {
        //            aura.getThx().set(sr.getScaledWidthStatic(mc) - 50);
        //        }
        //
        //        if (aura.getThy().get() > sr.getScaledHeightStatic(mc) - 50) {
        //            aura.getThy().set(sr.getScaledHeightStatic(mc) - 50);
        //        }
        //
        //        final int i2 = aura.getThx().get();
        //        final int i1 = aura.getThy().get();
        //
        //        if (percent < lastP) {
        //            diffP = lastP - percent;
        //        }
        //        lastP = percent;
        //        if (diffP > 0) {
        //            diffP = diffP + (0 - diffP) * 0.05f;
        //        }
        //
        //        diffP = MathHelper.clamp_double(diffP, 0, 100 - percent);
        //
        //        mc.getTextureManager().bindTexture(((AbstractClientPlayer) e).getLocationSkin());
        //        RenderUtils.drawBorderedRect(i2, i1 - 1.5f, i2 + rectLength - 6, i1 + 37.5, (float) 1, new Color(0, 0, 0, 50).getRGB(), new Color(29, 29, 29, 130).getRGB());
        //        Gui.drawRect(i2 + 1, i1, i2 + rectLength - 7, i1 + 36, new Color(40, 40, 40, 130).getRGB());
        //        GL11.glPushMatrix();
        //        GL11.glColor4f(1, 1, 1, 1);
        //        final int l6 = 8;
        //        final int i6 = 8;
        //        GL11.glScaled(4.4, 4.4, 4.4);
        //        Gui.drawScaledCustomSizeModalRect((float) ((i2 + 1.5) / 4.4), (float) ((i1 + 0.2) / 4.4), 8.0f, (float) l6, 8, i6, 8, 8, 64.0f, 64.0f);
        //        GL11.glPopMatrix();
        //        int hudColor = Novoline.getInstance().getModuleManager().getModule(HUD.class).getHUDColor();
        //        Gui.drawRect(i2 + 40, i1 + 16.5, i2 + 40 + 100 * space, i1 + 27.3, new Color(0, 0, 0, 50).getRGB());
        //        Gui.drawRect(i2 + 40, i1 + 16.5, i2 + 40 + percent * space, i1 + 27.3, hudColor);
        //        Gui.drawRect(i2 + 40 + percent * space, i1 + 16.5, i2 + 40 + percent * space + diffP * space, i1 + 27.3, new Color(hudColor).darker().getRGB());
        //        String text = String.format("%.1f", percent) + "%";
        //        mc.fontRendererCrack.drawString(text, i2 + 40 + 50 * space - mc.fontRendererCrack.getStringWidth(text) / 2,
        //                i1 + 18f, 0xffffffff, true);
        //        mc.fontRendererCrack.drawString(e.getName(), i2 + 40, i1 + 4, 0xffffffff, true);
        //        //   mc.fontRendererCrack.drawString(String.format("%.1f", (e.getHealth() + e.getAbsorptionAmount()) / 2), i2 + 41, i1 + 27, 0xffffffff, true);
        //        //   mc.fontRendererCrack.drawString(" \u2764", i2 + 40 + mc.fontRendererCrack.getStringWidth(String.format("%.1f", (e.getHealth() + e.getAbsorptionAmount()) / 2.0F)), i1 + 27, hudColor, true);
        //
        //
        //        GL11.glPopMatrix();
        //    }
        //
        //    public static void drawArmorHUD(EntityPlayer player, int x, int y) {
        //        GL11.glPushMatrix();
        //
        //        Minecraft mc = Minecraft.getInstance();
        //        final List<ItemStack> stuff = new ObjectArrayList<>();
        //        final boolean onWater = mc.player.isEntityAlive() && mc.player.isInsideOfMaterial(Material.water);
        //
        //        for (int index = 3; index >= 0; --index) {
        //            final ItemStack armor = player.inventory.armorInventory[index];
        //
        //            if (armor != null) {
        //                stuff.add(armor);
        //            }
        //        }
        //
        //        if (player.getCurrentEquippedItem() != null) {
        //            stuff.add(player.getCurrentEquippedItem());
        //        }
        //
        //        int split = -3;
        //        int split2 = -3;
        //
        //        for (ItemStack item : stuff) {
        //            if (mc.world != null) {
        //                RenderHelper.enableGUIStandardItemLighting();
        //                split += 16;
        //            }
        //
        //            GlStateManager.pushMatrix();
        //            GlStateManager.disableAlpha();
        //            GlStateManager.clear(256);
        //            GlStateManager.enableBlend();
        //            mc.getRenderItem().zLevel = -150.0F;
        //            mc.getRenderItem().renderItemAndEffectIntoGUI(item, split + x + 18, y + 17);
        //            mc.getRenderItem().zLevel = 0.0F;
        //            GlStateManager.enableBlend();
        //            final float z = 0.5F;
        //            GlStateManager.scale(z, z, z);
        //            GlStateManager.disableDepth();
        //            GlStateManager.disableLighting();
        //            GlStateManager.enableDepth();
        //            GlStateManager.scale(2.0f, 2.0f, 2.0f);
        //            GlStateManager.enableAlpha();
        //            GlStateManager.popMatrix();
        //        }
        //        GL11.glPopMatrix();
        //    }
        //
        //    public static void drawStack(FontRenderer font, boolean renderOverlay, ItemStack stack, float x, float y) {
        //        GL11.glPushMatrix();
        //
        //        Minecraft mc = Minecraft.getInstance();
        //
        //        if (mc.world != null) {
        //            RenderHelper.enableGUIStandardItemLighting();
        //        }
        //
        //        GlStateManager.pushMatrix();
        //        GlStateManager.disableAlpha();
        //        GlStateManager.clear(256);
        //        GlStateManager.enableBlend();
        //
        //        mc.getRenderItem().zLevel = -150.0F;
        //        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        //
        //        if (renderOverlay) {
        //            mc.getRenderItem().renderItemOverlayIntoGUI(font, stack, x, y, String.valueOf(stack.stackSize));
        //        }
        //
        //        mc.getRenderItem().zLevel = 0.0F;
        //
        //        GlStateManager.enableBlend();
        //        final float z = 0.5F;
        //
        //        GlStateManager.scale(z, z, z);
        //        GlStateManager.disableDepth();
        //        GlStateManager.disableLighting();
        //        GlStateManager.enableDepth();
        //        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        //        GlStateManager.enableAlpha();
        //        GlStateManager.popMatrix();
        //
        //        GL11.glPopMatrix();
        //    }
        //
        //    public static void drawArrow(double x, double y, int lineWidth, int color, double length) {
        //        start2D();
        //        GL11.glPushMatrix();
        //        GL11.glLineWidth(lineWidth);
        //        setColor(new Color(color));
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x, y);
        //        GL11.glVertex2d(x + 3, y + length);
        //        GL11.glVertex2d(x + 3 * 2, y);
        //        GL11.glEnd();
        //        GL11.glPopMatrix();
        //        stop2D();
        //    }
        //
        //
        //    public static void drawCheck(double x, double y, int lineWidth, int color) {
        //        start2D();
        //        GL11.glPushMatrix();
        //        GL11.glLineWidth(lineWidth);
        //        setColor(new Color(color));
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x, y);
        //        GL11.glVertex2d(x + 2, y + 3);
        //        GL11.glVertex2d(x + 6, y - 2);
        //        GL11.glEnd();
        //        GL11.glPopMatrix();
        //        stop2D();
        //    }
        //
        //    public static void drawCheckbox(double x, double y, double x2, double y2, double lineWidth, int color) {
        //        start2D();
        //        GL11.glPushMatrix();
        //        GL11.glLineWidth((float) lineWidth);
        //        setColor(new Color(color));
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x, y);
        //        GL11.glVertex2d(x, y + (y2 - y));
        //        GL11.glVertex2d(x + (x2 - x), y + (y2 - y));
        //        GL11.glVertex2d(x + (x2 - x), y);
        //        GL11.glVertex2d(x, y);
        //        GL11.glEnd();
        //        GL11.glPopMatrix();
        //        stop2D();
        //    }
        //
        //    public static void drawCornerBox(double x, double y, double x2, double y2, double lw, Color color) {
        //        double width = Math.abs(x2 - x);
        //        double height = Math.abs(y2 - y);
        //        double halfWidth = width / 4;
        //        double halfHeight = height / 4;
        //        start2D();
        //        GL11.glPushMatrix();
        //        GL11.glLineWidth((float) lw);
        //        setColor(color);
        //
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x + halfWidth, y);
        //        GL11.glVertex2d(x, y);
        //        GL11.glVertex2d(x, y + halfHeight);
        //        GL11.glEnd();
        //
        //
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x, y + height - halfHeight);
        //        GL11.glVertex2d(x, y + height);
        //        GL11.glVertex2d(x + halfWidth, y + height);
        //        GL11.glEnd();
        //
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x + width - halfWidth, y + height);
        //        GL11.glVertex2d(x + width, y + height);
        //        GL11.glVertex2d(x + width, y + height - halfHeight);
        //        GL11.glEnd();
        //
        //        GL11.glBegin(GL_LINE_STRIP);
        //        GL11.glVertex2d(x + width, y + halfHeight);
        //        GL11.glVertex2d(x + width, y);
        //        GL11.glVertex2d(x + width - halfWidth, y);
        //        GL11.glEnd();
        //
        //        GL11.glPopMatrix();
        //        stop2D();
        //    }
        fun start2D() {
            GL11.glEnable(3042)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glEnable(2848)
        }

        fun stop2D() {
            GL11.glEnable(3553)
            GL11.glDisable(3042)
            GL11.glDisable(2848)
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glColor4f(1f, 1f, 1f, 1f)
        }

        fun setColor(color: Color) {
            val alpha = (color.rgb shr 24 and 0xFF) / 255.0f
            val red = (color.rgb shr 16 and 0xFF) / 255.0f
            val green = (color.rgb shr 8 and 0xFF) / 255.0f
            val blue = (color.rgb and 0xFF) / 255.0f
            GL11.glColor4f(red, green, blue, alpha)
        }

        fun startDrawing() {
            GL11.glEnable(3042)
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(2929)
            mc.entityRenderer.setupCameraTransform(
                mc.timer.renderPartialTicks,
                0
            )
        }

        fun stopDrawing() {
            GL11.glDisable(3042)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glEnable(2929)
            GlStateManager.disableBlend()
        }

        fun drawLine(entity: Entity?, color: DoubleArray, x: Double, y: Double, z: Double) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            if (color.size >= 4) {
                if (color[3] <= 0.1) return
                GL11.glColor4d(color[0], color[1], color[2], color[3])
            } else {
                GL11.glColor3d(color[0], color[1], color[2])
            }
            GL11.glLineWidth(1.5f)
            GL11.glBegin(1)
            GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight().toDouble(), 0.0)
            GL11.glVertex3d(x, y, z)
            GL11.glEnd()
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
        }

        fun drawLine(blockPos: BlockPos, color: Int) {
            val mc = Minecraft.getMinecraft()
            val renderPosXDelta = blockPos.x - mc.renderManager.renderPosX + 0.5
            val renderPosYDelta = blockPos.y - mc.renderManager.renderPosY + 0.5
            val renderPosZDelta = blockPos.z - mc.renderManager.renderPosZ + 0.5
            GL11.glPushMatrix()
            GL11.glEnable(3042)
            GL11.glEnable(2848)
            GL11.glDisable(2929)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glLineWidth(1.0f)
            val blockPos9 = (mc.thePlayer.posX - blockPos.x.toDouble()).toFloat()
            val blockPos7 = (mc.thePlayer.posY - blockPos.y.toDouble()).toFloat()
            val f = (color shr 16 and 0xFF).toFloat() / 255.0f
            val f2 = (color shr 8 and 0xFF).toFloat() / 255.0f
            val f3 = (color and 0xFF).toFloat() / 255.0f
            val f4 = (color shr 24 and 0xFF).toFloat() / 255.0f
            GL11.glColor4f(f, f2, f3, f4)
            GL11.glLoadIdentity()
            val previousState = mc.gameSettings.viewBobbing
            mc.gameSettings.viewBobbing = false
            mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks)
            GL11.glBegin(3)
            GL11.glVertex3d(0.0, mc.thePlayer.eyeHeight.toDouble(), 0.0)
            GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta)
            GL11.glVertex3d(renderPosXDelta, renderPosYDelta, renderPosZDelta)
            GL11.glEnd()
            mc.gameSettings.viewBobbing = previousState
            GL11.glEnable(3553)
            GL11.glEnable(2929)
            GL11.glDisable(2848)
            GL11.glDisable(3042)
            GL11.glPopMatrix()
        }
    }
}