package com.example.contact_book.ui.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.contact_book.R
import com.example.contact_book.databinding.FragmentAddBinding
import com.example.contact_book.data.database.user.Enterprise
import com.example.contact_book.data.database.user.User
import com.example.contact_book.domain.model.Alert
import com.example.contact_book.domain.model.pattern
import com.example.contact_book.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val userViewModel by viewModels<UserViewModel>()
    private val IMAGE_REQUEST_CODE = 100
    private var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("Check Code")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddBinding.bind(view)

        binding.btNewUser.setOnClickListener(listener)

        binding.ivProfile.setOnClickListener(listener)

        inputValue( inputEditText = binding.etFirstName,
            inputLayout = binding.tiFirstName)

        inputValue( inputEditText = binding.etLastName,
            inputLayout = binding.tiLastName)

        inputValue( inputEditText = binding.etPhone,
            inputLayout = binding.tiPhone)

        inputValue( inputEditText = binding.etEmail,
            inputLayout = binding.tiEmail)

        inputValue( inputEditText = binding.etEnterprise,
            inputLayout = binding.tiEnterprise)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    val listener = View.OnClickListener { view ->

        when(view.id){

            R.id.ivProfile -> { imageChooser() }

            R.id.btNewUser -> { insertDataBase() }

        }

    }

    private fun insertDataBase() = if (inputCheck()) {

        val enterprise = Enterprise(

            name = binding.etEnterprise.text.toString(),
            email = binding.etEmail.text.toString()

        )

        if (bitmap != null) {

            val user = User(
                id = 0, firstname = binding.etFirstName.text.toString(),
                lastname = binding.etLastName.text.toString(),
                phone = Integer.parseInt(binding.etPhone.text.toString()), bitmap,
                enterprise
            )

            userViewModel.addUser(user)

        } else {

            val user = User(
                id = 0, firstname = binding.etFirstName.text.toString(),
                lastname = binding.etLastName.text.toString(),
                phone = Integer.parseInt(binding.etPhone.text.toString()),
                enterprise
            )

            userViewModel.addUser(user)

        }

        Alert.showSuccess(getString(R.string.addSuccess),requireContext()){}

        findNavController().navigate(R.id.action_addFragment_to_listFragment)

    } else {

        Alert.showError(getString(R.string.empty),requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun imageChooser() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                IMAGE_REQUEST_CODE
            );

        } else {

            return
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        intent.type = "image/*"

        intent.action = Intent.ACTION_GET_CONTENT

        openGallery.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it?.data?.data!!

                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)

                bitmap = ImageDecoder.decodeBitmap(source)

                val resized = Bitmap.createScaledBitmap(bitmap!!, 256, 256, true)

                binding.ivProfile.setImageBitmap(resized)
            }

        }



    private fun inputCheck(): Boolean {

        return binding.etFirstName.text!!.isNotEmpty() &&
                binding.etLastName.text!!.isNotEmpty() &&
                binding.etPhone.text!!.isNotEmpty() &&
                binding.etEmail.text!!.isNotEmpty() &&
                binding.etEnterprise.text!!.isNotEmpty()

    }

    private fun inputValue(inputEditText: TextInputEditText, inputLayout: TextInputLayout) {

        val emailCheck = Pattern.compile(pattern.EMAIL_PATTERN.toString())

        val phoneCheck = Pattern.compile(pattern.PHONE_PATTERN.toString())

        when (inputEditText.id){

            R.id.etFirstName, R.id.etLastName, R.id.etEnterprise-> {

                inputEditText.doOnTextChanged{ text, start, before, count ->

                    if(text!!.length < 3){

                        inputLayout.error = "Enter Value!"

                    }else { inputLayout.error = null }

                }

            }

            R.id.etPhone -> {

                inputEditText.doOnTextChanged{ text, start, before, count ->

                    if(!phoneCheck.matcher(text).matches()){

                        inputLayout.error = "Enter Value!"

                    }else { inputLayout.error = null }

                }

            }

            R.id.etEmail -> {

                inputEditText.doOnTextChanged{ text, start, before, count ->

                    if(!emailCheck.matcher(text).matches()){

                        inputLayout.error = "Enter Value!"

                    }else { inputLayout.error = null }

                }

            }

        }

    }

}


