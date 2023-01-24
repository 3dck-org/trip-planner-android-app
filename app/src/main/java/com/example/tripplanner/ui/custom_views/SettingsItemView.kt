package com.example.tripplanner.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ItemSettingsViewBinding

class SettingsItemView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var optionViewBinding: ItemSettingsViewBinding
    private var currText: String? = null

    init {
        val attributes = context
            .theme
            .obtainStyledAttributes(attrs, R.styleable.SettingsItemView, 0, 0)
        optionViewBinding = ItemSettingsViewBinding.inflate(LayoutInflater.from(context), this, true)
        currText = attributes.getString(R.styleable.SettingsItemView_si_text)
        if (currText != null)
            optionViewBinding.optionMaterialButton.text = currText
    }
}
