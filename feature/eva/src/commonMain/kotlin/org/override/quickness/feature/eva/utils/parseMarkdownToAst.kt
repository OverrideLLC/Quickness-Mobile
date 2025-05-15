package org.override.quickness.feature.eva.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser


/**
 * Parsea un string de Markdown y devuelve el nodo raíz del Árbol de Sintaxis Abstracta (AST).
 *
 * @param markdownText El texto en formato Markdown a parsear.
 * @return El nodo raíz del AST.
 */
fun parseMarkdownToAst(markdownText: String): ASTNode {
    val flavour =
        CommonMarkFlavourDescriptor() // Define el "sabor" de Markdown (CommonMark es estándar)
    val parser = MarkdownParser(flavour)
    return parser.buildMarkdownTreeFromString(markdownText)
}


/**
 * Renderiza un nodo individual del AST.
 * Esta es una implementación básica y maneja solo algunos tipos de elementos.
 *
 * @param node El nodo del AST a renderizar.
 * @param originalText El texto Markdown original, necesario para extraer el contenido del nodo.
 */
@Composable
fun RenderAstNode(node: ASTNode, originalText: String) {
    val textStyle = TextStyle(
        color = colorScheme.tertiary,
        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
    )
    when (node.type) {
        MarkdownElementTypes.ATX_1 -> RenderHeader(node, originalText, 28.sp, FontWeight.Bold)
        MarkdownElementTypes.ATX_2 -> RenderHeader(node, originalText, 24.sp, FontWeight.SemiBold)
        MarkdownElementTypes.ATX_3 -> RenderHeader(node, originalText, 20.sp, FontWeight.SemiBold)
        MarkdownElementTypes.ATX_4 -> RenderHeader(node, originalText, 18.sp, FontWeight.Medium)
        MarkdownElementTypes.ATX_5 -> RenderHeader(node, originalText, 16.sp, FontWeight.Medium)
        MarkdownElementTypes.ATX_6 -> RenderHeader(node, originalText, 14.sp, FontWeight.Medium)

        MarkdownElementTypes.PARAGRAPH -> {
            val annotatedString = buildAnnotatedString {
                appendMarkdownChildren(node, originalText, this)
            }
            if (annotatedString.isNotBlank()) { // Evitar Spacers innecesarios para párrafos vacíos
                Text(text = annotatedString, fontSize = 16.sp, style = textStyle)
                Spacer(Modifier.height(8.dp))
            }
        }

        MarkdownElementTypes.UNORDERED_LIST -> {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                node.children.filter { it.type == MarkdownElementTypes.LIST_ITEM }
                    .forEach { listItem ->
                        RenderAstNode(listItem, originalText) // Renderiza cada item de la lista
                    }
            }
            Spacer(Modifier.height(8.dp))
        }

        MarkdownElementTypes.LIST_ITEM -> {
            val listItemAnnotatedString = buildAnnotatedString {
                val contentNodes = node.children.dropWhile {
                    // Los primeros hijos de LIST_ITEM pueden ser LIST_BULLET y WHITE_SPACE
                    it.type == MarkdownTokenTypes.LIST_BULLET || it.type == MarkdownTokenTypes.WHITE_SPACE
                }
                // El contenido real del item suele estar en un PÁRRAFO hijo.
                contentNodes.forEach { childNode ->
                    appendMarkdownChildren(childNode, originalText, this, isListItemContent = true)
                }
            }
            if (listItemAnnotatedString.isNotBlank()) {
                Text(
                    text = AnnotatedString("• ") + listItemAnnotatedString,
                    fontSize = 16.sp,
                    style = textStyle
                )
                Spacer(Modifier.height(4.dp)) // Espacio entre items de lista
            }
        }
        // Puedes añadir más casos para MarkdownElementTypes como:
        // MarkdownElementTypes.ORDERED_LIST
        // MarkdownElementTypes.BLOCK_QUOTE
        // MarkdownElementTypes.CODE_FENCE (para bloques de código ```)
        // MarkdownElementTypes.CODE_BLOCK (para bloques de código indentados)
        // MarkdownElementTypes.HTML_BLOCK
        // MarkdownElementTypes.HORIZONTAL_RULE
        // MarkdownElementTypes.LINK_DEFINITION, MarkdownElementTypes.LINK_DESTINATION, etc. para enlaces e imágenes.
        else -> {
            if (node.children.isNotEmpty()) {
                node.children.forEach { child ->
                    RenderAstNode(child, originalText)
                }
            }
        }
    }
}

@Composable
private fun RenderHeader(
    node: ASTNode,
    originalText: String,
    fontSize: androidx.compose.ui.unit.TextUnit,
    fontWeight: FontWeight
) {
    val headerContent = buildAnnotatedString {
        node.children.forEach { childNode ->
            if (childNode.type != MarkdownTokenTypes.ATX_HEADER
                && childNode.type != MarkdownTokenTypes.WHITE_SPACE
            ) {
                appendMarkdownChildren(childNode, originalText, this)
            }
        }
    }
    if (headerContent.isNotBlank()) {
        Text(
            text = headerContent,
            fontSize = fontSize,
            fontWeight = fontWeight,
            style = TextStyle(
                color = colorScheme.tertiary,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
            )
        )
        Spacer(Modifier.height(8.dp))
    }
}


