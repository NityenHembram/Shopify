package com.amvlabs.shopify.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.shopify.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ImageRecyclerViewAdapter(private val bitmaps:ArrayList<Bitmap>,var onImageItemClickListener: OnImageItemClickListener, val ctx:Context):RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView:View,var onImageItemClickListener: OnImageItemClickListener):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val imageView = itemView.findViewById<ImageView>(R.id.rvImg)
        val removeImge = itemView.findViewById<ImageView>(R.id.removeImgBtn)
        init {
            imageView.setOnClickListener(this)
            removeImge.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
           when(p0?.id){
               R.id.rvImg -> {onImageItemClickListener.onImageClicked()}
               R.id.removeImgBtn->{onImageItemClickListener.onRemoveClicked(adapterPosition)}
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.add_image_items,parent,false)
        return ViewHolder(v, onImageItemClickListener )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img = bitmaps[position]
        Glide.with(ctx).load(img).into(holder.imageView)

    }

    override fun getItemCount(): Int {
      return bitmaps.size
    }
}

interface OnImageItemClickListener{
    fun onImageClicked()
    fun onRemoveClicked(position:Int)
}