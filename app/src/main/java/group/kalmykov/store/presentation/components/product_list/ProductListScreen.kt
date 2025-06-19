package group.kalmykov.store.presentation.components.product_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.EntryPointAccessors
import group.kalmykov.data.remote.dto.ProductDto
import group.kalmykov.shared.di.ImageLoaderEntryPoint
import group.kalmykov.shared.di.LocalImageLoader
import group.kalmykov.store.R

@Composable
fun ProductListScreen(
    searchQuery: String,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageLoader = remember(context) {
        EntryPointAccessors.fromApplication(
            context,
            ImageLoaderEntryPoint::class.java
        ).imageLoader()
    }

    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var editingProduct by rememberSaveable { mutableStateOf<ProductDto?>(null) }

    LaunchedEffect(Unit) { viewModel.loadProducts() }

    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { viewModel.loadProducts() }) {
                    Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.refresh))
                }
                Button(onClick = { showAddDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.add_product))
                }
            }

            Spacer(Modifier.height(8.dp))

            when (val state = uiState) {
                is ProductListUiState.Loading -> Box(
                    Modifier.fillMaxSize(),
                    Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is ProductListUiState.Error -> Box(
                    Modifier.fillMaxSize(),
                    Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_prefix, state.message),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is ProductListUiState.Success -> {
                    val products = state.products.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                    if (products.isEmpty()) {
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            Text(stringResource(R.string.no_products))
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(products, key = { it.id }) { prod ->
                                ProductItem(
                                    product = prod,
                                    onDelete = { viewModel.deleteProduct(prod.id) },
                                    onRequestEdit = { editingProduct = prod.copy() }
                                )
                            }
                        }
                    }
                }
            }

            if (showAddDialog) {
                AddProductDialog(
                    onAdd = {
                        viewModel.addProduct(it)
                        showAddDialog = false
                    },
                    onDismiss = { showAddDialog = false }
                )
            }

            editingProduct?.let { prod ->
                EditProductDialog(
                    initialProduct = prod,
                    onUpdate = { updated ->
                        viewModel.updateProduct(updated) { editingProduct = null }
                    },
                    onDismiss = { editingProduct = null }
                )
            }
        }
    }
}
