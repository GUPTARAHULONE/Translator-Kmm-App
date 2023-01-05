package com.example.translator_kmm_app.translate.domain.history

import com.example.translator_kmm_app.core.domain.utils.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
}
