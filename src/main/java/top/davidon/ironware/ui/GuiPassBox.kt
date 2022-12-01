package top.davidon.ironware.ui

import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiTextField

class GuiPassBox(var componentId: Int, var fontrenderer: FontRenderer, var x: Int, var y: Int, var par5Width: Int, var par6Height: Int) :
    GuiTextField(componentId, fontrenderer, x, y, par5Width, par6Height) {
    var password: String? = null

    override fun drawTextBox() {
        if (visible) {
            if (enableBackgroundDrawing) {
                drawRect(xPosition - 1, yPosition - 1, xPosition + width + 1, yPosition + height + 1, -6250336)
                drawRect(xPosition, yPosition, xPosition + width, yPosition + height, -16777216)
            }
            val i = if (isEnabled) enabledColor else disabledColor
            val j = cursorPosition - lineScrollOffset
            var k = selectionEnd - lineScrollOffset
            val s = fontRendererInstance.trimStringToWidth(
                text.replace(".".toRegex(), "*").substring(lineScrollOffset),
                width
            )
            val flag = j >= 0 && j <= s.length
            val flag1 = isFocused && cursorCounter / 6 % 2 == 0 && flag
            val l = if (enableBackgroundDrawing) xPosition + 4 else xPosition
            val i1 = if (enableBackgroundDrawing) yPosition + (height - 8) / 2 else yPosition
            var j1 = l
            if (k > s.length) {
                k = s.length
            }
            if (s.length > 0) {
                val s1 = if (flag) s.substring(0, j) else s
                j1 = fontRendererInstance.drawStringWithShadow(s1, l.toFloat(), i1.toFloat(), i)
            }
            val flag2 = cursorPosition < text.length || text.length >= maxStringLength
            var k1 = j1
            if (!flag) {
                k1 = if (j > 0) l + width else l
            } else if (flag2) {
                k1 = j1 - 1
                --j1
            }
            if (s.length > 0 && flag && j < s.length) {
                j1 = fontRendererInstance.drawStringWithShadow(s.substring(j), j1.toFloat(), i1.toFloat(), i)
            }
            if (flag1) {
                if (flag2) {
                    drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRendererInstance.FONT_HEIGHT, -3092272)
                } else {
                    fontRendererInstance.drawStringWithShadow("_", k1.toFloat(), i1.toFloat(), i)
                }
            }
            if (k != j) {
                val l1 = l + fontRendererInstance.getStringWidth(s.substring(0, k))
                drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + fontRendererInstance.FONT_HEIGHT)
            }
        }
    }
}