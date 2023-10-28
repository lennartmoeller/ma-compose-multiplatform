package com.lennartmoeller.ma.composemultiplatform.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual object DriverFactory {
    actual fun createDriver(databaseName: String): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        FinanceDatabase.Schema.create(driver)
        return driver
    }
}
