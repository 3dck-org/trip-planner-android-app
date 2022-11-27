package com.example.tripplanner.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.tripplanner.R
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL


fun log(message: String) = Timber.d(message)

fun getImageFromURL(imageUrl: String?, context: Context, imageView: ImageView) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .error(R.drawable.ic_godlen_city)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
        .into(imageView)
}

fun isImage(url: String?): Boolean {
    return try {
        val huc = URL(url).openConnection() as HttpURLConnection
        huc.requestMethod = "HEAD"
        val responseCode = huc.responseCode
        log("******** ${responseCode in listOf(200, 202, 204) || huc.contentType == "/image"}")
        responseCode in listOf(200, 202, 204) || huc.contentType == "/image"
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun getImageFromUrlWithException(imageUrl: String?, context: Context, imageView: ImageView) {
    Glide.with(context)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .transform(CenterCrop(), RoundedCorners(16))
                .centerCrop()
                .error(R.drawable.ic_godlen_city)
        )
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }
        })
        .into(imageView)
}