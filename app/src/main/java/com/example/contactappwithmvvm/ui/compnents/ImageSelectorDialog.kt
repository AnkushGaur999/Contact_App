package com.example.contactappwithmvvm.ui.compnents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.contactappwithmvvm.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageSelectorDialog: BottomSheetDialogFragment() {

    private var onButtonClicked: OnButtonClicked? = null

    companion object{

        const val CAMERA_SELECTED:Int = 1
        const val GALLERY_SELECTED:Int = 2

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.image_picker_bottom_dialog, container, false)

        val galleryButton: Button = view.findViewById(R.id.galleryButton)
        val cameraButton: Button = view.findViewById(R.id.cameraButton)

        galleryButton.setOnClickListener {
            onButtonClicked?.onButtonClicked(GALLERY_SELECTED)
            dismiss()
        }

        cameraButton.setOnClickListener {
            onButtonClicked?.onButtonClicked(CAMERA_SELECTED)
            dismiss()
        }

        return view
    }

    fun setClickListener(lister: OnButtonClicked){
        this.onButtonClicked = lister
    }


    interface OnButtonClicked{

        fun onButtonClicked(item: Int)

    }

}