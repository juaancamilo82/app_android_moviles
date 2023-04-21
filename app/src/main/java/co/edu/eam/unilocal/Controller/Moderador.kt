package co.edu.eam.unilocal

class Moderador(nombre: String, email: String, password: String, lugaresRegistrados: ArrayList<Lugar>?,
                lugaresGuardados: ArrayList<Lugar>?, lugaresFavoritos: ArrayList<Lugar>?,
                codigoModerador: Long, lugaresAprobados: ArrayList<Lugar>?, lugaresReprobados: ArrayList<Lugar>?)
    : Usuario(nombre, email, password, lugaresRegistrados, lugaresGuardados, lugaresFavoritos) {

    var codigoModerador: Long = codigoModerador
    var lugaresAprobados: ArrayList<Lugar>? = lugaresAprobados
    var lugaresReprobados: ArrayList<Lugar>? = lugaresReprobados
}

