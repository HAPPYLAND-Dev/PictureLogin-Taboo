package me.xiaozhangup.picturelogin

import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.VelocityPlugin

@RuntimeDependencies(
    RuntimeDependency(value = "mysql:mysql-connector-java:8.0.30"),
    RuntimeDependency(value = "net.kyori:adventure-text-minimessage:4.12.0")
)
object PictureLogin : Plugin() {

    @JvmStatic
    lateinit var plugin: VelocityPlugin
        private set

    @JvmStatic
    lateinit var pictureLoginJava: PictureLoginJava
        private set

    @JvmStatic
    @Config
    lateinit var config: Configuration
        private set


    override fun onEnable() {
        plugin = VelocityPlugin.getInstance()
        pictureLoginJava = PictureLoginJava(plugin.server, plugin.logger)
    }
}