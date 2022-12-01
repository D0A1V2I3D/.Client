package top.davidon.ironware.ui.alts

import com.github.javafaker.Faker
import fr.litarvan.openauth.microsoft.AuthTokens
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.util.Session
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import org.lwjgl.input.Mouse
import top.davidon.ironware.Client
import top.davidon.ironware.microshit.server.Server
import top.davidon.ironware.utils.Utils
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

class AltScreen(var parent: GuiScreen) : GuiScreen() {
    lateinit var textField: GuiTextField
    val MICROSOFT_TOEN_URL = "https://login.live.com/oauth20_token.srf"
    var code: String? = null
    var loggedInAccount: Account? = null
    var selectedAccount: Account? = null
    var listY = 5

    override fun initGui() {
        super.initGui()
        selectedAccount = Account("", "", "", "", AccountType.CRACKED, "")

        if (loggedInAccount == null) {
            loggedInAccount = Account(mc.session.username, "", "", mc.session.playerID, AccountType.CRACKED, UUID.randomUUID().toString())
        }
        //TODO LOAD ACCOUNTS
        this.textField = GuiTextField(69, mc.fontRendererObj, width - 230, 5, 200, 20)
        buttonList.add(GuiButton(0, width - 230, 35, "Login"))
        buttonList.add(GuiButton(1, width - 230, 65, "Delete"))
        buttonList.add(GuiButton(2, width - 230, 95, "Microsoft email:pass"))
        buttonList.add(GuiButton(3, width - 230, 125, "Microsoft weblogin"))
        buttonList.add(GuiButton(5, width - 230, 155, "Cracked"))
        buttonList.add(GuiButton(6, width - 230, 185, "Cracked random"))
        buttonList.add(GuiButton(7, width - 230, 215, "Cracked random one time"))
        buttonList.add(GuiButton(4, width - 230, 245, "Back"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Gui.drawRect(0, 0, mc.displayWidth, mc.displayHeight, -0xddddde)
        textField.drawTextBox()
        fontRendererObj.drawString("Logged in account: ", width - 230, 275, -1)
        fontRendererObj.drawString(loggedInAccount?.username ?: "", width - 210, 278 + fontRendererObj.FONT_HEIGHT, -1)

        fontRendererObj.drawString("Selected account: ", width - 230, 300 + fontRendererObj.FONT_HEIGHT, -1)
        fontRendererObj.drawString(
            selectedAccount?.username ?: "",
            width - 210,
            303 + fontRendererObj.FONT_HEIGHT * 2,
            -1
        )
        val offsetn = 3 + fontRendererObj.FONT_HEIGHT + 3

        for ((count, a) in Client.accountManager.accounts.withIndex()) {
            drawRect(5, listY + count * offsetn + 5, 205, listY + count * offsetn + offsetn + 5, -0xbfbfc0)
            fontRendererObj.drawString(
                a.type.displayName + ": " + a.username,
                8,
                listY + count * offsetn + 3 + 5,
                -1
            )

        }

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        textField.mouseClicked(mouseX, mouseY, mouseButton)
        if (Utils.isInside(mouseX, mouseY, width - 230, 5, width - 30, 25) && mouseButton == 1)
            textField.text = ""

        val offsetn = 3 + fontRendererObj.FONT_HEIGHT + 3
        for ((count, a) in Client.accountManager.accounts.withIndex()) {
            if (Utils.isInside(
                    mouseX,
                    mouseY,
                    8.0,
                    (listY + count * offsetn + 3 + 5).toDouble(),
                    (8 + fontRendererObj.getStringWidth(a.type.displayName + ": " + a.username)).toDouble(),
                    (listY + count * offsetn + 3 + 5 + fontRendererObj.FONT_HEIGHT).toDouble()
                )
            ) {
                selectedAccount = a
            }
        }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val i = Mouse.getEventDWheel()
        if (i < 0) {
            //down
            listY++
            listY++
            listY++
        } else if (i > 0) {
            listY--
            listY--
            listY--
        }
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                if (selectedAccount === loggedInAccount) {
                    return
                }
                if (selectedAccount?.type == AccountType.CRACKED) {
                    mc.session = Session(selectedAccount?.username ?: "", "", "", "legacy")
                } else if (selectedAccount?.type == AccountType.MICROSOFT) {
                    //TODO
                }
            }
            1 -> {
                Client.accountManager.accounts.remove(selectedAccount)
            }
            2 -> {
                if (textField.text.contains(":")) {
                    try {
                        var res = Client.microshit.loginWithCredentials(
                            textField.text.split(":".toRegex()).toTypedArray()[0],
                            textField.text.split(":".toRegex()).toTypedArray()[1])
                        mc.session = Session(res.profile.name, res.profile.id, res.accessToken, "legacy")
                    } catch (e: MicrosoftAuthenticationException) {
                        throw RuntimeException(e)
                    }
                }
            }
            3 -> {
                AccountHelper.login()
            }
            4 -> {
                mc.displayGuiScreen(parent)
            }
            5 -> {
                if (textField.text.isEmpty()) {
                    return
                }
                mc.session = Session(textField.text, "0", "0", "legacy")
                loggedInAccount =
                    Account(textField.text, "0", "0", "0", AccountType.CRACKED, UUID.randomUUID().toString())
                Client.accountManager.accounts.add(loggedInAccount!!)
            }
            6 -> {
                val faker = Faker()
                val usname = faker.name().username().replace(".", "")
                mc.session = Session(usname, "0", "0", "legacy")
                loggedInAccount = Account(usname, "0", "0", "0", AccountType.CRACKED, UUID.randomUUID().toString())
                Client.accountManager.accounts.add(loggedInAccount!!)
            }
            7 -> {
                val ffaker = Faker()
                val uusname = ffaker.name().username().replace(".", "")
                mc.session = Session(uusname, "0", "0", "legacy")
                loggedInAccount = Account(uusname, "0", "0", "0", AccountType.CRACKED, UUID.randomUUID().toString())
            }
        }
        super.actionPerformed(button)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        textField.textboxKeyTyped(typedChar, keyCode)
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
        //TODO SAVE ACCOUNTS
    }
}