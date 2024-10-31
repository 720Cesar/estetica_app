package com.example.estetica

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.estetica.databinding.ActivityAuthGitHubBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


class authGitHub : AppCompatActivity() {

    lateinit var binding: ActivityAuthGitHubBinding
    lateinit var auth: FirebaseAuth
    private val CHANNEL_ID = "channel_id_ei"
    private val notificacionID = 113

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthGitHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        crearCanalNotificacion()
        val email = binding.txtEmailGit
        val ingresa = binding.btnAuthGit
        val volver = binding.btnVolverGit

        volver.setOnClickListener(){
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ingresa.setOnClickListener(){
            if(email.text.isEmpty()){
                email.setError("Ingresa tu correo")
            }else{
                val provider = OAuthProvider.newBuilder("github.com")
                provider.addCustomParameter("login", email.text.toString())
                provider.scopes = listOf("user:email")
                val pendingResultTask = auth.pendingAuthResult
                if (pendingResultTask != null) {
                    pendingResultTask
                        .addOnSuccessListener {
                            Toast.makeText(this, "Autenticación exitosa", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    auth
                        .startActivityForSignInWithProvider(this, provider.build())
                        .addOnSuccessListener {
                            abrirHuella()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                        }
                }

            }
        }

    }

    private fun abrirHuella() {
        mandarNotificacion()
        val intent: Intent = Intent(this, authHuella::class.java)
        startActivity(intent)
    }

    //FUNCIÓN QUE PERMITE CREAR EL CANAL DE NOTIFICACIONES
    private fun crearCanalNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val titulo = "AVISO SOBRE INICIO DE SESIÓN"
            val desc = "Inicio de sesión exitoso. Bienvenido"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(CHANNEL_ID, titulo, importancia).apply {
                description = desc //Se asigna la descripcón a la notificación
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)

        }
    }

    //FUNCIÓN QUE PERMITE ENVIAR NOTIFICACIONES PERSONALIZADAS
    private fun mandarNotificacion(){

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.fondohorizontal)
        val bitmap2 = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)

        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_email)
            .setContentTitle("AVISO SOBRE INICIO DE SESIÓN") //Título de la notificacón
            .setContentText("Inicio de sesión exitoso. Bienvenido") //Descripción de la notificación
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap)) //Agregar imagen larga a la notificación
            .setLargeIcon(bitmap2) //Agregar logo de la estética
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Establecer prioridad de la notificación
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@authGitHub,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificacionID,builder.build() )
        }


    }

}