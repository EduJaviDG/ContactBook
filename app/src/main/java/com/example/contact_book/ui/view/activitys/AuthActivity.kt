package com.example.contact_book.ui.view.activitys

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.contact_book.R
import com.example.contact_book.databinding.AuthActivityBinding
import com.example.contact_book.domain.model.Session
import com.example.contact_book.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val viewModelToken by viewModels<TokenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        sessionIsActive()

        setupActionBarWithNavController(findNavController(R.id.authFragment))

    }

    override fun onSupportNavigateUp(): Boolean {

        val navControler = findNavController(R.id.authFragment)

        return navControler.navigateUp() || super.onSupportNavigateUp()
    }


    private fun sessionIsActive() {

        val session = Session(activity = this)

        viewModelToken.readToken.observe(this) { token ->

            if (token?.isNotEmpty()!! && session.isActive(token?.last()?.id)) {

                val intent = Intent(this, MainActivity::class.java)

                Handler(Looper.getMainLooper()).postDelayed({

                    startActivity(intent)

                    finish()

                }, 1500)

            }

        }

    }

}

