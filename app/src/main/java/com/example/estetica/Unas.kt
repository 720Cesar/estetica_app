package com.example.estetica

import android.os.Parcel
import android.os.Parcelable

/*La función de la clase es pasar los datos de las imágenes y los títulos
  La clase es Kotlin de tipo Data para hacer uso de los datos y se hace la
  clase de tipo Parcelable para poder obtener los datos en una Activity*/

/*NOTA: Ninguda de las funciones dentro de Class se escribieron por el desarrollado, sino que éstas
* aparecen cuando la clase se hace Parcelable y no son modificadas*/

data class Unas(var tituloImg: Int, var titulo : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tituloImg)
        parcel.writeString(titulo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Unas> {
        override fun createFromParcel(parcel: Parcel): Unas {
            return Unas(parcel)
        }

        override fun newArray(size: Int): Array<Unas?> {
            return arrayOfNulls(size)
        }
    }
}
