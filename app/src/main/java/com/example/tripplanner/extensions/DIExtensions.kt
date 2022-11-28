package com.example.tripplanner.extensions

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tripplanner.R
import timber.log.Timber


fun log(message: String) = Timber.d(message)

fun getImageFromURL(imageUrl: String?, context: Context, imageView: ImageView) {
    val drawable = CircularProgressDrawable(context)
    drawable.setColorSchemeColors(
        R.color.base_color_3
    )
    drawable.centerRadius = 30f
    drawable.strokeWidth = 5f
    drawable.start()

    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .error(R.drawable.ic_godlen_city)
        .placeholder(drawable)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
        .into(imageView)
}
