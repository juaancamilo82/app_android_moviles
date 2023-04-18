package co.edu.eam.unilocal

import android.graphics.Bitmap
import android.net.Uri

class Usuario (nombre: String, email:String, password:String, lugaresRegistrados: ArrayList<Lugar>?) {

    var nombre: String = nombre
    var email: String = email
    var password: String = password
    var lugaresRegistrados: ArrayList<Lugar>? = lugaresRegistrados
}

