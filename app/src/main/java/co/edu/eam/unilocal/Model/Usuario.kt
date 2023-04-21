package co.edu.eam.unilocal

import android.graphics.Bitmap
import android.net.Uri

open class Usuario (nombre: String, email:String, password:String, lugaresRegistrados: ArrayList<Lugar>?,
                    lugaresGuardados:ArrayList<Lugar>?, lugaresFavoritos:ArrayList<Lugar>?) {

    var nombre: String = nombre
    var email: String = email
    var password: String = password
    var lugaresRegistrados: ArrayList<Lugar>? = lugaresRegistrados
    var lugaresGuardados: ArrayList<Lugar>? = lugaresGuardados
    var lugaresFavoritos: ArrayList<Lugar>? = lugaresFavoritos
}

