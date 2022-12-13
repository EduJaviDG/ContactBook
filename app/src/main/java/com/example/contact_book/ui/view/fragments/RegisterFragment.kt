package com.example.contact_book.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.contact_book.R
import com.example.contact_book.data.resources.Resource
import com.example.contact_book.databinding.FragmentRegisterBinding
import com.example.contact_book.domain.model.Alert
import com.example.contact_book.viewmodel.AuthViewModel
import com.example.contact_book.viewmodel.FirestoreViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModelRegister by viewModels<AuthViewModel>()
    private val viewModelFireStore by viewModels<FirestoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //alert = Alert()

        binding = FragmentRegisterBinding.bind(view)

        binding.btRegister.setOnClickListener(listener)


    }

    val listener = View.OnClickListener { view ->

        when (view.id) {

            R.id.btRegister -> {

                val name = binding.etNameReg.text.toString()

                val email = binding.etEmailReg.text.toString()

                val password = binding.etPasswordReg.text.toString()

                val check = binding.etCheck.text.toString()

                when {

                    inputCheck() && password == check -> {

                        viewModelRegister.signUp(
                            name = name, email = email,
                            password = password
                        )

                        val taskRegister = viewModelRegister.signUp

                        stateRegister(taskRegister)


                    }

                    !inputCheck() && password != check ||
                            !inputCheck() && password == check -> {

                        Alert.showError(
                            context = requireContext(),
                            message = getString(R.string.empty)
                        )

                    }

                    inputCheck() && password != check -> {

                        Alert.showError(
                            context = requireContext(), message =
                            getString(R.string.match)
                        )

                    }

                }

            }

        }

    }

    private fun inputCheck(): Boolean {
        return binding.etNameReg.text!!.isNotEmpty() &&
                binding.etEmailReg.text!!.isNotEmpty() &&
                binding.etPasswordReg.text!!.isNotEmpty() &&
                binding.etCheck.text!!.isNotEmpty()
    }

    private fun stateRegister(taskRegister: LiveData<Resource<FirebaseUser>?>) {


        taskRegister.observe(this, object: Observer<Resource<FirebaseUser>?>{

            override fun onChanged(action: Resource<FirebaseUser>?) {

                when (action) {

                    is Resource.Error -> {

                        binding.cvProgressR.visibility = View.INVISIBLE

                        Alert.showError(context = requireContext(), message = action.exception?.message.toString())

                        taskRegister.removeObserver(this)

                    }
                    is Resource.Success -> {

                        binding.cvProgressR.visibility = View.INVISIBLE

                        val name = binding.etNameReg.text.toString()

                        val email = binding.etEmailReg.text.toString()

                        viewModelFireStore.addUser(name = name, email = email)

                        Alert.showSuccess(
                            context = requireContext(),
                            message = getString(R.string.succesfuly)
                        ) { result ->

                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                        }

                        taskRegister.removeObserver(this)

                    }

                    Resource.inProgress -> {

                        binding.cvProgressR.visibility = View.VISIBLE

                    }

                }

            }

        } )

       }

    }

