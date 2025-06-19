package group.kalmykov.store.presentation.screens.home_screen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import group.kalmykov.store.presentation.components.product_list.ProductListScreen
import group.kalmykov.store.presentation.components.search_filed.SearchField

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ProductListScreen(searchQuery = searchQuery)
        }
    }
}
