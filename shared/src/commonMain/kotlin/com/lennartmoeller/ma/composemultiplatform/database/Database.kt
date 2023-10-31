package com.lennartmoeller.ma.composemultiplatform.database

import com.lennartmoeller.ma.composemultiplatform.entities.Account
import com.lennartmoeller.ma.composemultiplatform.entities.Category
import com.lennartmoeller.ma.composemultiplatform.entities.Data
import com.lennartmoeller.ma.composemultiplatform.entities.Transaction

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
                query.clearTransactions()
                // insert the initial data
                initialData.accounts.values.forEach { account ->
                    insertAccount(account)
                }
                initialData.categories.values.forEach { category ->
                    insertCategory(category)
                }
                initialData.transactions.values.forEach { transaction ->
                    insertTransaction(transaction)
                }
                // TODO: Add insert categories, insert transactions
            }
        }

        fun getAccounts(): Map<Int, Account> {
            return query.getAccounts { id, label, startBalance ->
                Account(
                    id = id.toInt(), label = label, startBalance = startBalance.toInt()
                )
            }.executeAsList().associateBy { it.id }
        }

        fun getCategories(): Map<Int, Category> {
            return query.getCategories { id, label, type ->
                Category(
                    id = id.toInt(),
                    label = label,
                    type = type.toInt()
                )
            }.executeAsList().associateBy { it.id }
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
            )
        }

        fun insertCategory(category: Category) {
            query.insertCategory(
                id = category.id.toLong(),
                label = category.label,
                type = category.type.toLong(),
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
