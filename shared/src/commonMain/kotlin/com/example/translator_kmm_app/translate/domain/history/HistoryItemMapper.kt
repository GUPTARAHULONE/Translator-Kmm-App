package com.example.translator_kmm_app.translate.domain.history

import database.HistoryEntity

fun HistoryEntity.toHistoryItem():HistoryItem {
    return HistoryItem(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText
    )
}