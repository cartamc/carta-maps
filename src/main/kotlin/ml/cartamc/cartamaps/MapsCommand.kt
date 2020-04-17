package ml.cartamc.cartamaps

import com.mattmalec.pterodactyl4j.PteroBuilder
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command

class MapsCommand(private val ptUrl: String, private val token: String) : Command("maps") {
    fun createServer() {
        val api = PteroBuilder().apply {
            applicationUrl = applicationUrl
            token = token
        }.build().asApplication()

        val nest = api.retrieveNestById("8").execute()
        val location = api.retrieveLocationById("1").execute()
        val egg = api.retrieveEggById(nest, "27").execute()
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        sender.sendMessage(ComponentBuilder("Hello!").create()[0])
    }
}