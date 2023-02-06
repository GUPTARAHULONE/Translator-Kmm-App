package com.example.kmm_translator.di

import com.example.kmm_translator.database.TranslateDatabase
import com.example.kmm_translator.translate.data.history.SqlDelightHistoryDataSource
import com.example.kmm_translator.translate.data.local.DatabaseDriverFactory
import com.example.kmm_translator.translate.data.remote.HttpClientFactory
import com.example.kmm_translator.translate.data.translate.KtorTranslateClient
import com.example.kmm_translator.translate.domain.history.HistoryDataSource
import com.example.kmm_translator.translate.domain.translate.Translate
import com.example.kmm_translator.translate.domain.translate.TranslateClient
import io.ktor.client.*

class AppModule {
    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUsecase: Translate by lazy {
        Translate(translateClient, historyDataSource)
    }
}
