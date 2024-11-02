package com.doggies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.doggies.home.HomeScreen
import com.doggies.ui.theme.DoggiesTheme

class MainActivity : ComponentActivity() {
    private var dataLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            !dataLoaded
        }
        enableEdgeToEdge()
        setContent {
            DoggiesTheme {
                HomeScreen{
                    dataLoaded = it
                }
            }
        }
    }
}
