//
//  MainViewModel.swift
//  iosApp
//
//  Created by Abdul Aziz on 27/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class MainViewModel: ObservableObject{
    
    let useCase = DefaultCurrencyUseCase()
    @Published var currencyData:[CurrencyRateData]? = [CurrencyRateData]()
    @Published var errorText:String? = ""
    
    func getList()->[String]{
        return CurrencyUtils.shared.selectableCurrency
    }
    func refreshData(){
        let flow = useCase.getAllRates()
        FlowExtensionKt.asCompletion(flow, result: {result, error in
            if error == nil{
                self.currencyData = result as? [CurrencyRateData]
            } else {
                self.errorText = error
            }
        })
    }
}
