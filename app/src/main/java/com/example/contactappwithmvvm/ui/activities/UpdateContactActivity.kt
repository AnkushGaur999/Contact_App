package com.example.contactappwithmvvm.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.contactappwithmvvm.R
import com.example.contactappwithmvvm.database.entities.Contact
import com.example.contactappwithmvvm.databinding.ActivityUpdateContactBinding
import com.example.contactappwithmvvm.ui.compnents.ImageSelectorDialog
import com.example.contactappwithmvvm.viewmodels.ContactViewModel
import java.io.File

class UpdateContactActivity : AppCompatActivity(), ImageSelectorDialog.OnButtonClicked {

    private lateinit var binding: ActivityUpdateContactBinding

    private lateinit var viewModel: ContactViewModel

    private var imgBitmap: Bitmap? = null
    private var imageUri: Uri? = null

    /**
     * This variable is use to pick the image from gallery and set image into imageview.
     */
    private val galleryResultActivity =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->

            result?.let {
                binding.userProfile.setImageURI(result)
                imgBitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, result)
                } else {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(contentResolver, result)
                    ImageDecoder.decodeBitmap(source)
                }
            }
        }

    /**
     * This variable is use to ask gallery access permission from user and if user provide permission
     * then open image picker in gallery.
     */
    private val galleryPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                isGranted ->
            if (isGranted) {
                getImageFromGallery()
            }
        }

    /**
     *  Check the camera permission granted or not.
     */
    private val cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                isGranted ->
            if (isGranted) {
                getImageFromCamera()
            }
        }

    /**
     * Get the image from camera and convert into bitmap.
     */
    private val cameraResultActivity =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {

                isTaken ->

            if (isTaken) {
                binding.userProfile.setImageURI(imageUri)
                imgBitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                } else {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(contentResolver, imageUri!!)
                    ImageDecoder.decodeBitmap(source)
                }
            }

            binding.uploadProfileBtn.setOnClickListener {
                val imageBottomDialog = ImageSelectorDialog()
                imageBottomDialog.setClickListener(this)
                imageBottomDialog.show(supportFragmentManager, ImageSelectorDialog::class.java.name)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_contact)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        viewModel.getContact(intent.getLongExtra("contactId", -1))

        viewModel.userDetails.observe(this) { contact ->

            contact?.let {
                binding.contact = contact
                imgBitmap = contact.image
            }
        }

        binding.uploadProfileBtn.setOnClickListener {
            val imageBottomDialog: ImageSelectorDialog = ImageSelectorDialog()
            imageBottomDialog.setClickListener(this)
            imageBottomDialog.show(supportFragmentManager, ImageSelectorDialog::class.java.name)
        }
        binding.viewModel = viewModel

        binding.navBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        setObserver()
    }

    /**
     *  This method for observe all the live data variable from view model and update the UI.
     */
    private fun setObserver() {

        viewModel.updateButtonClick.observe(this) {
            if (validateData()) {
                val contact = Contact(
                    id = intent.getLongExtra("contactId", -1),
                    firstName = binding.firstNameET.text.toString().trim(),
                    lastName = binding.lastNameET.text.toString().trim(),
                    contactNumber = binding.mobileNoET.text.toString().trim(),
                    email = binding.emailET.text.toString().trim(),
                    city = binding.cityET.text.toString(),
                    state = binding.stateET.text.toString(),
                    image = imgBitmap
                )
                updateContact(contact)
            }
        }
    }

    private fun updateContact(contact: Contact) {

        viewModel.updateContact(contact)
        Toast.makeText(this, "Contact Updated successfully", Toast.LENGTH_LONG).show()

        val intent = Intent(this, ContactActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    /**
     * This method is check all the data fill into form is correct or not.
     */
    private fun validateData(): Boolean {

        when {
            binding.firstNameET.text.isEmpty() -> {
                showToast("First name can't be blank")
                binding.firstNameET.requestFocus()
                binding.firstNameET.error = "First name can't be blank"
                return false
            }

            binding.lastNameET.text.isEmpty() -> {
                showToast("Last name can't be blank")
                binding.lastNameET.requestFocus()
                binding.lastNameET.error = "Last name can't be blank"
                return false
            }

            binding.mobileNoET.text.isEmpty() || binding.mobileNoET.text.length < 10 -> {
                showToast("Phone number is not valid")
                binding.mobileNoET.requestFocus()
                binding.mobileNoET.error = "Phone number is not valid"
                return false
            }


            binding.emailET.text.isEmpty() -> {
                showToast("Email is required")
                binding.emailET.requestFocus()
                binding.emailET.error = "Email is required"
                return false
            }

            !isValidEmail(binding.emailET.text.toString()) -> {
                showToast("Email is not valid")
                binding.emailET.requestFocus()
                binding.emailET.error = "Email is not valid"
                return false
            }

            binding.cityET.text.isEmpty() -> {
                showToast("City is required")
                binding.cityET.requestFocus()
                binding.cityET.error = "City is required"
                return false
            }

            binding.stateET.text.isEmpty() -> {
                showToast("State is required")
                return false
            }

            else -> return true
        }

    }

    /**
     *  This method is use for validate the email address, check whether email is correct or not.
     */
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    /**
     * Show the show for custom message
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * This method is use for check gallery permission and if user grant the permission then open gallery.
     */
    private fun checkGalleryPermissionAndOpenGallery() {

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getImageFromGallery()
        } else {

            galleryPermissionResult.launch(permission)
        }

    }


    /**
     * Check the camera permission is granted or not.
     * If camera permission is not granted, we manually ask for camera permission from user.
     * Otherwise we simply open the camera.
     */
    private fun checkCameraPermission() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionResult.launch(Manifest.permission.CAMERA)
        } else {
            getImageFromCamera()
        }

    }

    /**
     * This method is used for open gallery image picker.
     */
    private fun getImageFromGallery() {
        galleryResultActivity.launch("image/*")
    }

    /**
     * This method is use for open camera using intent.
     */
    private fun getImageFromCamera() {

        imageUri = createImageFileUri()
        cameraResultActivity.launch(imageUri)
    }

    /**
     * This method is use to create a file in app storage for store image and return uri.
     */
    private fun createImageFileUri(): Uri {

        // Create a file to save the image
        val file = File(application.filesDir, "camera_user_photo.png")

        return FileProvider.getUriForFile(
            applicationContext,
            "com.example.contactappwithmvvm.fileProvider",
            file
        )
    }

    /**
     * This listener check the selected item from bottom dialog in ImageSelectorDialog dialog.
     */
    override fun onButtonClicked(item: Int) {
        when (item) {
            ImageSelectorDialog.CAMERA_SELECTED -> {
                checkCameraPermission()
            }

            ImageSelectorDialog.GALLERY_SELECTED -> {
                checkGalleryPermissionAndOpenGallery()
            }
        }
    }

}