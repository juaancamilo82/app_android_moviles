package co.edu.eam.unilocal.Model

class ArraySesiones {

    val myArrayList = ArrayList<Sesion>()
    companion object {
        private val instance = ArraySesiones()
        fun getInstance(): ArraySesiones {
            return instance
        }
    }
}