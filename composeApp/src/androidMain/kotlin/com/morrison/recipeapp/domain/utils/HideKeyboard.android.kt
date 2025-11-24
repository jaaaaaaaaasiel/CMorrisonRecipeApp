package com.morrison.recipeapp.domain.utils

import androidx.compose.ui.focus.FocusManager

actual fun HideKeyboard(focusManager: FocusManager) {
    focusManager.clearFocus()
}