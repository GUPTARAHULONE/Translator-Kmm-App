package com.example.translator_kmm_app.translate.domain.history

import com.example.translator_kmm_app.core.domain.utils.CommonFlow
import com.example.translator_kmm_app.core.domain.utils.toCommonFlow
import com.example.translator_kmm_app.database.TranslateDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    private val db: TranslateDatabase
) : HistoryDataSource {
    private val queries = db.translateQueries
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            item.id,
            item.fromLanguageCode,
            item.fromText,
            item.toLanguageCode,
            item.toText,
            Clock.System.now().toEpochMilliseconds()
        )
    }
}
