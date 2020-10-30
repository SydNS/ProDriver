@file:Suppress("DEPRECATION")
package com.example.prodriver.Views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.prodriver.R
import kotlinx.android.synthetic.main.splashscreen.*


class SplashScreen : Activity() {
    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.anim)
        val splashcreentext = AnimationUtils.loadAnimation(applicationContext, R.anim.splashcreentext)

        logo.animation = animation
        splashname.animation=splashcreentext

        //this code will pause the app for 1.5 secs and then any thing in run method will run.

        val handler = Handler()
        handler.postDelayed({
//            val userPref = applicationContext.getSharedPreferences("user", MODE_PRIVATE)
//            val isLoggedIn = userPref.getBoolean("isLoggedIn", false)
//            if (isLoggedIn) {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            } else {
                isFirstTime
//            }
        }, 4000)
    }

    //start Auth Activity// if its true then its first time and we will change it false

    // start Onboard activity
    //for checking if the app is running for the very first time
    //we need to save a value to shared preferences

    private val isFirstTime:
    //default value true
            Unit
        get() {
            //for checking if the app is running for the very first time
            //we need to save a value to shared preferences
            val preferences = application.getSharedPreferences("onBoard", MODE_PRIVATE)
            val isFirstTime = preferences.getBoolean("isFirstTime", true)
            //default value true

            if (isFirstTime) {
                // if its true then its first time and we will change it false
                val editor = preferences.edit()
                editor.putBoolean("isFirstTime", false)
                editor.apply()

                // start Onboard activity
                startActivity(Intent(this, OnBoardActivity::class.java))
                finish()
            } else {
                //start Auth Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
}