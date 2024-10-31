package com.example.estetica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.estetica.databinding.ActivityMainBinding
import com.example.estetica.databinding.ActivitySplashBinding


class Splash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fondo = binding.fondo
        val logo = binding.imgLogo
        val nombre = binding.nombre
        val lottie = binding.lottieImg

        //Se animan cada uno de los elementos del Splash
        //Se asigna una duración de 1s, delay de 3s y el movimiento
        fondo.animate().setDuration(1000).setStartDelay(3000).translationY(-2600f)
        logo.animate().setDuration(1000).setStartDelay(3000)?.translationY(2400f)
        nombre.animate().setDuration(1000).setStartDelay(3000)?.translationY(2400f)
        lottie.animate().setDuration(1000).setStartDelay(3000)?.translationY(2400f)


        //Se hace uso de un handler para iniciar la actividad después del Splash
        val mHandler = Handler()
        mHandler.postDelayed({
            //Se inicia la activity del inicio de sesión después de 4.6s de iniciar el Splash
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 4600L)

    }
}
