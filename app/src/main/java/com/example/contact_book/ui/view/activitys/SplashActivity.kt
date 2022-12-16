package com.example.contact_book.ui.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.contact_book.data.database.token.Token
import com.example.contact_book.domain.model.Session
import com.example.contact_book.viewmodel.SplashViewModel
import com.example.contact_book.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModelToken by viewModels<TokenViewModel>()

    private val viewModelSplash by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        //setTheme(R.style.SplashScreen)

        super.onCreate(savedInstanceState)

        installSplashScreen().apply {

            setKeepOnScreenCondition{

                viewModelSplash.isloading.value!!

            }

        }

        sessionIsActive()

    }

    private fun sessionIsActive(){

        val session = Session(activity = this)

        viewModelToken.readToken.observe(this, object: Observer<List<Token>>{

            override fun onChanged(token: List<Token>?) {

                if (token?.isNotEmpty()!! && session.isActive(token?.last()?.id)) {

                    val intent = Intent(this@SplashActivity, MainActivity::class.java)

                    startActivity(intent)

                    finish()

                    viewModelToken.readToken.removeObserver(this)


                }else {

                    val intent = Intent(this@SplashActivity, AuthActivity::class.java)

                    startActivity(intent)

                    finish()

                    viewModelToken.readToken.removeObserver(this)

                }
            }

        })

    }

}