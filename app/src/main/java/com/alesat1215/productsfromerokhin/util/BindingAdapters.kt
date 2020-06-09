package com.alesat1215.productsfromerokhin.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream

val storageFB by lazy { FirebaseStorage.getInstance().reference }

@BindingAdapter("fb_image")
fun bindImage(imageView: ImageView, fsPath: String?) {
    if (fsPath == null) return
    Glide.with(imageView)
        .load(storageFB.child(fsPath))
        .into(imageView)
}

@GlideModule
class FBGlideModule: AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.append(StorageReference::class.java, InputStream::class.java, FirebaseImageLoader.Factory())
    }
}