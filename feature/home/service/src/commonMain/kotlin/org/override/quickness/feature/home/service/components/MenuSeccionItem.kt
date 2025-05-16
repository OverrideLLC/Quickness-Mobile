package org.override.quickness.feature.home.service.components// En tu módulo 'commonMain/kotlin'

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter

data class MenuSeccion(
    val id: String,
    val titulo: String, // Ej: "Desayuno", "Almuerzo", "Postres"
    val alimentos: List<Alimento>,
    val iconoUrl: String? = null // Opcional: para un icono junto al título
)

@Composable
fun MenuSeccionItem(
    seccion: MenuSeccion,
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false // Para controlar si la sección está expandida inicialmente
) {
    var isExpanded by remember { mutableStateOf(initialExpanded) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .animateContentSize( // Animación suave del tamaño del Card
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
            contentColor = colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded } // Expande/contrae al hacer clic
        ) {
            // Encabezado de la sección
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = seccion.titulo,
                    fontSize = 16.sp,
                    color = colorScheme.onSurface
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 200,
                        delayMillis = 100
                    )
                ) + expandVertically(
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
                    expandFrom = Alignment.Top
                ),
                exit = fadeOut(animationSpec = tween(durationMillis = 200)) + shrinkVertically(
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
                    shrinkTowards = Alignment.Top
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    if (seccion.alimentos.isNotEmpty()) {
                        seccion.alimentos.forEach { alimento ->
                            // Usamos el componente TarjetaAlimento que definimos antes
                            // Podrías optar por una representación más simple aquí si lo prefieres
                            TarjetaAlimento(
                                alimento = alimento,
                                onAlimentoClick = {
                                    println("Alimento '${it.nombre}' de la sección '${seccion.titulo}' clickeado.")
                                    // Acción al hacer clic en un alimento específico
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre tarjetas de alimento
                        }
                    } else {
                        Text(
                            text = "No hay alimentos disponibles en esta sección.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- Modelos de datos y componentes de Alimento (copiados para que el preview funcione) ---
// (Asegúrate de que estos estén definidos en tu proyecto real y no duplicados así)
data class Alimento(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val urlImagen: String,
    val categoria: String? = null,
    val valoracion: Float? = null
)

@Composable
fun TarjetaAlimento(
    alimento: Alimento,
    onAlimentoClick: (Alimento) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Reducido el padding para que quepa mejor en la sección
        shape = MaterialTheme.shapes.medium, // Esquinas redondeadas
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = { onAlimentoClick(alimento) }
    ) {
        Row( // Cambiado a Row para una vista más compacta dentro de la sección
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder de Imagen
            Box(
                modifier = Modifier
                    .size(80.dp) // Tamaño más pequeño para la imagen
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(alimento.urlImagen),
                    contentDescription = "Imagen de ${alimento.nombre}",
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) { // Permite que el texto ocupe el espacio restante
                Text(
                    text = alimento.nombre,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}


fun obtenerAlimentosDeEjemplo(): List<Alimento> {
    return listOf(
        Alimento(
            "1",
            "Huevos Benedictinos",
            "Clásicos huevos pochados sobre muffin inglés con salsa holandesa.",
            12.50,
            "https://laroussecocina.mx/wp-content/uploads/2018/01/huevos-benedictinos-001-larousse-cocina-1.jpg.webp",
            "Desayuno",
            4.7f
        ),
        Alimento(
            "2",
            "Chilaquiles Verdes",
            "Totopos de maíz bañados en salsa verde, con crema, queso y cebolla.",
            9.75,
            "https://www.recetasnestle.com.mx/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/e9f0731e2d28969e6d6e9708f78b57af.webp?itok=93dhx4pr",
            "Desayuno",
            4.9f
        ),
        Alimento(
            "3",
            "Avena con Frutas",
            "Avena caliente servida con fresas, plátano y nueces.",
            6.00,
            "https://img.huffingtonpost.es/files/image_720_480/uploads/2024/09/26/avena-con-manzana.jpeg",
            "Desayuno",
            4.5f
        ),
        Alimento(
            "h1",
            "Hamburguesa Clásica",
            "Deliciosa hamburguesa con carne de res, lechuga, tomate y queso.",
            8.99,
            "https://www.recetasnestle.com.mx/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/b870204bfb4f08fd190242e969b19084.webp?itok=8QfwXf_5",
            "Almuerzo",
            4.5f
        ),
        Alimento(
            "p1",
            "Pizza Pepperoni",
            "Pizza tradicional con abundante pepperoni y queso mozzarella.",
            12.50,
            "https://www.thespruceeats.com/thmb/3bDTiWZ5EL0z_MRXkK_VUX1MgC0=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/PizzaSpaghettiPie-LeahMaroney-ee23307805294dedaf94605b2835a032.jpg",
            "Almuerzo",
            4.8f
        ),
        Alimento(
            "s1",
            "Sopa de Tortilla",
            "Caldo de jitomate con tiras de tortilla frita, aguacate, queso y crema.",
            7.00,
            "https://www.mexicoenmicocina.com/wp-content/uploads/2013/10/sopa-de-tortilla-p.jpg",
            "Almuerzo",
            4.6f
        ),
        Alimento(
            "t1",
            "Tiramisú",
            "Postre italiano con capas de bizcocho, café, mascarpone y cacao.",
            6.50,
            "https://www.recetasnestle.com.mx/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/b7449708e28548f4be380e5376cc7a02.webp?itok=AqKw2l0Y",
            "Postres",
            4.9f
        ),
        Alimento(
            "f1",
            "Flan Napolitano",
            "Flan cremoso con caramelo, un clásico de la repostería.",
            5.00,
            "https://www.recetasnestle.com.mx/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/54c6cbcbc6d611e056122d64560cd9c1.webp?itok=yfbrq3Mo",
            "Postres",
            4.7f
        )
    )
}


fun obtenerSeccionesDeMenuEjemplo(): List<MenuSeccion> {
    val todosLosAlimentos = obtenerAlimentosDeEjemplo()
    return listOf(
        MenuSeccion("des", "Desayuno", todosLosAlimentos.filter { it.categoria == "Desayuno" }),
        MenuSeccion("alm", "Almuerzo", todosLosAlimentos.filter { it.categoria == "Almuerzo" }),
        MenuSeccion("cen", "Cena", emptyList()), // Ejemplo de sección vacía
        MenuSeccion("pos", "Postres", todosLosAlimentos.filter { it.categoria == "Postres" })
    )
}