package com.lennartmoeller.ma_compose_multiplatform.database

import com.lennartmoeller.ma_compose_multiplatform.entities.Account
import com.lennartmoeller.ma_compose_multiplatform.entities.Category
import com.lennartmoeller.ma_compose_multiplatform.entities.Data
import com.lennartmoeller.ma_compose_multiplatform.entities.Icon
import com.lennartmoeller.ma_compose_multiplatform.entities.Transaction

class Database {
    companion object {
        private val sqlDriver = DriverFactory.createDriver("finance.db")
        private val database = FinanceDatabase(sqlDriver)
        private val query = database.financeDatabaseQueries

        fun build(initialData: Data) {
            query.transaction {
                // clear the whole database
                query.clearAccounts()
                query.clearCategories()
                query.clearIcons()
                query.clearTransactions()
                // insert the initial data
                initialData.accounts.values.forEach { account ->
                    insertAccount(account)
                }
                initialData.categories.values.forEach { category ->
                    insertCategory(category)
                }
                initialData.icons.values.forEach { icon ->
                    insertIcon(icon)
                }
                initialData.transactions.values.forEach { transaction ->
                    insertTransaction(transaction)
                }
            }
        }

        fun getAccounts(): Map<Int, Account> {
            return query.getAccounts { id, label, startBalance, icon ->
                Account(
                    id = id.toInt(), label = label, startBalance = startBalance.toInt(), icon = icon
                )
            }.executeAsList().associateBy { it.id }
        }

        fun getCategories(): Map<Int, Category> {
            return query.getCategories { id, label, type, icon ->
                Category(
                    id = id.toInt(),
                    label = label,
                    type = type.toInt(),
                    icon = icon,
                )
            }.executeAsList().associateBy { it.id }
        }

        fun getIconUnicode(name: String): String? {
            return query.getIconUnicode(name).executeAsOneOrNull()
        }

        fun getTransactions(): Map<Int, Transaction> {
            return query.getTransactions { id, date, account, category, description, amount ->
                Transaction(
                    id = id.toInt(),
                    date = date,
                    account = account.toInt(),
                    category = category.toInt(),
                    description = description ?: "",
                    amount = amount.toInt()
                )
            }.executeAsList().associateBy { it.id }
        }

        fun insertAccount(account: Account) {
            query.insertAccount(
                id = account.id.toLong(),
                label = account.label,
                start_balance = account.startBalance.toLong(),
                icon = account.icon,
            )
        }

        fun insertCategory(category: Category) {
            query.insertCategory(
                id = category.id.toLong(),
                label = category.label,
                type = category.type.toLong(),
                icon = category.icon,
            )
        }

        fun insertIcon(icon: Icon) {
            query.insertIcon(
                name = icon.name,
                unicode = icon.unicode,
            )
        }

        fun insertTransaction(transaction: Transaction) {
            query.insertTransaction(
                id = transaction.id.toLong(),
                date = transaction.date,
                account = transaction.account.toLong(),
                category = transaction.category.toLong(),
                description = transaction.description,
                amount = transaction.amount.toLong()
            )
        }
    }
}
