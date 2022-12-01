package top.davidon.ironware.utils

import java.awt.Color

class GayUtil {
    companion object {
        fun getRainbow(seconds: Float, saturation: Float, brightness: Float): Int {
            val hue =
                System.currentTimeMillis() % (seconds * 1000).toInt() / (seconds * 1000)
            return Color.HSBtoRGB(hue, saturation, brightness)
        }
    }
}