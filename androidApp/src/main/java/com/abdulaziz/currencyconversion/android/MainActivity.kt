package com.abdulaziz.currencyconversion.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.abdulaziz.currencyconversion.android.theme.AppStyles
import com.abdulaziz.currencyconversion.android.theme.MyApplicationTheme
import com.abdulaziz.currencyconversion.android.theme.fromHex
import com.abdulaziz.currencyconversion.android.ui.screen.Home
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            MaterialTheme {
                systemUiController.setSystemBarsColor(
                    color = Color.fromHex("#0063cc")
                )
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.fromHex("#0063cc")),
                    topBar = {
                        TopBar()
                    },
                    backgroundColor = Color.White
                ) { _ ->
                    Home()
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name) +" \uD83D\uDCB9",
                    style = AppStyles.textSubtitle.copy(color = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            backgroundColor =Color.fromHex("#0063cc"),
            contentColor = Color.White
        )
    }
}


@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
