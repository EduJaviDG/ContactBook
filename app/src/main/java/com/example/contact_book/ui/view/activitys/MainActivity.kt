package com.example.contact_book.ui.view.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.contact_book.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.mainFragemnt))

    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.mainFragemnt)

        return  navController.navigateUp() || super.onSupportNavigateUp()
    }

}