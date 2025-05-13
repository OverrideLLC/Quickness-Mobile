package org.override.quickness.feature.home.shop.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.shop.components.Balance
import org.override.quickness.feature.home.shop.components.Products
import org.override.quickness.feature.home.shop.components.QuicknessFamily
import org.override.quickness.feature.home.shop.components.QuicknessPlus
import org.override.quickness.feature.home.shop.components.QuicknessShop
import org.override.quickness.feature.home.shop.components.QuicknessStudent
import org.override.quickness.feature.home.shop.states.ProductData
import org.override.quickness.feature.home.shop.states.ShopState
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.component.BottomSheetContent

@Composable
fun ShopScreen(
    paddingValues: PaddingValues
) = Screen(paddingValues = paddingValues)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(viewModel: ShopViewModel = koinViewModel(), paddingValues: PaddingValues) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value
    val list = listProducts(viewModel, scope, sheetState)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Balance(
                credits = "100",
                viewModel = viewModel
            )
        }
        item {
            Text(
                text = "Subscriptions",
                color = colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp
            )
            HorizontalDivider(
                color = colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(list) { index ->

            Products(
                text = index.text,
                icon = viewModel.getDrawable(index.icon.name),
                containerColor = index.containerColor,
                iconTint = index.iconTint,
                colorText = index.colorText,
                brushStartColor = index.brushStartColor ?: Color.Transparent,
                onClick = index.onClickAction
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listProducts(
    viewModel: ShopViewModel,
    scope: CoroutineScope,
    sheetState: SheetState
): List<ProductData> = listOf(
    ProductData(
        text = "Plus",
        icon = ResourceNameKey.LOGOQUICKNESSQC,
        containerColor = colorScheme.primary,
        iconTint = Color.White,
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
        text = "Student",
        icon = ResourceNameKey.BOOK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        containerColor = colorScheme.primary,
        iconTint = Color.White,
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
        text = "Family",
        icon = ResourceNameKey.FAMILY_RESTROOM_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        containerColor = colorScheme.primary,
        iconTint = Color.White,
        colorText = colorScheme.tertiary,
        brushStartColor = Color(0xFF66BB6A),
        onClickAction = {
            scope.launch {
                viewModel.update { copy(showBottomSheetFamily = true) }
                sheetState.show()
            }
        }
    ),
)