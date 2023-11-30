package com.example.chatme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val splashProgressBar: ProgressBar = findViewById(R.id.pb)

        // Set up the rotating animation
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnimation.duration = 300 // Set the duration of the animation in milliseconds
        rotateAnimation.repeatCount = Animation.INFINITE // Set to repeat indefinitely
        rotateAnimation.interpolator = android.view.animation.LinearInterpolator()

        // Set the animation to the ProgressBar
        splashProgressBar.startAnimation(rotateAnimation)
        Handler().postDelayed({
var auth=FirebaseAuth.getInstance()
            var sharedPreferences=getPreferences(Context.MODE_PRIVATE)
            if (auth.currentUser != null && sharedPreferences.getString("rememberMe","yes").equals("yes")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()}
            else {
            startActivity(Intent(this, Loginpage::class.java))
                finish()
                }
            }, 1000)



    }



    }
