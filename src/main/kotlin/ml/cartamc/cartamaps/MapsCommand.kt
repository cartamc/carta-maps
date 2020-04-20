package ml.cartamc.cartamaps

import com.mattmalec.pterodactyl4j.DataType
import com.mattmalec.pterodactyl4j.PowerAction
import com.mattmalec.pterodactyl4j.PteroBuilder
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.logging.Logger


class MapsCommand(private val logger: Logger, private val ptUrl: String, private val token: String) : Command("maps") {
    private val app = PteroBuilder().apply {
        applicationUrl = applicationUrl
        token = token
    }.build().asApplication()
    private val client = PteroBuilder().apply {
        applicationUrl = applicationUrl
        token = token
    }.build().asClient()

    /**
     * Creates and installs a server that is set not to start upon completion.
     */
    private fun createServer(id: String): ApplicationServer {
        val env = mapOf<String, String>(
                "SERVER_JARFILE" to "server.jar",
                "MOTD" to "",
                "MAXPLAYERS" to "10",
                "VERSION" to "1.15.2",
                "TYPE" to "vanilla")
        val portRange = setOf("25565")

        val nest = app.retrieveNestById("8").execute()
        val location = app.retrieveLocationById("1").execute()
        val egg = app.retrieveEggById(nest, "27").execute()
        val action = app.createServer().setName(id)
                .setDescription("Carta Maps autogenerated")
                .setOwner(app.retrieveUserById("1").execute())
                .setEgg(egg)
                .setLocations(Collections.singleton(location))
                .setAllocations(0L)
                .setDatabases(0L)
                .setCPU(0L)
                .setDisk(3L, DataType.GB)
                .setMemory(1L, DataType.GB)
                .setDockerImage("quay.io/pterodactyl/core:java")
                .setDedicatedIP(false)
                .setPortRange(portRange)
                .startOnCompletion(false)
                .setEnvironment(env)
                .build()
        val server = action.execute();
        return server
    }

    private fun create(sender: CommandSender, url: String, version: String) {
        val downloadFile = createTempFile()
        val conn = (URL(url).openConnection() as HttpURLConnection).apply {
            instanceFollowRedirects = true
        }
        try {
            sender.sendMessage(ComponentBuilder("Downloading...").create()[0])
            conn.connect()
            Files.copy(conn.inputStream,
                    downloadFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            logger.severe(e.toString())
            return
        } finally {
            conn.disconnect()
        }

        var server = createServer("test")
        while (!server.container.isInstalled) {
            Thread.sleep(1000)

        }
        server.container.isInstalled
        client.setPower(
                client.retrieveServerByIdentifier(server.identifier).execute(),
                PowerAction.START)
                .execute()

        unzip(downloadFile.path, "/servers/${server.uuid}/world")

        sender.sendMessage(ComponentBuilder("Hello!").create()[0])
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        when (args[0]) {
            "create" -> {
                create(sender, args[1], args[2])
            }
        }
    }
}