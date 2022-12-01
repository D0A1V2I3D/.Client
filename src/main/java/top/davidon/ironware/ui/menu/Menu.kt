package top.davidon.ironware.ui.menu

import net.minecraft.client.gui.*
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.input.Keyboard
import top.davidon.ironware.Client
import top.davidon.ironware.ui.GuiPassBox
import top.davidon.ironware.utils.Utils

class Menu : GuiScreen() {
    lateinit var usernameField: GuiTextField
    lateinit var passField: GuiPassBox
    lateinit var message: String
    var offset = 5

    override fun initGui() {
        usernameField = GuiTextField(0, mc.fontRendererObj, width / 2 - 100, (height / 2 - 47.5).toInt(), 200, 20)
        passField = GuiPassBox(1, mc.fontRendererObj, width / 2 - 100, (height / 2 - 22.5).toInt(), 208, 20)
        buttonList.add(GuiButton(3, width / 2 - 100, (height / 2 + 2.5).toInt(), 200, 20, "Login"))

        usernameField.setTextColor(-1)
        usernameField.enableBackgroundDrawing = true

        passField.setTextColor(-1)
        passField.enableBackgroundDrawing = true

        message = ""

        Keyboard.enableRepeatEvents(true)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, width, height, -0xddddde)
        if (!true) { // not logged in
            usernameField.drawTextBox()
            passField.drawTextBox()
            mc.fontRendererObj.drawStringWithShadow(
                message,
                (width / 2 - mc.fontRendererObj.getStringWidth(message) / 2).toFloat(),
                (height / 2 + 32.5).toFloat(),
                -1
            )
            super.drawScreen(mouseX, mouseY, partialTicks)
        } else {
            val buttonLst = arrayOf("Singleplayer", "Multiplayer", "Settings", "Alts", "Quit")
            val startX = width / 2
            val startY =
                height / 2 - fontRendererObj.FONT_HEIGHT / 2 - offset - fontRendererObj.FONT_HEIGHT - offset - fontRendererObj.FONT_HEIGHT
            var count = 0
            for (name in buttonLst) {
                val x1 = startX - fontRendererObj.getStringWidth(name) / 2
                val x2 = startX + fontRendererObj.getStringWidth(name) / 2
                val y = startY + (fontRendererObj.FONT_HEIGHT + offset) * count
                val hovered = Utils.isInside(mouseX, mouseY, x1, y, x2, y + fontRendererObj.FONT_HEIGHT)
                drawCenteredString(fontRendererObj, name, startX, y, if (hovered) -0x100 else -1)
                count++
            }
            GlStateManager.pushMatrix()
            GlStateManager.translate(width / 2f, height / 2f - 100f, 0f)
            GlStateManager.scale(2f, 2f, 1f)
            GlStateManager.translate(-(width / 2f), -(height / 2f - 100f), 0f)
            drawCenteredString(fontRendererObj, Client.name, (width / 2f).toInt(), (height / 2f - 100f).toInt(), -1)
            GlStateManager.popMatrix()
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!true) { //!logged in
            usernameField.mouseClicked(mouseX, mouseY, mouseButton)
            passField.mouseClicked(mouseX, mouseY, mouseButton)
            super.mouseClicked(mouseX, mouseY, mouseButton)
        } else {
            val buttonLst = arrayOf("Singleplayer", "Multiplayer", "Settings", "Alts", "Quit")
            val startX = width / 2
            val startY =
                height / 2 - fontRendererObj.FONT_HEIGHT / 2 - offset - fontRendererObj.FONT_HEIGHT - offset - fontRendererObj.FONT_HEIGHT
            var count = 0
            for (name in buttonLst) {
                val x1 = startX - fontRendererObj.getStringWidth(name) / 2
                val x2 = startX + fontRendererObj.getStringWidth(name) / 2
                val y = startY + (fontRendererObj.FONT_HEIGHT + offset) * count
                val hovered: Boolean = Utils.isInside(mouseX, mouseY, x1, y, x2, y + fontRendererObj.FONT_HEIGHT)
                if (mouseButton == 0 && hovered) {
                    //mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    when (name) {
                        "Singleplayer" -> mc.displayGuiScreen(GuiSelectWorld(this))
                        "Multiplayer" -> mc.displayGuiScreen(GuiMultiplayer(this))
                        "Alts" -> mc.displayGuiScreen(Client.accountManager.altScreen)
                        "Settings" -> mc.displayGuiScreen(GuiOptions(this, mc.gameSettings))
                        "Quit" -> mc.shutdown()
                    }
                }
                count++
            }
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (!true) { // !loggedin
            if (keyCode == Keyboard.KEY_TAB) {
                if (!usernameField.isFocused && !passField.isFocused) {
                    usernameField.isFocused = true
                } else if (usernameField.isFocused) {
                    usernameField.isFocused = false
                    passField.isFocused = true
                }
            } else if (keyCode == Keyboard.KEY_RETURN) {
                TODO("Login")
            }
            usernameField.textboxKeyTyped(typedChar, keyCode)
            passField.textboxKeyTyped(typedChar, keyCode)
        }

        super.keyTyped(typedChar, keyCode)
    }

    override fun actionPerformed(button: GuiButton?) {
        if (!true) { // not logged in
            if (button!!.id == 3) {
                TODO("Login")
            }
            super.actionPerformed(button)
        }
    }

    override fun onGuiClosed() {
        super.onGuiClosed()

        Keyboard.enableRepeatEvents(false)
    }
}