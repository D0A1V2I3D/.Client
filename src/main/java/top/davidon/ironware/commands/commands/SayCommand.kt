package top.davidon.ironware.commands.commands

import top.davidon.ironware.commands.Command
import top.davidon.ironware.utils.Utils

class SayCommand : Command("say", "Says anythink in chat", "say <message>", listOf("s", "say")) {
    override fun onCommand(args: Array<String>, command: String) {
        val message = command.replace(command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0], "")
        if (message.equals("", ignoreCase = true)) return
        Utils.sendMessageToServer(message)
    }
}