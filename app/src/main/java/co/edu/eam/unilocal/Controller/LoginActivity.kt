package co.edu.eam.unilocal.Controller

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.commit
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.View.home.HomeFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var crudController: CrudController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        crudController = CrudController()
        quemarInfo()
    }

    fun irAlRegistro(v: View) {
        val intent = Intent(this, RegisroActivity::class.java)
        startActivity(intent)
    }
    fun loguear(v: View) {

        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val email = findViewById<EditText>(R.id.emailLogin).text.toString()
        val password = findViewById<EditText>(R.id.passLogin).text.toString()

        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email, ignoreCase = true) && Usuario.password.equals(password)
        }

        if(usuario!=null){

            val listaSesiones = ArraySesiones.getInstance().myArrayList
            val sesion = Sesion(usuario)
            listaSesiones.add(sesion)
                val lugar = crudController.searchLPlace(email)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("nombreLugar", lugar?.nombre.toString())
                startActivity(intent)

        }else{
            Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun quemarInfo(){

        val usuarios = ArrayUsuario.getInstance().myArrayList

        val nuevoUsuario = Usuario(
            "Steven Cardona",
            "1",
            "1",null,
            null,
            null,null
        )
        usuarios.add(nuevoUsuario)

        val nuevoUsuario2 =
            Usuario(
                "Vanessa Valencia",
                "2",
                "2",null,
                null,
                null,null
            )
        usuarios.add(nuevoUsuario2)

        val nuevoModerador =
            Moderador(
                nombre = "Steven Cardona Pérez",
                email = "3",
                password = "3",
                lugaresRegistrados = null,
                lugaresGuardados = null,
                lugaresFavoritos = null,
                codigoModerador = 230923,
                lugaresAprobados = null,
                lugaresReprobados = null
            )
            usuarios.add(nuevoModerador)
    }
}


