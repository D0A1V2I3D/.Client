package top.davidon.ironware.ui.clicgui

import top.davidon.ironware.modules.ModuleCategory
import java.awt.Color

data class CategoryData(
    var posX: Int, var posY: Int,
    var width: Int, var height: Int, var category: ModuleCategory, var color: Color
) {
    var sposX = posX + width
    var sposY = posY + height
    var dragging = false
    var dragX = 0
    var dragY = 0
}