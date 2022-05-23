package com.amvlabs.shopify

import android.content.Intent
import android.graphics.Bitmap
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.shopify.adapter.ImageRecyclerViewAdapter
import com.amvlabs.shopify.adapter.OnImageItemClickListener
import com.amvlabs.shopify.databinding.ActivityAddItemBinding
import com.amvlabs.shopify.viewmodel.AddItemViewModel
import java.io.ByteArrayOutputStream
import java.io.File


class AddItemActivity : AppCompatActivity(),OnImageItemClickListener {
    private lateinit var bind:ActivityAddItemBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var addImgBtn:ImageButton
    private lateinit var imageUri:Uri
    private lateinit var viewModel:AddItemViewModel
    private var bitmapArray =ArrayList<Bitmap>()

    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            val bitmap = it.data?.extras?.get("data") as Bitmap
            bitmapArray.add(bitmap)
            viewModel._bitmapArray.value = bitmapArray
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(bind.root)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[AddItemViewModel::class.java]

        addImgBtn = bind.addImgBtn
        recyclerView = bind.imageRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel._bitmapArray.observe(this, Observer {
            Log.d("TAG", "onCreate: $it")
            recyclerView.adapter = ImageRecyclerViewAdapter(it,this,this)
        })

        addImgBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(intent)
        }

        bind.submitBtn.setOnClickListener{
            viewModel.saveData()
        }

    }


    override fun onImageClicked() {

    }



    override fun onRemoveClicked(position: Int) {
        bitmapArray.removeAt(position)
        viewModel._bitmapArray.value = bitmapArray
    }



}