package com.example.contactappwithmvvm.ui.compnents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.contactappwithmvvm.R
import com.example.contactappwithmvvm.databinding.ImagePickerBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageSelectorDialog: BottomSheetDialogFragment() {

    private var onButtonClicked: OnButtonClicked? = null
    private lateinit var binding:ImagePickerBottomDialogBinding

    companion object{

        const val CAMERA_SELECTED:Int = 1
        const val GALLERY_SELECTED:Int = 2

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_picker_bottom_dialog, null, false)

       binding.galleryButton.setOnClickListener {
            onButtonClicked?.onButtonClicked(GALLERY_SELECTED)
            dismiss()
        }

      binding.cameraButton.setOnClickListener {
            onButtonClicked?.onButtonClicked(CAMERA_SELECTED)
            dismiss()
        }

        return binding.root
    }

    fun setClickListener(lister: OnButtonClicked){
        this.onButtonClicked = lister
    }


    interface OnButtonClicked{
        fun onButtonClicked(item: Int)

    }

}