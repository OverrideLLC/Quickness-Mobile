package org.override.quickness.feature.home.service.apollo

import androidx.compose.runtime.Immutable
import org.override.quickness.feature.home.service.apollo.utils.AnnouncementData
import org.override.quickness.feature.home.service.apollo.utils.CourseData
import org.override.quickness.feature.home.service.apollo.utils.WorkData

@Immutable
data class ApolloState(
    val isLoading: Boolean = false,
    val courses: List<CourseData> = listOf(
        CourseData(
            title = "Matemáticas Discretas",
            degree = "3er Semestre",
            career = "Ingeniería de Software",
            section = "A",
            works = listOf(
                WorkData(
                    title = "Tarea 1: Tablas de Verdad",
                    description = "Resolver los ejercicios 1.1 a 1.5 del libro de texto sobre tablas de verdad."
                )
            ),
            announcements = listOf(
                AnnouncementData(
                    title = "Próximo Examen Parcial",
                    description = "El examen cubrirá los temas de Lógica Proposicional y Teoría de Conjuntos."
                )
            )
        ),
        CourseData(
            title = "Introducción a la Economía",
            degree = "1er Año",
            career = "Administración de Empresas",
            section = "Única",
            works = listOf(
                WorkData(
                    title = "Ensayo: Inflación",
                    description = "Escribir un ensayo de 1000 palabras sobre las causas y consecuencias de la inflación."
                )
            ),
            announcements = listOf(
                AnnouncementData(
                    title = "Conferencia Invitada",
                    description = "El Dr. Pérez dará una conferencia sobre \"Perspectivas Económicas Globales\" el viernes."
                )
            )
        ),
        CourseData(
            title = "Biología Celular",
            degree = "2º Semestre",
            career = "Medicina",
            section = "B-1",
            works = listOf(
                WorkData(
                    title = "Reporte de Práctica de Mitosis",
                    description = "Detallar las fases de la mitosis observadas en el microscopio y dibujar cada una."
                )
            ),
            announcements = listOf(
                AnnouncementData(
                    title = "Cambio de Horario Laboratorio",
                    description = "El laboratorio del miércoles se moverá de 10-12 a 14-16 hrs."
                )
            )
        ),
        CourseData(
            title = "Historia del Arte Renacentista",
            degree = "5º Semestre",
            career = "Bellas Artes",
            section = "C",
            works = listOf(
                WorkData(
                    title = "Análisis Comparativo: Donatello vs. Miguel Ángel",
                    description = "Comparar las técnicas escultóricas y temáticas de ambos artistas."
                )
            ),
            announcements = listOf(
                AnnouncementData(
                    title = "Visita al Museo Cancelada",
                    description = "La visita programada al Museo Nacional de Arte para el jueves ha sido cancelada."
                )
            )
        )
    ),
    val selectedCourse: CourseData? = null,
)