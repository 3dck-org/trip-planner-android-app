package com.example.tripplanner.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.tripplanner.R

object GlideLoader {

    fun loadImage(context: Context, view: ImageView, imageUrl: String){
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.ic_godlen_city)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(view)
    }
}