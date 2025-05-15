package org.override.quickness.feature.home.service.lyra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column // Importado para MacrosItem de ejemplo
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
// import androidx.compose.foundation.lazy.LazyRow // No se usa directamente aquí
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text // Importado para MacrosItem de ejemplo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Importado para MacrosItem de ejemplo
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.service.components.CalendarCarousel
import org.override.quickness.feature.home.service.components.CaloriesCount
// Asumiendo que MacrosItem es algo como esto, lo defino aquí para que compile
// Deberías usar tu propia implementación de MacrosItem
// import org.override.quickness.feature.home.service.components.MacrosItem

// ------ Inicio: Implementación de ejemplo de MacrosItem ------
// Si ya tienes MacrosItem definido en otro lugar, puedes borrar esto.
// Esto es solo para que el código sea compilable y para aplicar el Modifier.weight.
@Composable
fun MacrosItem(name: String, count: Number, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier // El modifier se pasa desde la llamada
            .background(colorScheme.surfaceVariant, CircleShape.copy(all = androidx.compose.foundation.shape.CornerSize(16.dp)))
            .padding(vertical = 16.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, fontSize = 14.sp, color = colorScheme.onSurfaceVariant)
            Text(text = "$count%", fontSize = 12.sp, color = colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
        }
    }
}
// ------ Fin: Implementación de ejemplo de MacrosItem ------


@Composable
fun LyraRoot(
    viewModel: LyraViewModel = koinViewModel(),
    paddingValues: PaddingValues // paddingValues se recibe aquí
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LyraScreen(
        state = state,
        paddingValues = paddingValues, // y se pasa a LyraScreen
        viewModel = viewModel,
        onAction = viewModel::onAction,
    )
}

@Composable
fun LyraScreen(
    state: LyraState,
    paddingValues: PaddingValues, // paddingValues se recibe aquí
    viewModel: LyraViewModel, // viewModel no se usa directamente en este Composable, considerar si es necesario pasarlo
    onAction: (LyraAction) -> Unit,
) {
    val itemsPerPage = 3
    // Agrupa los macros en listas de 'itemsPerPage'
    val pagedMacros = state.macros.toList().chunked(itemsPerPage)
    // El número de páginas es el tamaño de la lista de grupos
    val actualPageCount = pagedMacros.size

    // Asegúrate de que el PagerState use el recuento de páginas correcto
    val pagerState = rememberPagerState(pageCount = { actualPageCount })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(), // Aplicar los paddingValues aquí
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            CalendarCarousel(
                year = state.currentDay.year,
                month = state.currentDay.monthNumber,
                viewModel = viewModel,
                color = colorScheme.surfaceContainer,
                onDaySelected = { day -> onAction(LyraAction.OnDaySelected(day)) }
            )
        }
        item {
            CaloriesCount(calories = 2000) // Considera obtener las calorías del 'state'
        }
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) { // Envuelve Pager y DotsIndicator
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp) // Añade un poco de padding horizontal al Pager
                    // pageSpacing = 8.dp // Espacio opcional entre páginas enteras
                ) { pageIndex ->
                    // Obtiene los items para la página actual
                    // Es importante manejar el caso donde pagedMacros podría estar vacío
                    // o pageIndex podría estar fuera de los límites si actualPageCount es 0.
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
            val sizeFactor = if (index == selectedIndex) 1.2f else 1.0f // Opcional: hacer el punto actual un poco más grande
            Box(
                modifier = Modifier
                    .size(dotSize * sizeFactor)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}