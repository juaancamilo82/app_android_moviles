package co.edu.eam.unilocal.Model

class ArrayUsuario {

    val myArrayList = ArrayList<Usuario>()
    companion object {
        private val instance = ArrayUsuario()
        fun getInstance(): ArrayUsuario {
            return instance
        }
    }
}

