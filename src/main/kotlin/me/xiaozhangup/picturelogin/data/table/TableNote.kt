package me.xiaozhangup.picturelogin.data.table

import me.xiaozhangup.picturelogin.data.DatabaseManager
import me.xiaozhangup.picturelogin.data.DatabaseManager.dataSource
import taboolib.common.util.asList
import taboolib.module.database.*

class TableNote : SQLTable {

    override val table: Table<Host<SQL>, SQL> = Table("mail_note", DatabaseManager.host) {
        add("id") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }

        add("from") {
            type(ColumnTypeSQL.VARCHAR, 36)
        }

        add("to") {
            type(ColumnTypeSQL.VARCHAR, 36)
        }

        add("note") {
            type(ColumnTypeSQL.TEXT)
        }
    }


    fun getByFrom(uuid: String): Int {
        var n = 0
        table.select(dataSource) {
            where("from" eq uuid)
        }.forEach {
            n++
        }
        return n
    }

    fun getByTo(uuid: String): Int {
        var n = 0
        table.select(dataSource) {
            where("to" eq uuid)
        }.forEach {
            n++
        }
        return n
    }

}