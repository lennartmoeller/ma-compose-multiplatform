package com.lennartmoeller.ma_compose_multiplatform.database

import app.cash.sqldelight.db.SqlDriver

expect object DriverFactory {
    fun createDriver(databaseName: String): SqlDriver
}
