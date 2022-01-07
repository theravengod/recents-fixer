package kitty.cheshire.recents.fixer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import kitty.cheshire.recents.fixer.ui.screens.MainScreenContent
import kitty.cheshire.recents.fixer.ui.theme.RecentsFixerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecentsFixerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreenContent()
                }
            }
        }
    }
}