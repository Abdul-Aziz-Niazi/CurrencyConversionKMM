package com.abdulaziz.currencyconversion.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdulaziz.currencyconversion.android.theme.AppStyles
import com.abdulaziz.currencyconversion.android.theme.fromHex
import com.abdulaziz.currencyconversion.android.ui.viewmodels.MainViewModel
import com.abdulaziz.currencyconversion.data.model.CurrencyRateData
import com.abdulaziz.currencyconversion.theme.purple500
import kotlinx.coroutines.flow.collectLatest


@Composable
fun Home() {
    val mainViewModel: MainViewModel = viewModel()
    val state = mainViewModel.homeState.collectAsState()
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var currencyData by rememberSaveable { mutableStateOf(listOf<CurrencyRateData>()) }
    var selectedCurrency by rememberSaveable { mutableStateOf(MainViewModel.DEFAULT_CURRENCY) }

    LaunchedEffect(mainViewModel) {
        mainViewModel.init(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp).padding(horizontal = 8.dp)
    ) {

        LaunchedEffect(key1 = mainViewModel) {
            mainViewModel.homeState.collectLatest {
                selectedCurrency = state.value.baseCurrency
                currencyData = state.value.currencyData
            }
        }

        CurrencyTextField(mainViewModel, selectedCurrency)

        Button(
            onClick = {
                showDialog = true
            }, modifier = Modifier
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.fromHex(purple500))
        ) {
            Text(text = "Selected $selectedCurrency", style = AppStyles.textBodySemiBold)
        }

        CurrencyList(currencyData, mainViewModel, selectedCurrency)
        if (showDialog) {
            AlertDialog(onDismissRequest = {
                showDialog = false
            }, title = { Text(text = "Currency") }, text = { Text(text = "Select the base currency") }, buttons = {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp)
                ) {
                    items(items = mainViewModel.currencyList) {
                        Text(text = it, modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                mainViewModel.updateBaseCurrency(it)
                                showDialog = false
                            })
                        Divider()
                    }
                }
            })
        }
    }
}

@Composable
private fun CurrencyTextField(
    mainViewModel: MainViewModel,
    selectedCurrency: String,
) {
    TextField(
        value = mainViewModel.baseValue,
        onValueChange = {
            mainViewModel.baseValue = mainViewModel.updatedValue(it)
        }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
        label = { Text(text = "Amount in $selectedCurrency") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            backgroundColor = Color.White,
        ),
        shape = RoundedCornerShape(4.dp),
    )
}

@Composable
private fun CurrencyList(
    currencyData: List<CurrencyRateData>,
    mainViewModel: MainViewModel,
    selectedCurrency: String
) {
    LazyColumn {
        items(items = currencyData) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White)
            ) {
                val currencyRate =
                    currencyData.find { it.currencyName == selectedCurrency } ?: throw IllegalArgumentException("Selected currency not found")
                Text(
                    text = mainViewModel.convertRate(it.rate, currencyRate).value,
                    style = AppStyles.textBody,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(text = it.currencyName, style = AppStyles.textBody, modifier = Modifier.align(Alignment.CenterEnd))
            }
            Divider()
        }
    }
}