package co.edu.eam.unilocal.Model

import co.edu.eam.unilocal.Model.Lugar
import co.edu.eam.unilocal.Model.Usuario

class Moderador(nombre: String, email: String, password: String, lugaresRegistrados: ArrayList<Lugar>?,
                lugaresGuardados: ArrayList<Lugar>?, lugaresFavoritos: ArrayList<Lugar>?,
                codigoModerador: Long, lugaresAprobados: ArrayList<Lugar>?, lugaresReprobados: ArrayList<Lugar>?)
    : Usuario(nombre, email, password, lugaresRegistrados, lugaresGuardados, lugaresFavoritos) {

    var codigoModerador: Long = codigoModerador
    var lugaresAprobados: ArrayList<Lugar>? = lugaresAprobados
    var lugaresReprobados: ArrayList<Lugar>? = lugaresReprobados
}

