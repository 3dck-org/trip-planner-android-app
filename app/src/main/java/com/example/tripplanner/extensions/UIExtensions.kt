package com.example.tripplanner.extensions

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.util.PatternsCompat
import com.example.tripplanner.constants.Constants.passwordPattern
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

fun TextInputLayout.passwordValidation(
    password: String
): Boolean {
    if (password.length < 8) {
        this.error = "Password doesn\'t fulfil the conditions!"
        return false
    } else {
        this.error = null
    }
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(password)
    if (!matcher.matches()) {
        this.error = "Password doesn\'t fulfil the conditions!"
        return false
    }
    this.error = null
    return true
}

fun TextInputLayout.emailLoginValidation(email: String): Boolean {
    val matcher =
        PatternsCompat.EMAIL_ADDRESS.matcher(email)
    if (!matcher.matches()) {
        this.error = "Email\'s incorrect"
        return false
    }

    this.error = null
    return true
}

fun TextInputLayout.fieldIsNotEmptyValidation(field: String): Boolean {
    if (field.isEmpty()) {
        this.error = "Field cannot be empty"
        return false
    } else {
        this.error = null
    }

    if (field.split("").contains(" ")) {
        this.error = "Field cannot have spaces"
        return false
    } else {
        this.error = null
    }

    this.error = null
    return true
}

fun Button.enable() {
    this.isEnabled = true
}

fun Button.disable() {
    this.isEnabled = false
}

fun ProgressBar.hide(){
    this.visibility = View.GONE
}

fun ProgressBar.show(){
    this.visibility = View.VISIBLE
}

