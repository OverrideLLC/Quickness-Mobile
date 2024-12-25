package org.quickness.ui.screens.home.shop

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.ui.components.models.Balance
import org.quickness.ui.components.component.BottomSheetContent
import org.quickness.ui.components.models.Products
import org.quickness.ui.components.models.QuicknessFamily
import org.quickness.ui.components.models.QuicknessPlus
import org.quickness.ui.components.models.QuicknessShop
import org.quickness.ui.components.models.QuicknessStudent
import org.quickness.ui.theme.Success
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24

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
        Balance("0")
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
                    icon = product.icon,
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
        icon = Res.drawable.LogoQuicknessQC,
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
        icon = Res.drawable.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
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
        icon = Res.drawable.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
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
        icon = Res.drawable.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
        containerColor = colorScheme.primary,
        iconTint = colorScheme.tertiary,
        colorText = colorScheme.tertiary,
        brushStartColor = Success,
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
        content = { QuicknessPlus() }
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
        content = { QuicknessStudent() }
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
        content = { QuicknessFamily() }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = Success,
        showContent = state.showBottomSheetShop,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetShop = false) }
            }
        },
        content = { QuicknessShop() }
    )
}
