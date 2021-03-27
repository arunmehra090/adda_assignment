package com.adda.extension

import android.view.View

fun View.setViewVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.isViewVisible(): Boolean = this.visibility == View.VISIBLE

fun View.isViewInvisible(): Boolean = this.visibility == View.INVISIBLE

fun View.isViewGone(): Boolean = this.visibility == View.GONE

fun View.show() { this.visibility = View.VISIBLE }

fun View.setInvisible() { this.visibility = View.INVISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.setClickable() { this.isClickable = true }

fun View.setNonClickable() { this.isClickable = false }

fun View.setEnabled() { this.isEnabled = true }

fun View.setDisabled() { this.isEnabled = false }






