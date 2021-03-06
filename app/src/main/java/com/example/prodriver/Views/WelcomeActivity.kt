@file:Suppress("DEPRECATION")
package com.example.prodriver.Views

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.prodriver.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth? = null
    private val firebaseAuthListner: AuthStateListener? = null
    private val currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )


//        mAuth = FirebaseAuth.getInstance();
//
//        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
//            {
//                currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                if(currentUser != null)
//                {
//                    Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
//                    startActivity(intent);
//                }
//            }
//        };
        driver_welcome_btn?.setOnClickListener {
            val driverIntent = Intent(this,DriverLoginRegisterActivity::class.java)
            startActivity(driverIntent)
//            finish()
        }
        customer_welcome_btn?.setOnClickListener {
            val customerIntent = Intent(this,CustomerLoginRegisterActivity::class.java            )
            startActivity(customerIntent)
//            finish()
        }
    }
    //    @Override
    //    protected void onStart()
    //    {
    //        super.onStart();
    //
    //        mAuth.addAuthStateListener(firebaseAuthListner);
    //    }
    //
    //
    //    @Override
    //    protected void onStop()
    //    {
    //        super.onStop();
    //
    //        mAuth.removeAuthStateListener(firebaseAuthListner);
    //    }
}