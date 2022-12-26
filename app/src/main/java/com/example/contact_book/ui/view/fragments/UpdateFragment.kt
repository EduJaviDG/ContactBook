package com.example.contact_book.ui.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contact_book.R
import com.example.contact_book.databinding.FragmentUpdateBinding
import com.example.contact_book.data.database.user.Enterprise
import com.example.contact_book.data.database.user.User
import com.example.contact_book.domain.model.Alert
import com.example.contact_book.util.pattern
import com.example.contact_book.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.regex.Pattern

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var binding: FragmentUpdateBinding
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var menuHost: MenuHost
    private val IMAGE_REQUEST_CODE = 100
    private lateinit var bitmap: Bitmap
    private lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        appContext = requireContext().applicationContext

        bitmap = args.currentUser.profilePhoto!!

        menuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.delete_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {

                    R.id.menuDelete -> {

                        deleteDatabase()

                        true

                    }
                    else -> false
                }

            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUpdateBinding.bind(view)

        loadData()

        inputValue( inputEditText = binding.etUpdateFirstName,
            inputLayout = binding.tiUpdateFirstName)

        inputValue( inputEditText = binding.etUpdateLastName,
            inputLayout = binding.tiUpdateLastName)

        inputValue( inputEditText = binding.etUpdatePhone,
            inputLayout = binding.tiUpdatePhone)

        inputValue( inputEditText = binding.etUpdateEmail,
            inputLayout = binding.tiUpdateEmail)

        inputValue( inputEditText = binding.etUpdateEnterprise,
            inputLayout = binding.tiUpdateEnterprise)

        binding.btUpdateUser.setOnClickListener(listener)

        binding.ivUpdateProfile.setOnClickListener(listener)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    val listener = View.OnClickListener { view ->

        when (view.id) {

            R.id.ivUpdateProfile -> { imageChooser() }

            R.id.btUpdateUser -> {

                updateDatabse()

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)

            }

        }

    }

    private fun loadData() {

        binding.etUpdateFirstName.setText(args.currentUser.firstname)
        binding.etUpdateLastName.setText(args.currentUser.lastname)
        binding.etUpdatePhone.setText(args.currentUser.phone.toString())
        binding.etUpdateEmail.setText(args.currentUser.enterprise.email)
        binding.etUpdateEnterprise.setText(args.currentUser.enterprise.name)
        binding.ivUpdateProfile.setImageBitmap(args.currentUser.profilePhoto)

        if(args.currentUser.profilePhoto!!.height == 1 &&
            args.currentUser.profilePhoto!!.width == 1 ) {

            binding.ivUpdateProfile.visibility = View.VISIBLE

        }

    }

    private fun deleteDatabase() {

        val title = "${getString(R.string.delete)} ${args.currentUser.firstname}"

        val message = getString(R.string.deleteContact)

        Alert.showDelete( context = requireContext(), message = message, title = title) { result ->

            userViewModel.deleteUser(args.currentUser)


            val message = "${getString(R.string.removed)} ${args.currentUser.firstname}"

            Alert.showSuccess(context = requireContext(), message = message ){ result ->

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)

            }

        }

    }

    private fun updateDatabse() {

        if (inputCheck()) {

            val enterprise = Enterprise(
                email = binding.etUpdateEmail.text.toString(),
                name = binding.etUpdateEnterprise.text.toString()
            )

            val user = User(
                id = args.currentUser.id, firstname = binding.etUpdateFirstName.text.toString(),
                lastname = binding.etUpdateLastName.text.toString(),
                phone = Integer.parseInt(binding.etUpdatePhone.text.toString()),
                bitmap, enterprise
            )

            userViewModel.updateUser(user)

            Alert.showSuccess(getString(R.string.updateSuccess),requireContext()){}

        } else {

            Alert.showError(getString(R.string.empty),requireContext())

        }

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

                val resize = Bitmap.createScaledBitmap(bitmap!!, 256,256, true)

                binding.ivUpdateProfile.setImageBitmap(resize)
            }

        }

    private fun inputCheck(): Boolean {

        return binding.etUpdateFirstName.text!!.isNotEmpty() &&
                binding.etUpdateLastName.text!!.isNotEmpty() &&
                binding.etUpdatePhone.text!!.isNotEmpty() &&
                binding.etUpdateEmail.text!!.isNotEmpty() &&
                binding.etUpdateEnterprise.text!!.isNotEmpty()

        }

    private fun inputValue( inputEditText: TextInputEditText, inputLayout: TextInputLayout) {

            val emailCheck = Pattern.compile(pattern.EMAIL_PATTERN.toString())

            val phoneCheck = Pattern.compile(pattern.PHONE_PATTERN.toString())


            when (inputEditText.id){

                 R.id.etUpdateFirstName, R.id.etUpdateLastName, R.id.etUpdateEnterprise-> {

                    inputEditText.doOnTextChanged{ text, start, before, count ->

                        if(text!!.length < 3){

                            inputLayout.error = "Enter Value!"

                            binding.btUpdateUser.isClickable = false

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.disabled_button)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                            getColor(requireContext(),R.color.colorSilver))

                        }else {

                            inputLayout.error = null

                            binding.btUpdateUser.isClickable = true

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.button_style_2)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                            getColor(requireContext(),R.color.white))

                        }

                    }

                }

                R.id.etUpdatePhone -> {

                    inputEditText.doOnTextChanged{ text, start, before, count ->

                        if(!phoneCheck.matcher(text).matches()){

                            inputLayout.error = "Enter Value!"

                            binding.btUpdateUser.isClickable = false

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.disabled_button)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                            getColor(requireContext(),R.color.colorSilver))

                        }else {

                            inputLayout.error = null

                            binding.btUpdateUser.isClickable = true

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.button_style_2)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                            getColor(requireContext(),R.color.white))

                        }

                    }

                }

                R.id.etUpdateEmail -> {

                    inputEditText.doOnTextChanged{ text, start, before, count ->

                        if(!emailCheck.matcher(text).matches()){

                            inputLayout.error = "Enter Value!"

                            binding.btUpdateUser.isClickable = false

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.disabled_button)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                            getColor(requireContext(),R.color.colorSilver))

                        } else {

                            inputLayout.error = null

                            binding.btUpdateUser.isClickable = true

                            binding.btUpdateUser.background =
                                ContextCompat.getDrawable(requireContext(),
                                    R.drawable.button_style_2)

                            binding.btUpdateUser.setTextColor(ContextCompat.
                                getColor(requireContext(),R.color.white))

                        }

                    }

                }

            }

        }

    }