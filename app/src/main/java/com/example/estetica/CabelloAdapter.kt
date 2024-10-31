package com.example.estetica

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

/*La función Adapter permite obtener los datos de la clase Unas*/
class CabelloAdapter(private val cabelloList: ArrayList<Cabello>) : RecyclerView.Adapter<CabelloAdapter.MyViewHolder>(){

    var onItemClick : ((Cabello) -> Unit)? = null //Inicializa onItemClick

    //Retorna la vista de cada uno de los item de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2,
        parent,false)
        return MyViewHolder(itemView)
    }

    //Cuenta el número total de elementos de la lista
    override fun getItemCount(): Int {
        return cabelloList.size
    }

    //Permitr obtener y establecer los valores de cada elemento de la lista con sus respectivos datos
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemActual = cabelloList[position]
        holder.tituloImg.setImageResource(itemActual.tituloImg) //Se añade la imagen respectiva del elemento
        holder.titulo.text = itemActual.titulo //Se añade el respectivo título del elemento

        //Permite al usuario dar clic en los elementos de la lista para ver con mayor detalle
        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(itemActual)
        }

    }

    //Obtiene los valores de los elementos que se encuentran en el layout
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tituloImg : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val titulo : TextView = itemView.findViewById(R.id.tvHeading)
    }

}