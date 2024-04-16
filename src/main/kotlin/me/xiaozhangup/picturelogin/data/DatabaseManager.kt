package me.xiaozhangup.picturelogin.data

import me.xiaozhangup.picturelogin.PictureLogin
import me.xiaozhangup.picturelogin.data.table.TableMailbox
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.SkipTo
import taboolib.module.database.HostSQL
import javax.sql.DataSource

@SkipTo(LifeCycle.ENABLE)
object DatabaseManager {

    lateinit var host: HostSQL
        private set
    lateinit var dataSource: DataSource
        private set

    @JvmStatic
    lateinit var tableMailbox: TableMailbox
        private set

    private val databaseConfig: taboolib.library.configuration.ConfigurationSection by lazy {
        PictureLogin.config.getConfigurationSection("database")
            ?: throw RuntimeException("Config 'database' does not exist.")
    }


    @Awake(LifeCycle.ENABLE)
    fun init() {
        host = HostSQL(databaseConfig)
        dataSource = host.createDataSource()

        tableMailbox = TableMailbox().apply { createTable() }
    }

}