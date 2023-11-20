package com.lennartmoeller.ma_compose_multiplatform.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.lennartmoeller.ma_compose_multiplatform.core.appContext

actual object DriverFactory {
    actual fun createDriver(databaseName: String): SqlDriver {
        return AndroidSqliteDriver(FinanceDatabase.Schema, appContext, databaseName)
    }
}
