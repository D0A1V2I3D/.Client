package top.davidon.ironware.ui.clicgui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import top.davidon.ironware.Client
import top.davidon.ironware.modules.Module
import top.davidon.ironware.modules.ModuleCategory
import top.davidon.ironware.modules.render.ClickGui
import top.davidon.ironware.settings.SettingType
import top.davidon.ironware.settings.settings.BooleanSetting
import top.davidon.ironware.utils.RenderUtils
import top.davidon.ironware.utils.Utils
import top.davidon.ironware.utils.vector.Vec4d
import top.davidon.ironware.utils.vector.Vec4i
import java.awt.Color

class ClickGuiScreen : GuiScreen() {
    var mc = Minecraft.getMinecraft()
    var fr = Minecraft.getMinecraft().fontRendererObj
    var sr = ScaledResolution(Minecraft.getMinecraft())
    var categoryList: MutableList<CategoryData> = ArrayList()
    var offset = 3
    var globalHeight: Int = offset + fr.FONT_HEIGHT + offset
    var globalWidth = 100
    var listening = false
    var listenModule: Module? = null
    var pressed = false
    var mpressed = false
    var globalBackgrund = 0xff222222.toInt()
    var globalLighterBackground = 0xff303030.toInt()
    var globalText = 0xffffffff.toInt()
    var globalAccent = 0xffffff00.toInt()
    // settings
    var settingsPopup = false
    var settingsModule: Module? = null
    var settingWidth = 400
    var settingsHeight = 300;
    var settingsPosX = 50
    var settingsPosY = 50
    var settingsDragX = settingsPosX
    var settingsDragY = settingsPosY
    var settingsDragging = false
    var setEndY = 0
    // misc
    lateinit var cgui: ClickGui

    init {
        categoryList.add(CategoryData(25, 25, globalWidth, globalHeight, ModuleCategory.COMBAT, Color(0xffff0000.toInt())))
        categoryList.add(CategoryData(150, 25, globalWidth, globalHeight, ModuleCategory.MOVEMENT, Color(0xff00ff00.toInt())))
        categoryList.add(CategoryData(275, 25, globalWidth, globalHeight, ModuleCategory.RENDER, Color(0xff0000ff.toInt())))
        categoryList.add(CategoryData(400, 25, globalWidth, globalHeight, ModuleCategory.PLAYER, Color(0xffffff00.toInt())))
        categoryList.add(CategoryData(525, 25, globalWidth, globalHeight, ModuleCategory.GHOST, Color(0xffff8000.toInt())))
    }

