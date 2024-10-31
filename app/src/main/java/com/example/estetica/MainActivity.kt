package com.example.estetica

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.estetica.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider

class MainActivity : AppCompatActivity() {

    //Uso del Binding
    lateinit var binding: ActivityMainBinding

    lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    //Variables de notificaciones
    private val CHANNEL_ID = "channel_id_ei"
    private val notificacionID = 113


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        val google = binding.cardGoogle
        val twitter = binding.cardTwitter
        val git = binding.cardGitHub
        val mapa = binding.btnMapa
        val email = binding.txtEmail
        val pass = binding.txtPass
        val ingresar = binding.btnIngresar

        crearCanalNotificacion()
        iniciaAnimacion()

        ingresar.setOnClickListener(){
            if(email.text.isEmpty()){
                email.setError("Ingresa tu correo")
            }else if(pass.text.isEmpty()){
                pass.setError("Ingresa tu contraseña")
            }else{
                ingresar(email.text.toString().trim(), pass.text.toString().trim())
            }
        }

        google.setOnClickListener(){
            ingresaGoogle()
        }

        twitter.setOnClickListener(){
            ingresaTwitter()
        }

        git.setOnClickListener(){
            val intent: Intent = Intent(this, authGitHub::class.java)
            startActivity(intent)
        }

        mapa.setOnClickListener(){
            val intent: Intent = Intent(this, mapaGps::class.java)
            startActivity(intent)
        }

    }



    //AUTENTICACIÓN CORREO ELECTRÓNICO
    fun ingresar(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { taks ->

            if (taks.isSuccessful) {
                val usuario = auth.currentUser
                Toast.makeText(baseContext, "Ingreso exitoso", Toast.LENGTH_SHORT).show()
                mandarNotificacion()
                mostrarperfil()
            } else {
                Toast.makeText(baseContext, "Error de email/contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun mostrarperfil() {
        val intent = Intent(this, authHuella::class.java)
        startActivity(intent)
    }

    //AUTENTICACIÓN CON GOOGLE
    private fun ingresaGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(this , authHuella::class.java)
                mandarNotificacion()
                intent.putExtra("email" , account.email)
                intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()

            }
        }
    }

    //AUTENTICACIÓN CON TWITTER
    private fun ingresaTwitter() {
        val provider = OAuthProvider.newBuilder("twitter.com")
        provider.addCustomParameter("lang", "mx")

        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    Toast.makeText(this, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                    mandarNotificacion()
                    val intent: Intent = Intent(this, authHuella::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
        } else {
            auth
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    Toast.makeText(this, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                    mandarNotificacion()
                    val intent: Intent = Intent(this, authHuella::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
        }
    }

    //ANIMACIONES
    private fun iniciaAnimacion(){
        // Se inicializan las animaciones
        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_dowm = AnimationUtils.loadAnimation(this,R.anim.bottom_down)

        // Configurando Bottom_Down en el layout de decoración de arriba
        binding.linearLayoutArriba.animation = bottom_dowm

        // Creación de un handler para los demás layouts
        var handler = Handler()
        var runnable = Runnable{
            binding.cardLogo.animation = fade_in
            binding.cardView5.animation = fade_in
            binding.cardGitHub.animation = fade_in
            binding.cardGoogle.animation = fade_in
            binding.cardTwitter.animation = fade_in
            binding.txtBienvenido.animation = fade_in
            binding.btnMapa.animation = fade_in
        }

        handler.postDelayed(runnable, 1000)
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
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificacionID,builder.build() )
        }


    }

    override fun onBackPressed() {} //Evita que el usuario regrese a una Activity equivocada

}