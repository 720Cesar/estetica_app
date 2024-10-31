package com.example.estetica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

/*En esta Activity se muestran los Fragment de Cuenta, Uñas y Cabello.
* Además, aquí es donde se hace uso de las Tab o pestañas que permiten
* la navegación entre los Fragment (Las listas y el cerrado de sesión)*/

private lateinit var tabLayout: TabLayout
private lateinit var viewPager2: ViewPager2
private lateinit var adapter: FragmentPageAdapter

class tabHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_home)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)

        //Se añaden las tres pestañas que se observan en el layout
        tabLayout.addTab(tabLayout.newTab().setText("Cabello"))
        tabLayout.addTab(tabLayout.newTab().setText("Uñas"))
        tabLayout.addTab(tabLayout.newTab().setText("Cuenta"))

        viewPager2.adapter = adapter

        //Permite al usuario mostrar el fragmente indicado a partir de la posición en la que se encuentre
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null){
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        //Muestra al usuario la vista del fragment depeniendo de la posición correspondiente
        //Es decir, muestra las diferentes vistas
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

    override fun onBackPressed() {} //Evita que el usuario regrese a una Activity equivocada

}