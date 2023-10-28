package com.lennartmoeller.ma.composemultiplatform.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.lennartmoeller.ma.composemultiplatform.core.appContext

actual object DriverFactory {
    actual fun createDriver(databaseName: String): SqlDriver {
        return AndroidSqliteDriver(FinanceDatabase.Schema, appContext, databaseName)
    }
}
