package com.example.tripplanner.views.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ItemSettingsViewBinding

class SettingsItemView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: ItemSettingsViewBinding
    private var currText: String? = null
    private var isAva: Boolean = false

    init {
        val attributes = context
            .theme
            .obtainStyledAttributes(attrs, R.styleable.SettingsItemView, 0, 0)
        binding = ItemSettingsViewBinding.inflate(LayoutInflater.from(context), this, true)
        currText = attributes.getString(R.styleable.SettingsItemView_si_text)
        isAva = attributes.getBoolean(R.styleable.SettingsItemView_is_available, false) == true
        if (currText != null)
            binding.settingMb.text = currText
        binding.settingMb.isEnabled = isAva

    }
}
