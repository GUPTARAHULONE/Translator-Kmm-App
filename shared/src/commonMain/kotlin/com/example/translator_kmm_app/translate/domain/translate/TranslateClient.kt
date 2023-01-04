package com.example.translator_kmm_app.translate.domain.translate

import com.example.translator_kmm_app.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}
