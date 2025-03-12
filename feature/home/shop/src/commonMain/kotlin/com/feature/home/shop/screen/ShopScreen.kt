package com.feature.home.shop.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.feature.home.shop.components.Balance
import com.feature.home.shop.components.Products
import com.feature.home.shop.components.QuicknessFamily
import com.feature.home.shop.components.QuicknessPlus
import com.feature.home.shop.components.QuicknessShop
import com.feature.home.shop.components.QuicknessStudent
import com.feature.home.shop.states.ProductData
import com.feature.home.shop.states.ShopState
import com.shared.resources.drawable.ResourceNameKey
import com.shared.ui.components.component.BottomSheetContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun ShopScreen() = Screen()

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
private fun Screen(viewModel: ShopViewModel = koinViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value

    val productList = ProductList(viewModel, scope, sheetState)

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Balance(credits = "0", viewModel = viewModel)
        Spacer(modifier = Modifier.padding(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            items(productList) { product ->
                Products(
                    text = product.text,
                    icon = viewModel.getDrawable(product.icon.name),
                    containerColor = product.containerColor,
                    iconTint = product.iconTint,
                    colorText = product.colorText,
                    brushStartColor = product.brushStartColor!!,
                    onClick = product.onClickAction
                )
            }
        }
        BottomSheets(
            sheetState = sheetState,
            viewModel = viewModel,
            scope = scope,
            state = state
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductList(
    viewModel: ShopViewModel,
    scope: CoroutineScope,
    sheetState: SheetState
): List<ProductData> = listOf(
    ProductData(
        text = "Quickness Plus",
        icon = ResourceNameKey.LOGOQUICKNESSQC,
        containerColor = colorScheme.primary,
        iconTint = colorScheme.tertiary,
        colorText = colorScheme.tertiary,
        brushStartColor = Color(0xFFb88f14),
        onClickAction = {
            scope.launch {
                viewModel.update { copy(showBottomSheetPlus = true) }
                sheetState.show()
            }
        }
    ),
    ProductData(
        text = "Quickness Student",
        icon = ResourceNameKey.BOOK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        containerColor = colorScheme.primary,
        iconTint = colorScheme.tertiary,
        colorText = colorScheme.tertiary,
        brushStartColor = Color(0xFF1E88E5),
        onClickAction = {
            scope.launch {
                viewModel.update { copy(showBottomSheetStudent = true) }
                sheetState.show()
            }
        }
    ),
    ProductData(
        text = "Quickness Family",
        icon = ResourceNameKey.FAMILY_RESTROOM_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        containerColor = colorScheme.primary,
        iconTint = colorScheme.tertiary,
        colorText = colorScheme.tertiary,
        brushStartColor = Color(0xFF66BB6A),
        onClickAction = {
            scope.launch {
                viewModel.update { copy(showBottomSheetFamily = true) }
                sheetState.show()
            }
        }
    ),
    ProductData(
        text = "Quickness Shop",
        icon = ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
        containerColor = colorScheme.primary,
        iconTint = colorScheme.tertiary,
        colorText = colorScheme.tertiary,
        brushStartColor = Color(0xFF00FF00),
        onClickAction = {
            scope.launch {
                viewModel.update { copy(showBottomSheetShop = true) }
                sheetState.show()
            }
        }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheets(
    sheetState: SheetState,
    viewModel: ShopViewModel,
    scope: CoroutineScope,
    state: ShopState
) {
    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = Color(0xFFFFD700),
        showContent = state.showBottomSheetPlus,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetPlus = false) }
            }
        },
        content = { QuicknessPlus(viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)) }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = Color(0xFF42A5F5),
        showContent = state.showBottomSheetStudent,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetStudent = false) }
            }
        },
        content = { QuicknessStudent(viewModel.getDrawable(ResourceNameKey.BOOK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)) }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = Color(0xFF66BB6A),
        showContent = state.showBottomSheetFamily,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetFamily = false) }
            }
        },
        content = { QuicknessFamily(viewModel.getDrawable(ResourceNameKey.FAMILY_RESTROOM_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)) }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = Color(0xFF00FF00),
        showContent = state.showBottomSheetShop,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetShop = false) }
            }
        },
        content = { QuicknessShop(viewModel.getDrawable(ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24.name)) }
    )
}
