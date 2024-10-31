package com.example.estetica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

/*El uso princiapl de esta Activity es el obtener los datos
* de las imagenes y los titulos de cada item de la lista.
* En esta única Actitity se reciben valors de cabello y uñas*/

class detalladoC : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detallado_c)

        /*Cada uno de "Nuevo" recibe el valor textual de "name", el cual
        * permite detectar si la información se recibió correctamente desde
        * cada Fragment, tanto de cabello como de uñas*/
        val nuevo = intent.getParcelableExtra<Cabello>("cabello")
        val nuevo2 = intent.getParcelableExtra<Unas>("unas")

        /*En caso de recibir un valor, asigna los datos obtenidos de cada clase
        * a los elementos de la activity que en este caso con el título y la imagen*/
        if (nuevo != null){
            val textView: TextView = findViewById(R.id.tituloDetallado)
            val imageView: ImageView = findViewById(R.id.imgDetallado)

            textView.text = nuevo.titulo
            imageView.setImageResource((nuevo.tituloImg))

        }
        if(nuevo2 != null){
            val textView: TextView = findViewById(R.id.tituloDetallado)
            val imageView: ImageView = findViewById(R.id.imgDetallado)

            textView.text = nuevo2.titulo
            imageView.setImageResource((nuevo2.tituloImg))

        }


    }
}