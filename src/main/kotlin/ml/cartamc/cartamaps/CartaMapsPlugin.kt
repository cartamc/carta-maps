@file:Suppress("unused")

package ml.cartamc.cartamaps

import net.md_5.bungee.api.plugin.Plugin

class CartaMapsPlugin : Plugin() {
    override fun onEnable() {
        logger.info("Yay! It loads!")
        proxy.pluginManager.registerCommand(this, MapsCommand())
    }
}