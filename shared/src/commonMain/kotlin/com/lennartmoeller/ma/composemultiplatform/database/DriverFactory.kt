package com.lennartmoeller.ma.composemultiplatform.database

import app.cash.sqldelight.db.SqlDriver

expect object DriverFactory {
    fun createDriver(databaseName: String): SqlDriver
}
