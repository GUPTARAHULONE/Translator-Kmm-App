package com.example.translator_kmm_app.translate.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create():SqlDriver
}