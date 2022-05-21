package com.amvlabs.shopify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.shopify.adapter.ImageRecyclerViewAdapter
import com.amvlabs.shopify.databinding.ActivityAddItemBinding
import java.io.File


class AddItemActivity : AppCompatActivity() {
    private lateinit var bind:ActivityAddItemBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var addImgBtn:ImageButton
    private lateinit var imageUri:Uri

    private var launcher = registerForActivityResult(ActivityResultContracts.TakePicture(),
        ActivityResultCallback {
            Log.d("TAG", ": $imageUri ")
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(bind.root)

        addImgBtn = bind.addImgBtn
        recyclerView = bind.imageRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageRecyclerViewAdapter()

        addImgBtn.setOnClickListener {
            imageUri = createImageUri()
            launcher.launch(imageUri)
        }

    }

    fun createImageUri():Uri{
        val image = File(applicationContext.filesDir,"photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.amvlabs.shopify.fileprovider"
        ,image)
    }

}