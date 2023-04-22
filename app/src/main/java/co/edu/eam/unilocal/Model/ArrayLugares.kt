package co.edu.eam.unilocal.Model

class ArrayLugares {

    val myArrayList = ArrayList<Lugar>()
    companion object {
        private val instance = ArrayLugares()
        fun getInstance(): ArrayLugares {
            return instance
        }
    }
}




