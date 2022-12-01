package top.davidon.ironware.commands.commands

import org.lwjgl.opengl.Display
import top.davidon.ironware.Client
import top.davidon.ironware.commands.Command
import top.davidon.ironware.utils.Utils

class ClientNameCommand :
    Command("ClientName", "Changes the name in watermark", "clientname <name>", listOf("cl", "clientname")) {
    override fun onCommand(args: Array<String>, command: String) {
        val old = Client.name
        Client.name = command.replace(command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0], "")
        Display.setTitle(Client.name)
        Utils.sendClientMessage(String.format("Client name set from %s to %s", old, Client.name))
    }
}