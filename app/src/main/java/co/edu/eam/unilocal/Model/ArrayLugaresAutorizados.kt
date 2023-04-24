package co.edu.eam.unilocal.Model

class ArrayLugaresAutorizados {

    val myArrayList = ArrayList<Lugar>()
    companion object {
        private val instance = ArrayLugaresAutorizados()
        fun getInstance(): ArrayLugaresAutorizados {
            return instance
        }
    }
}