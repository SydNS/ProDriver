package com.example.prodriver.Views

//import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.prodriver.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var uAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 101

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val splashcreentext = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash
        )
        splash.animation=splashcreentext

        displayButton() 
        signinwithphone.setOnClickListener {
            phonedetails.visibility=View.VISIBLE
            buttons.visibility=View.INVISIBLE
        }
        verifyButton.setOnClickListener {
            startActivity(Intent(this,Info::class.java))
        }

    }

    private fun displayButton() {
        val handler = Handler()
        handler.postDelayed({
            buttons.visibility= View.VISIBLE
            buttons.animation=AnimationUtils.loadAnimation(applicationContext,
                R.anim.splash
            )
        }, 2000)
    }


}