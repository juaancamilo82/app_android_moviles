package co.edu.eam.unilocal.Model

import android.net.Uri

class Lugar(
    nombre: String, categoria: String, direccion: String, referencia: String, horario: String, numeroTelefono: String,
    correoUsuario: String, fotos: List<ByteArray>, estaAutorizado:Boolean
) {

    var nombre: String = nombre
    var categoria: String = categoria
    var direccion: String = direccion
    var referencia: String = referencia
    var horario: String = horario
    var telefono: String = numeroTelefono
    var correoUsuario: String? = correoUsuario
    var estaAutorizado:Boolean? = estaAutorizado

    var fotos: List<ByteArray> = fotos

}