/**
 * Función de ayuda para construir un AnnotatedString a partir de los hijos de un nodo AST,
 * aplicando estilos básicos como negrita y cursiva.
 *
 * @param parentNode El nodo padre cuyos hijos se procesarán.
 * @param originalText El texto Markdown original.
 * @param builder El AnnotatedString.Builder para añadir el texto con estilo.
 * @param isListItemContent Indica si se está procesando dentro de un item de lista para un manejo especial.
 */
fun appendMarkdownChildren(
    parentNode: ASTNode,
    originalText: String,
    builder: AnnotatedString.Builder,
    isListItemContent: Boolean = false
) {
    parentNode.children.forEach { childNode ->
        when (childNode.type) {
            MarkdownElementTypes.STRONG -> {
                builder.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                appendMarkdownChildren(childNode, originalText, builder)
                builder.pop()
            }

            MarkdownElementTypes.EMPH -> {
                builder.pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                appendMarkdownChildren(childNode, originalText, builder)
                builder.pop()
            }

            MarkdownElementTypes.CODE_SPAN -> {
                val codeText = childNode.children
                    .filterNot { it.type == MarkdownTokenTypes.BACKTICK }
                    .joinToString("") { it.getTextInNode(originalText).toString() }

                builder.pushStyle(
                    SpanStyle(
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        background = androidx.compose.ui.graphics.Color.LightGray.copy(alpha = 0.5f)
                    )
                ) // Estilo para código
                builder.append(codeText)
                builder.pop()
            }
            // Añadir manejo para MarkdownElementTypes.INLINE_LINK para [texto](url)
            // Esto es más complejo ya que necesitas el texto y la URL, y posiblemente un ClickableText.
            // Por ahora, solo renderizamos el texto del enlace.
            MarkdownElementTypes.LINK_TEXT -> { // El texto visible de un enlace: [texto]
                // El nodo LINK_TEXT tiene hijos que son el texto real.
                appendMarkdownChildren(childNode, originalText, builder)
            }


            MarkdownTokenTypes.TEXT,
            MarkdownTokenTypes.WHITE_SPACE, // Los espacios son importantes
            MarkdownTokenTypes.COLON, MarkdownTokenTypes.COLON,
            MarkdownTokenTypes.SINGLE_QUOTE, MarkdownTokenTypes.DOUBLE_QUOTE,
            MarkdownTokenTypes.LPAREN, MarkdownTokenTypes.RPAREN,
            MarkdownTokenTypes.LBRACKET, MarkdownTokenTypes.RBRACKET, // Corchetes, si no son parte de un enlace
            MarkdownTokenTypes.EXCLAMATION_MARK,
            MarkdownTokenTypes.GT, MarkdownTokenTypes.LT,
            MarkdownTokenTypes.EMAIL_AUTOLINK, MarkdownTokenTypes.AUTOLINK, MarkdownTokenTypes.URL -> {
                var text = childNode.getTextInNode(originalText).toString()
                // La lógica para quitar viñetas de list items es mejor manejarla en RenderAstNode/LIST_ITEM
                // o asegurándose que getTextInNode no las incluya si se llama al nodo de contenido correcto.
                builder.append(text)
            }

            // Ignorar tokens estructurales que ya son manejados por sus elementos padres (MarkdownElementTypes)
            // o que no deben ser renderizados como texto plano en este contexto.
            MarkdownTokenTypes.ATX_HEADER, // El '#'
            MarkdownTokenTypes.SETEXT_1, MarkdownTokenTypes.SETEXT_2, // Líneas === o --- para encabezados
            MarkdownTokenTypes.EMPH, MarkdownTokenTypes.EMPH, // Usados para EMPH/STRONG, ya manejados
            MarkdownTokenTypes.BACKTICK, // Usado para CODE_SPAN, ya manejado
            MarkdownTokenTypes.LIST_BULLET, MarkdownTokenTypes.LIST_NUMBER, // Viñetas/números de listas
            MarkdownTokenTypes.HARD_LINE_BREAK, // Podrías querer convertir esto en un \n
            MarkdownTokenTypes.EOL, // Fin de línea
            MarkdownElementTypes.LINK_DESTINATION, // La URL de un enlace, no el texto visible
            MarkdownElementTypes.LINK_LABEL // Etiqueta de enlace para referencias
                -> {
                // No añadir estos tokens como texto. Si tienen hijos que deben ser procesados (raro para tokens puros),
                // la llamada recursiva podría ir aquí, pero usualmente no es necesario para estos tipos.
                if (childNode.children.isNotEmpty()) { // Procesar hijos si los hay (ej. texto dentro de un delimitador no manejado)
                    appendMarkdownChildren(childNode, originalText, builder)
                }
            }
            // Para otros tipos de elementos anidados que podrían tener su propio renderizado
            // o que simplemente contienen texto.
            else -> {
                if (childNode.children.isNotEmpty()) {
                    appendMarkdownChildren(childNode, originalText, builder)
                } else {
                    // Si no tiene hijos y es un tipo desconocido, extraemos su texto si es posible.
                    val textContent = childNode.getTextInNode(originalText).toString()
                    if (textContent.isNotBlank()) {
                        builder.append(textContent)
                    }
                }
            }
        }
    }
}