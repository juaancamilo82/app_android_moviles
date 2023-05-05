package co.edu.eam.unilocal.Controller

import co.edu.eam.unilocal.Model.*

 open class CrudController {

     fun searchUser(email: String): Usuario? {
        val userList = ArrayUsuario.getInstance().myArrayList
        val user = userList.find { Usuario ->
            Usuario.email.equals(email, ignoreCase = true)
        }
        return user
    }

    fun searchLPlace(name: String): Lugar?{
        val placeList = ArrayLugares.getInstance().myArrayList
        val place = placeList.find { Lugar ->
            Lugar.nombre.equals(name, ignoreCase = true)
        }
        return place
    }

     fun searchAutoricedPlace(name: String): Lugar?{
         val placeList = ArrayLugaresAutorizados.getInstance().myArrayList
         val place = placeList.find { LugaresAutorizados ->
             LugaresAutorizados.nombre.equals(name, ignoreCase = true)
         }
         return place
     }

     fun searchSesion(email: String): Sesion? {
         val sesionList = ArraySesiones.getInstance().myArrayList
         val sesion = sesionList.find { Sesion ->
             Sesion.usuario.email.equals(email, ignoreCase = true)
         }
         return sesion
     }


     fun searchAutoricedPlaceByPos(posicionActual: Int): Lugar? {
         val nombre = ArrayLugaresAutorizados.getInstance().myArrayList.get(posicionActual).nombre
         val listaLugaresAutorizados = ArrayLugaresAutorizados.getInstance().myArrayList
         val lugarAutorizado = listaLugaresAutorizados.find { LugaresAutorizados ->
             LugaresAutorizados.nombre.equals(nombre, ignoreCase = true)
         }
         return lugarAutorizado
     }


     fun eliminarLugarRegistrado(indice: Int, listaLugares: ArrayList<Lugar>, lugaresAutorizados: ArrayList<Lugar>) {
         if (indice >= 0 && indice < listaLugares.size) {
             listaLugares.removeAt(indice)
             lugaresAutorizados.removeAt(indice)
         } else {
             // Manejar el caso de un índice inválido si es necesario
         }
     }

     fun agregarLugarAfavoritos(lugar:Lugar?, usuario:Usuario?){
         if (lugar != null) {
             usuario?.agregarLugarFavorito(lugar)
         }
     }

     fun eliminarLugarFavorito(lugar: Lugar?, usuario:Usuario?){
         usuario?.lugaresFavoritos?.remove(lugar)
     }


     fun guardarLugar(lugar:Lugar?, usuario:Usuario?){
         if (lugar != null) {
             usuario?.guardarLugar(lugar)
         }
     }

     fun eliminarLugarGuardado(lugar: Lugar?, usuario:Usuario?){
         usuario?.lugaresGuardados?.remove(lugar)
     }

}