package org.quickness.ui.components

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    sheetState: SheetState,
    colorBackground: Color,
    showContent: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showContent) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            content = { content() },
            containerColor = colorBackground,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        )
    }
}