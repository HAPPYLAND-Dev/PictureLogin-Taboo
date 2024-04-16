package me.xiaozhangup.picturelogin.data.table

import me.xiaozhangup.picturelogin.MailType
import me.xiaozhangup.picturelogin.data.DatabaseManager
import me.xiaozhangup.picturelogin.data.DatabaseManager.dataSource
import taboolib.module.database.*
import java.util.*

class TableMailbox : SQLTable {

    override val table: Table<Host<SQL>, SQL> = Table("mailbox", DatabaseManager.host) {
        add("uuid") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }

        add("type") {
            type(ColumnTypeSQL.TINYTEXT)
        }

        add("title") {
            type(ColumnTypeSQL.TINYTEXT)
        }

        add("sender") {
            type(ColumnTypeSQL.VARCHAR, 36)
        }

        add("receiver") {
            type(ColumnTypeSQL.VARCHAR, 36)
        }

        add("context") {
            type(ColumnTypeSQL.TEXT)
        }
    }

    fun bySender(sender: UUID, type: MailType? = null): Int {
        return table.select(dataSource) {
            where {
                "sender" eq sender.toString()
                if (type != null) "type" eq type.name
            }
        }.map { }.size
    }

    fun byReceiver(receiver: UUID, type: MailType? = null): Int {
        return table.select(dataSource) {
            where {
                "receiver" eq receiver.toString()
                if (type != null) "type" eq type.name
            }
        }.map { }.size
    }
}