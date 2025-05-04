package com.meally.meally.common.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.focusClearer(focusManager: FocusManager) =
    this.pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
        }
    }
