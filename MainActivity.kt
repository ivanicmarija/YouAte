package hr.ferit.marijaivanic.youate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import hr.ferit.marijaivanic.youate.ui.NavigationController
import hr.ferit.marijaivanic.youate.ui.YouAteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<YouAteViewModel>()
        setContent {
            NavigationController(viewModel)
        }
    }
}