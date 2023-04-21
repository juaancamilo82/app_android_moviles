package co.edu.eam.unilocal

class Lugar (nombre: String, categoria: String, direccion: String, referencia: String, horario: String, numeroTelefono: Long,
correoUsuario:String, estaAorobado:Boolean?) {

    var nombre: String = nombre
    var categoria: String = categoria
    var direccion: String = direccion
    var referencia: String = referencia
    var horario: String = horario
    var telefono: Long = numeroTelefono
    var correoUsuario: String?=correoUsuario
    var estaAprobado: Boolean?=estaAorobado
}