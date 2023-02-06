package com.example.kmm_translator.voice_to_text.domain

import com.example.kmm_translator.core.domain.utils.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}