package group.kalmykov.store.presentation.screens.cart_screen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Cart Screen", style = MaterialTheme.typography.headlineMedium)
    }
}