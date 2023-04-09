package me.xiaozhangup.picturelogin.data.table

import me.xiaozhangup.picturelogin.data.DatabaseManager
import me.xiaozhangup.picturelogin.data.DatabaseManager.dataSource
import me.xiaozhangup.picturelogin.data.table.SQLTable
import taboolib.module.database.*

class TableMail : SQLTable {

    override val table: Table<Host<SQL>, SQL> = Table("mail_mail", DatabaseManager.host) {
        add("id") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }

        add("from") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }

        add("to") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }

        add("mail") {
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