//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by Rahul Gupta on 24/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class MicrophonePowerObserver: ObservableObject{
    
    // AnyCancellable  is like a flow which can cancelled when we put nil in that
    private var cancellable: AnyCancellable? = nil
       private var audioRecorder: AVAudioRecorder? = nil
       
    // published used so that it can be observed from other class
       @Published private(set) var micPowerRatio = 0.0
       
       private let powerRatioEmissionsPerSecond = 20.0
       
       func startObserving() {
           do {
               //map of string as key
               let recorderSettings: [String: Any] = [
                   AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                   AVNumberOfChannelsKey: 1
               ]
               
               let recorder = try AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null", isDirectory: true), settings: recorderSettings)
               recorder.isMeteringEnabled = true
               recorder.record()
               self.audioRecorder = recorder
               
               //sink for collecting values like flow
               // weak self is used for putting self to null later, becoz MicrophonePowerObserver class can be destroyed before and still cancellable in memory
               self.cancellable = Timer.publish(
                   every: 1.0 / powerRatioEmissionsPerSecond,
                   tolerance: 1.0 / powerRatioEmissionsPerSecond,
                   on: .main,
                   in: .common
               )
               .autoconnect()
               .sink { [weak self] _ in
                   recorder.updateMeters()
                   
                   let powerOffset = recorder.averagePower(forChannel: 0)
                   if powerOffset < -50 {
                       self?.micPowerRatio = 0.0
                   } else {
                       let normalizedOffset = CGFloat(50 + powerOffset) / 50
                       self?.micPowerRatio = normalizedOffset
                   }
               }
           } catch {
               print("An error occurred when observing microphone power: \(error.localizedDescription)")
           }
       }
       
       func release() {
           cancellable = nil
           
           audioRecorder?.stop()
           audioRecorder = nil
           
           micPowerRatio = 0.0
       }
}
