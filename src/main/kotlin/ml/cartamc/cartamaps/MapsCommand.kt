package ml.cartamc.cartamaps

import com.mattmalec.pterodactyl4j.PteroBuilder
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command

class MapsCommand : Command("maps") {
    fun createServer() {
        val api = PteroBuilder().apply {
            applicationUrl = "https://pt.kliu.io"
            token = ""
        }.build().asApplication()

        val nest = api.retrieveNestById("8").execute()
        val location = api.retrieveLocationById("1").execute()
        val egg = api.retrieveEggById(nest, "27").execute()
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        sender.sendMessage(ComponentBuilder("Hello!").create()[0])
    }
}