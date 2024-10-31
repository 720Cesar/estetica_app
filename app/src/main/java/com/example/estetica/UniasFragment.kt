package com.example.estetica

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*El uso de Fragment se debe a la implementación de Tab, es decir, las pestañas que
* permiten cambiar entre elementos de un layout a otro sin cambiar de Activity
* Muchos de los elementos son propios de los Fragment, solo los elementos comentados
* son aquellos que fueron agregados*/

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UniasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UniasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Declarar variables para mostrar la lista
    private lateinit var adapter: UnasAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var unasArrayList: ArrayList<Unas>

    lateinit var imgId : Array<Int>
    lateinit var titulo : Array<String>
    lateinit var unas: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaLista() //Función que obtiene los elementos de la lista
        val layoutManager = LinearLayoutManager(context)

        //Referencia al RecyclerView, es decir, el elemento que permite usar listas
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        //FixedSize indica que los elementos de la lista tienen el mismo ancho y alto
        recyclerView.setHasFixedSize(true)
        //Referencia a la clase Adapter
        adapter = UnasAdapter(unasArrayList)
        recyclerView.adapter = adapter

        //Permite que se inicie la Activity que detalla al item que selecciona el usuario
        adapter.onItemClick = {
            val intent = Intent(activity, detalladoC::class.java)
            intent.putExtra("unas",it)  //Se envía el texto a la Activity detalladoC para que funcione correctamente
            startActivity(intent)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unias, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UniasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UniasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //La función permite obtener todos los elementos de imagenes y titulos en el proyecto
    //Los elementos llamados se almacenan en un Array
    private fun iniciaLista(){
        unasArrayList = arrayListOf<Unas>()

        imgId = arrayOf(
            R.drawable.u1,
            R.drawable.u2,
            R.drawable.u3,
            R.drawable.u4,
            R.drawable.u5,
            R.drawable.u6,
            R.drawable.u7,
            R.drawable.u8,
            R.drawable.u9
        )

        titulo = arrayOf(
            getString(R.string.u1Titulo),
            getString(R.string.u2Titulo),
            getString(R.string.u3Titulo),
            getString(R.string.u4Titulo),
            getString(R.string.u5Titulo),
            getString(R.string.u6Titulo),
            getString(R.string.u7Titulo),
            getString(R.string.u8Titulo),
            getString(R.string.u9Titulo)

        )

        unas = arrayOf(
            getString(R.string.unas1),
            getString(R.string.unas2),
            getString(R.string.unas3),
            getString(R.string.unas4),
            getString(R.string.unas5),
            getString(R.string.unas6),
            getString(R.string.unas7),
            getString(R.string.unas8),
            getString(R.string.unas9)
        )

        //Realiza el recorrido de los indices
        for (i in imgId.indices){
            val unas = Unas(imgId[i],titulo[i])
            //Guarda cada uno de los valores obtenidos al Array
            unasArrayList.add(unas)
        }

    }

}