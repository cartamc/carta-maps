@file:Suppress("unused")

package ml.cartamc.cartamaps

import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.nio.file.Files


class CartaMapsPlugin : Plugin() {
    private fun initializeConfiguration(): Configuration {
        if (!dataFolder.exists()) dataFolder.mkdir()

        val file = File(dataFolder, "config.yml")

        if (!file.exists()) {
            try {
                getResourceAsStream("config.yml").use {
                    Files.copy(it, file.toPath())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return ConfigurationProvider.getProvider(
                YamlConfiguration::class.java).load(file)
    }

    override fun onEnable() {
        logger.info("Initializing universes...")
        val cfg = initializeConfiguration()
        proxy.pluginManager.registerCommand(this, MapsCommand(
                logger, cfg.getString("ptUrl"), cfg.getString("token")))
    }
}