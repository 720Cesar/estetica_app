package com.example.estetica

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

/*Esta clase permite la creación de los Fragment
* mediante el uso del fragmentManager*/

class FragmentPageAdapter (

    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
    ): FragmentStateAdapter(fragmentManager, lifecycle){

    //Realiza un conteo de cuantas pestañas se van a usar, en este caso, 3
    override fun getItemCount(): Int {
        return 3
    }

    //Permite crear y establecer las vistas dependiendo de la posición
    override fun createFragment(position: Int): Fragment {
        //En caso de que la posición sea 0 (Que esté en el pestaña 1 de cabello)
            return if(position == 0){
                CabelloFragment() //Entonces se llama al ActivityFragment del Cabello
            }else if(position == 1){
                UniasFragment()
            }else{
                ProductosFragment()
            }
    }


}