    fun moduleInit() {
        cgui = Client.moduleManager.getModuleByName("ClickGui").get() as ClickGui
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        val sepHeight = cgui.separatorHeight.get().toInt()
        val outWidth = cgui.borderWidth.get().toFloat()
        if (listening) {
            fr.drawStringWithShadow("Listening", ((width - offset - fr.getStringWidth("Listening")).toFloat()),
                ((height - offset - fr.FONT_HEIGHT).toFloat()),
                0xffffffff.toInt()
            )
        }
        if (settingsPopup) {
            if (settingsDragging) {
                settingsPosX = mouseX - settingsDragX
                settingsPosY = mouseY - settingsDragY
            }
            var endY = settingsPosY + globalHeight + sepHeight;
            drawRect(settingsPosX, settingsPosY, settingsPosX + settingWidth, settingsPosY + globalHeight + sepHeight, globalLighterBackground)
            fr.drawString((settingsModule?.name ?: "") + " Settings", settingsPosX + offset, settingsPosY + offset, globalText)
            if (cgui.seperator.get())
                drawRect(settingsPosX, settingsPosY + globalHeight, settingsPosX + settingWidth, settingsPosY + globalHeight + sepHeight, globalText)
            for (s in settingsModule?.settings!!) {
                when (s.type) {
                    SettingType.BOOLEAN -> {
                        fr.drawString(s.name, settingsPosX + offset, endY + offset, globalText)
                        drawRect(settingsPosX + offset + fr.getStringWidth(s.name) + offset, endY + offset, settingsPosX + offset + fr.getStringWidth(s.name) + globalHeight - offset, endY + globalHeight - offset, globalLighterBackground)
                        if ((s as BooleanSetting).get()) {
                            drawRect(settingsPosX + offset + fr.getStringWidth(s.name) + offset + 1, endY + offset + 1, settingsPosX + offset + fr.getStringWidth(s.name) + globalHeight - offset - 1, endY + globalHeight - offset - 1, globalText)
                        }
                        s.vec = Vec4i(settingsPosX + offset + fr.getStringWidth(s.name) + offset, endY + offset, settingsPosX + offset + fr.getStringWidth(s.name) + globalHeight - offset, endY + globalHeight - offset)
                        endY += globalHeight
                    }
                    SettingType.COLOR -> {
                        //
                    }
                    SettingType.DESCRIPTION -> {
                        //
                    }
                    SettingType.ENUM -> {
                        //
                    }
                    SettingType.NUMBERRANGE -> {
                        //
                    }
                }
                drawRect(settingsPosX, settingsPosY + globalHeight + sepHeight, settingsPosX + settingWidth, endY, globalBackgrund)
                if (cgui.outlines.get())
                    RenderUtils.drawBorderedRect(settingsPosX, settingsPosY, settingsPosX + settingWidth, endY, outWidth, globalText, 0x00000000)
            }
        } else {
            for (cd in categoryList) {
                if (cd.dragging) {
                    cd.posX = mouseX - cd.dragX
                    cd.posY = mouseY - cd.dragY
                }
                cd.sposX = cd.posX + cd.width
                cd.sposY = cd.posY + cd.height
                drawRect(cd.posX, cd.posY, cd.sposX, cd.sposY + sepHeight, globalLighterBackground)
                fr.drawString(cd.category.displayName, cd.posX + offset, cd.posY + offset, globalText)
                if (cgui.seperator.get())
                    drawRect(cd.posX, cd.sposY, cd.sposX, cd.sposY + sepHeight, cd.color.getRGB())
                var mods = Client.moduleManager.getModulesByCategory(cd.category)
                mods.sortedWith(Comparator.comparingInt<Module> { m ->
                    fr.getStringWidth(
                        (m as Module).name
                    )
                }.reversed())
                var endY = 0
                for ((offsetn, m) in mods.withIndex()) {
                    drawRect(cd.posX, cd.sposY+sepHeight+(globalHeight * offsetn), cd.sposX, cd.sposY+sepHeight+(globalHeight * offsetn) + globalHeight, globalBackgrund)
                    var color: Int = if (m.enabled)
                        -1
                    else Color.WHITE.darker().darker().getRGB()
                    fr.drawString(m.name, cd.posX + offset, cd.sposY+sepHeight+(globalHeight * offsetn) + offset, color)
                    endY = cd.sposY+sepHeight+(globalHeight * offsetn) + globalHeight
                }
                if (cgui.outlines.get())
                    RenderUtils.drawBorderedRect(cd.posX, cd.posY, cd.sposX, endY, outWidth, cd.color.getRGB(), 0x00000000)
            }
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        if (mouseButton == 2) mpressed = true
        val sepHeight = cgui.separatorHeight.get().toInt()
        if (settingsPopup) {
            if (Utils.isInside(mouseX, mouseY, settingsPosX, settingsPosY, settingsPosX + settingWidth, settingsPosY + globalHeight + sepHeight) && mouseButton == 0) {
                settingsDragX = mouseX - settingsPosX
                settingsDragY = mouseY - settingsPosY
                settingsDragging = true
            }
            if (!Utils.isInside(mouseX, mouseY, settingsPosX, settingsPosY, settingsPosX + settingWidth, setEndY))
                settingsPopup = false
        } else {
            for (cd in categoryList) {
                if (Utils.isInside(mouseX, mouseY, cd.posX, cd.posY, cd.sposX, cd.sposY + sepHeight) && mouseButton == 3) {
                    //TODO CAT SETTINGS
                }; if (Utils.isInside(mouseX, mouseY, cd.posX, cd.posY, cd.sposY, cd.sposY + sepHeight) && mouseButton == 0) {
                    cd.dragX = mouseX - cd.posX
                    cd.dragY = mouseY - cd.posY
                    cd.dragging = true
                } else {
                    var mods = Client.moduleManager.getModulesByCategory(cd.category)
                    mods.sortedWith(Comparator.comparingInt<Module> { m ->
                        fr.getStringWidth(
                            (m as Module).name
                        )
                    }.reversed())
                    for ((offsetn, m) in mods.withIndex()) {
                        if (Utils.isInside(mouseX, mouseY, cd.posX + offset, cd.sposY+sepHeight+(globalHeight * offsetn) + offset, cd.sposX - offset, cd.sposY+sepHeight+(globalHeight * offsetn) + globalHeight - offset)) {
                            if (mouseButton == 0)
                                m.toggle()
                            else if (mouseButton == 1) {
                                if (m.settings.isEmpty()) return
                                settingsPopup = true
                                settingsModule = m
                            } else if (mouseButton == 2) {
                                listening = true
                                listenModule = m
                            }
                        }
                    }
                }
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        mpressed = false
        settingsDragging = false
        for (cd in categoryList) {
            cd.dragging = false
        }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val i = Mouse.getEventDWheel()
        if (i < 0 && mpressed) {
            //down
            settingsPosX += 3
            for (cd in categoryList) {
                cd.posX += 3
            }
        } else if (i > 0 && mpressed) {
            settingsPosX -= 3
            for (cd in categoryList) {
                cd.posX -= 3
            }
        } else if (i < 0) {
            //down
            settingsPosY += 3
            for (cd in categoryList) {
                cd.posY += 3
            }
        } else if (i > 0) {
            settingsPosY -= 3
            for (cd in categoryList) {
                cd.posY -= 3
            }
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        val keyState = Keyboard.getEventKeyState()
        if (!listening) {
//            if (settingsPopup) {
//                settingsPopup = false
//                return
//            }
        } else {
            listenModule?.bind(keyCode)
            listening = false
        }
    }

    override fun handleKeyboardInput() {
        val key = Keyboard.getEventKey()
        val state = Keyboard.getEventKeyState()
        when (key) {
            Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_SPACE, Keyboard.KEY_LSHIFT, Keyboard.KEY_LCONTROL -> KeyBinding.setKeyBindState(
                key,
                state
            )
        }
        super.handleKeyboardInput()
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }
}