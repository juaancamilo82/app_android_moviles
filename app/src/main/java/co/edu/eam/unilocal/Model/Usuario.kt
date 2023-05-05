package co.edu.eam.unilocal.Model

import android.net.Uri

open class Usuario (nombre: String, email:String, password:String, lugaresRegistrados: ArrayList<Lugar>?,
                    lugaresGuardados:ArrayList<Lugar>?, lugaresFavoritos:ArrayList<Lugar>?, fotoPerfil: Uri?
) {

    var nombre: String = nombre
    var email: String = email
    var password: String = password
    var lugaresRegistrados: ArrayList<Lugar>? = lugaresRegistrados
    var lugaresGuardados: ArrayList<Lugar>? = lugaresGuardados
    var lugaresFavoritos: ArrayList<Lugar>? = lugaresFavoritos
    var fotoPerfil: Uri? = fotoPerfil

    fun agregarLugarRegistrado(lugar: Lugar) {
        if (lugaresRegistrados == null) {
            lugaresRegistrados = ArrayList()
        }
        lugaresRegistrados?.add(lugar)
    }

    fun agregarLugarFavorito(lugar: Lugar) {
        if (lugaresFavoritos == null) {
            lugaresFavoritos = ArrayList()
        }
        lugaresFavoritos?.add(lugar)
    }

    fun guardarLugar(lugar: Lugar) {
        if (lugaresGuardados == null) {
            lugaresGuardados = ArrayList()
        }
        lugaresGuardados?.add(lugar)
    }

}

