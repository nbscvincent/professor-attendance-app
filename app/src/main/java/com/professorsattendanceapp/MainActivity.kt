package com.professorsattendanceapp


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.professorsattendanceapp.posts.PostsScreen
import com.professorsattendanceapp.theme.NBSCollegeTheme
import com.professorsattendanceapp.viewmodel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val screenViewModel: ScreenViewModel by viewModels {
        AppViewModelProvider.Factory
    }

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Handle double tap back button to close the app
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    backPressedOnce = true

                    // Reset the flag after a delay (e.g., 2 seconds)
                    lifecycleScope.launch {
                        delay(2000)
                        backPressedOnce = false
                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setContent {
            NBSCollegeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    PostsScreen()
                    //Navigation()

                }
            }
        }
    }
}