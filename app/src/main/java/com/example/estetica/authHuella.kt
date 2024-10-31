package com.example.estetica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.estetica.databinding.ActivityAuthHuellaBinding
import java.util.concurrent.Executor

class authHuella : AppCompatActivity() {

    lateinit var binding: ActivityAuthHuellaBinding

    lateinit var ejecutor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthHuellaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ejecutor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@authHuella,ejecutor,object:BiometricPrompt.AuthenticationCallback(){

            /*Funciones que evalúan el estado de la autenticación si hay error,
            * si falla la autenticación o si es exitosa*/
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@authHuella, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@authHuella, "Falló la autenticación", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@authHuella, "Autenticación satisfactoria", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this@authHuella, tabHome::class.java)
                startActivity(intent)

            }
        })

        /*Se añade el apartado de autenticación del celular con el uso del
        * BiometricManager*/
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrico")
            .setSubtitle("Usar huella para la autenticación")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        //Abre la autenticación cuando se le da al botón
        binding.btnAutenticar.setOnClickListener(){
            biometricPrompt.authenticate(promptInfo)
        }
    }

}