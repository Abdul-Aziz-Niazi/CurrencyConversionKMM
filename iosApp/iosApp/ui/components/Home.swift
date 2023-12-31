//
//  Home.swift
//  iosApp
//
//  Created by Abdul Aziz on 27/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Combine

struct Home:View {
    @EnvironmentObject var viewModel:MainViewModel
    @State private var text: String = ""
    @State var showAlert = false
    @State var selectedCurrency:String = "USD"
    var items = ["Item 1", "Item 2", "Item 3"]
    var selectedCurrencyRateData: CurrencyRateData? {
        return viewModel.currencyData?.first(where: { $0.currencyName as String == selectedCurrency })
        }
    

    var body: some View {
        VStack(){
            Text("Currency Conversion 💹")
                .frame(maxWidth: .infinity, maxHeight: CGFloat(56))
                .background(Color(hex: "#0062cc"))
                .foregroundColor(.white)
                .font(.system(size: 20, weight: Font.Weight.bold))
            TextField("Amount in USD", text: Binding(

                get:{
                    text
                },
                set:{ newValue in
                    let pattern = "(\\.[^.]*){2,}"
                    let regex = try! NSRegularExpression(pattern: pattern, options: [])
                    let range = NSRange(location: 0, length: newValue.utf16.count)
                    let filtered = regex.stringByReplacingMatches(in: newValue, options: [], range: range, withTemplate: ".")
                    print("\(newValue)  \(filtered)")
                    text = filtered
                }
            ))
            .padding()
            .frame(height: 55)
            .textFieldStyle(PlainTextFieldStyle())
            .background(
                RoundedRectangle(cornerRadius: 4, style: .continuous)
                    .stroke(Color.gray, lineWidth: 1)
            )
            .keyboardType(.decimalPad)
            .onChange(of:text) { newValue in

            }
            .padding(.horizontal)
            
            HStack(){
                Button(action: {
                    showAlert = true
                }) {
                    Text("🟡 Selected \(selectedCurrency)").font(.system(size:14, weight: Font.Weight.medium)).padding(4)
                }.sheet(isPresented: $showAlert) {
                    VStack{
                        Text("Currency").foregroundColor(.black).font(.system(size:18, weight:Font.Weight.bold))
                        Text("Select the base currency").foregroundColor(.black)
                        List(viewModel.getList(), id: \.self){ item in
                            Button(action:{
                                showAlert = false
                                selectedCurrency = item
                            }){
                                Text(item).foregroundColor(.black)
                            }
                        }
                    }
                }.buttonStyle(BorderedButtonStyle())
                    .foregroundColor(.white)
                    .background(Color(hex: ColorKt.purple500))
                    .cornerRadius(4)
                Spacer()
            }.padding(.vertical,4).padding(.horizontal)
            
            List(viewModel.currencyData ?? [], id: \.currencyName) { item in
                HStack{
                    let textVar = !text.isEmpty && text.allSatisfy { $0.isNumber || $0 == "." || $0 == "-" } ? text : "1"
                    let mCurrencyRate:CurrencyRateData = selectedCurrencyRateData ?? item
                    let rate = CurrencyUtils.shared.convertRate(rate:Double(truncating:item.rate ?? 0), amount: textVar, selectedCurrency:mCurrencyRate)
                    Text("\(rate)")

                    Spacer()
                    Text(item.currencyName)
                }
            }
            .listStyle(PlainListStyle())
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.white)
        .onAppear{
            viewModel.refreshData()
        }
    }
    
  
}
