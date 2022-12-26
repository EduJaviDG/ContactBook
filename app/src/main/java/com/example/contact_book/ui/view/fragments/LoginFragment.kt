package com.example.contact_book.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.contact_book.R
import com.example.contact_book.resource.Resource
import com.example.contact_book.data.database.token.Token
import com.example.contact_book.databinding.FragmentLoginBinding
import com.example.contact_book.domain.model.Alert
import com.example.contact_book.domain.model.Session
import com.example.contact_book.ui.view.activitys.MainActivity
import com.example.contact_book.viewmodel.AuthViewModel
import com.example.contact_book.viewmodel.TokenViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModelLogin by viewModels<AuthViewModel>()
    private val viewModelToken by viewModels<TokenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        binding.tvRegister.setOnClickListener(listener)

        binding.btLogin.setOnClickListener(listener)

    }

    val listener = View.OnClickListener { view ->

        when(view.id){

            R.id.tvRegister -> {

                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            R.id.btLogin -> {

                initLogin()

            }

        }

    }

    private fun initLogin() {

        val password = binding.etPasswordLog.text.toString()

        val email = binding.etEmailLog.text.toString()

        if(checkInput()){

            viewModelLogin.login(email = email, password = password)

            val taskLogin = viewModelLogin.login

            stateLogin(taskLogin)

        }else {

            Alert.showError( getString(R.string.empty), requireContext() )

        }


    }

    private fun stateLogin(taskLogin: LiveData<Resource<FirebaseUser>?>) {

            taskLogin.observe(this, object: Observer<Resource<FirebaseUser>?> {

                override fun onChanged(action: Resource<FirebaseUser>?) {

                    when(action){

                        is Resource.Error -> {

                            binding.cvProgressL.visibility = View.INVISIBLE

                            Alert.showError(action.exception.message.toString(),requireContext())

                            taskLogin.removeObserver(this)

                        }

                        is Resource.Success -> {

                            binding.cvProgressL.visibility = View.INVISIBLE

                            val tokenId = viewModelLogin.currentUser?.
                            getIdToken(true).toString()

                            val tokenUid = viewModelLogin.currentUser?.uid.toString()

                            println("Token of current {$tokenId}")

                            val token = Token(uid = tokenUid, id = tokenId)

                            segueToApp(token)

                            taskLogin.removeObserver(this)

                        }

                        Resource.inProgress -> {

                            binding.cvProgressL.visibility = View.VISIBLE

                        }

                    }

                }

            })

        }

    private fun checkInput(): Boolean {

        return !binding.etEmailLog.text!!.isNullOrEmpty() &&
               !binding.etPasswordLog.text!!.isNullOrEmpty()

    }


    private fun segueToApp(token: Token){

        val session = Session(requireActivity())

        session.saveToken(token.id)

        viewModelToken.insertToken(token)

        val intent = Intent(requireContext(),MainActivity::class.java)

         startActivity(intent)

    }



    }


