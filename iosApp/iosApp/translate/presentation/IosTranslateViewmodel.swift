//
//  IosTranslateViewmodel.swift
//  iosApp
//
//  Created by Rahul Gupta on 11/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen {
    @MainActor class IosTranslateViewmodel : ObservableObject {
        private var historyDataSource: HistoryDataSource
        private var translateUsecase: Translate
        
        private var viewModel: TranslateViewModel
        // why cant share this code
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating: false,
            fromLanguage: UiLanguage(language : .english, imageName: ".english"),
            toLanguage: UiLanguage(language : .german, imageName: ".german"),
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            history: []
       )
                    
        private var handle: DisposableHandle?
        
        init(historyDataSource: HistoryDataSource, translateUsecase: Translate) {
            self.historyDataSource = historyDataSource
            self.translateUsecase = translateUsecase
            self.viewModel=TranslateViewModel(translate: translateUsecase, historyDataSource: historyDataSource, coroutineScope: nil)
        }

        
        func onEvent(event: TranslateEvent){
            self.viewModel.onEvent(event: event)
        }
        
        func onStartOberving(){
            handle = viewModel.state.subscribe(
                onCollect: { state in
                    if let state = state{
                        self.state = state
                    }
                })
        }
        
        func dispose(){
            handle?.dispose()
        }
    }
}
