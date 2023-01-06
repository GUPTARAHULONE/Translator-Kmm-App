package com.example.translator_kmm_app.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator_kmm_app.translate.domain.history.HistoryDataSource
import com.example.translator_kmm_app.translate.domain.translate.Translate
import com.example.translator_kmm_app.translate.presentation.TranslateEvent
import com.example.translator_kmm_app.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: Translate,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {
    private val viewModel by lazy {
        TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}
