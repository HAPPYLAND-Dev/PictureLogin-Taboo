package me.xiaozhangup.picturelogin.data.table

import me.xiaozhangup.picturelogin.data.DatabaseManager
import taboolib.module.database.Host
import taboolib.module.database.SQL
import taboolib.module.database.Table

interface SQLTable {

    val table: Table<Host<SQL>, SQL>


    fun createTable() {
        table.createTable(DatabaseManager.dataSource)
    }

}