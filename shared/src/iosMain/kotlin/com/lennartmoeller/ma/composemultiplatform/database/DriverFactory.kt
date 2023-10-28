package com.lennartmoeller.ma.composemultiplatform.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual object DriverFactory {
    actual fun createDriver(databaseName: String): SqlDriver {
        return NativeSqliteDriver(FinanceDatabase.Schema, databaseName)
    }
}
