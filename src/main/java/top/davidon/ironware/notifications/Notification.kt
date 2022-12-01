package top.davidon.ironware.notifications

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import top.davidon.ironware.utils.RenderUtils
import java.awt.Color

class Notification {
    private val mc = Minecraft.getMinecraft()
    lateinit private var type: NotificationType
    lateinit private var messsage: String
    lateinit private  var title:kotlin.String
    private var start: Long = 0
    private  var fadeIn:Long = 0
    private  var fadeOut:Long = 0
    private  var end:Long = 0

    constructor(type: NotificationType, title: String, messsage: String, length: Int) {
        this.type = type
        this.title = title
        this.messsage = messsage
        fadeIn = (200 * length).toLong()
        fadeOut = fadeIn + 500 * length
        end = fadeOut + fadeIn
    }

    fun show() {
        start = System.currentTimeMillis()
    }

    fun isShown(): Boolean {
        return getTime() <= end
    }

    private fun getTime(): Long {
        return System.currentTimeMillis() - start
    }

    fun render() {
        val sr = ScaledResolution(mc)
        val offset: Double
        val width = 120
        val height = 30
        val time = getTime()
        offset = if (time < fadeIn) {
            Math.tanh(time / fadeIn.toDouble() * 3.0) * width
        } else if (time > fadeOut) {
            Math.tanh(3.0 - (time - fadeOut) / (end - fadeOut).toDouble() * 3.0) * width
        } else {
            width.toDouble()
        }
        var color = Color(20, 20, 20, 80)
        val color1: Color
        if (type === NotificationType.INFO) color1 =
            Color(0, 255, 0) else if (type === NotificationType.WARNING) color1 = Color(204, 193, 0) else {
            color1 = Color(204, 0, 18)
            val i = Math.max(0, Math.min(255, (Math.sin(time / 100.0) * 255.0 / 2 + 127.5).toInt()))
            color = Color(i, 0, 0, 220)
        }
        val fr = Minecraft.getMinecraft().fontRendererObj

        RenderUtils.drawBorderedRect(
            sr.scaledWidth - offset,
            (sr.scaledHeight - 5 - height).toDouble(),
            sr.scaledWidth.toDouble(),
            (sr.scaledHeight - 5).toDouble(),
            3F,
            color1.rgb,
            color.rgb
        )
        fr.drawString(title, (sr.scaledWidth - offset + 8).toInt(), sr.scaledHeight - 2 - height, -1)
        fr.drawString(messsage, (sr.scaledWidth - offset + 8).toInt(), sr.scaledHeight - 15, -1)
    }
}