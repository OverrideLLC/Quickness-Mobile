package org.override.quickness.feature.home.service.lyra

// import androidx.compose.foundation.lazy.LazyRow // No se usa directamente aquí
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.service.components.CalendarCarousel
import org.override.quickness.feature.home.service.components.CaloriesCount
import org.override.quickness.feature.home.service.components.MenuSeccionItem
import org.override.quickness.feature.home.service.components.obtenerSeccionesDeMenuEjemplo

@Composable
fun MacrosItem(name: String, count: Number, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                colorScheme.surfaceContainer,
                CircleShape.copy(all = androidx.compose.foundation.shape.CornerSize(16.dp))
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .size(height = 70.dp, width = 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, fontSize = 14.sp, color = colorScheme.onSurface)
            Text(
                text = "$count%",
                fontSize = 12.sp,
                color = colorScheme.onSurface
            )
        }
    }
}

@Composable
fun LyraRoot(viewModel: LyraViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LyraScreen(
        state = state,
        viewModel = viewModel,
        onAction = viewModel::onAction,
    )
}

@Composable
fun LyraScreen(
    state: LyraState,
    viewModel: LyraViewModel, // viewModel no se usa directamente en este Composable, considerar si es necesario pasarlo
    onAction: (LyraAction) -> Unit,
) {
    val itemsPerPage = 3
    val pagedMacros = state.macros.toList().chunked(itemsPerPage)
    val actualPageCount = pagedMacros.size
    val pagerState = rememberPagerState(pageCount = { actualPageCount })
    val color = colorScheme.surfaceContainer

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            CalendarCarousel(
                year = state.currentDay.year,
                month = state.currentDay.monthNumber,
                viewModel = viewModel,
                color = color,
                onDaySelected = { day -> onAction(LyraAction.OnDaySelected(day)) }
            )
        }
        item {
            CaloriesCount(calories = 2000)
        }
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) { // Envuelve Pager y DotsIndicator
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) { pageIndex ->
                    if (actualPageCount > 0 && pageIndex < actualPageCount) {
                        val currentPageItems = pagedMacros[pageIndex]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            // Distribuye los items equitativamente en la fila
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            currentPageItems.forEach { macro ->
                                MacrosItem(
                                    name = macro.first,
                                    count = macro.second,
                                    // Cada item toma peso 1 para distribuirse y tiene un pequeño padding
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp)
                                )
                            }
                            // Rellena con Spacers si hay menos de 'itemsPerPage' en la última página
                            // para mantener la consistencia de tamaño de los items visibles.
                            if (currentPageItems.size < itemsPerPage) {
                                for (i in 0 until (itemsPerPage - currentPageItems.size)) {
                                    Spacer(Modifier.weight(1f).padding(horizontal = 4.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // El indicador de puntos debe usar el número de páginas real
                if (actualPageCount > 1) { // Solo mostrar puntos si hay más de una página
                    DotsIndicator(
                        totalDots = actualPageCount, // Usar el recuento de páginas correcto
                        selectedIndex = pagerState.currentPage,
                        selectedColor = colorScheme.primary, // Usar colores del tema
                        unselectedColor = colorScheme.onSurface.copy(alpha = 0.3f), // Usar colores del tema
                        dotSize = 8.dp,
                        spacing = 6.dp // Un poco más de espaciado entre puntos
                    )
                }
            }
        }
        item {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 20.sp,
                color = colorScheme.onSurface
            )
            HorizontalDivider(
                color = colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(obtenerSeccionesDeMenuEjemplo()) { seccion ->
            MenuSeccionItem(
                seccion = seccion,
            )
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unselectedColor: Color,
    dotSize: Dp,
    spacing: Dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) selectedColor else unselectedColor
            val sizeFactor =
                if (index == selectedIndex) 1.2f else 1.0f // Opcional: hacer el punto actual un poco más grande
            Box(
                modifier = Modifier
                    .size(dotSize * sizeFactor)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}