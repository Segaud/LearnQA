package com.segaud.learnqa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.segaud.learnqa.ui.AppShell
import com.segaud.learnqa.ui.theme.LearnQATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearnQATheme {
                AppShell()
            }
        }
    }
}