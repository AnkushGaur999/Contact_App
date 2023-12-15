package com.example.contactappwithmvvm.adapters

import android.graphics.Bitmap
import androidx.databinding.BindingAdapter
import com.example.contactappwithmvvm.R
import de.hdodenhof.circleimageview.CircleImageView

class BindingAdapter {

   companion object{
       @JvmStatic
       @BindingAdapter("app:imageBitmap")
       fun CircleImageView.fromBitmap(bitmap: Bitmap?){

           if(bitmap!=null){
               this.setImageBitmap(bitmap)
           }else{
               this.setImageResource(R.drawable.user_image)
           }
       }

   }
}