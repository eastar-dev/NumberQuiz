package dev.eastar.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("select")
fun View.isSelect(isSelect: Boolean) {
//    Log.e(this, isSelect)
    isSelected = isSelect
}