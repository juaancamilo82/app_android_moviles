package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usuarios = ArrayUsuario.getInstance().myArrayList

        val nuevoUsuario = Usuario(
            "Steven Cardona",
            "1",
            "1",null
        )
        usuarios.add(nuevoUsuario)

        val nuevoUsuario2 =
            Usuario(
                "Vanessa Valencia",
                "2",
                "2",null
            )
        usuarios.add(nuevoUsuario2)

    }

    fun irAlRegistro(v: View) {
        val intent = Intent(this, RegisroActivity::class.java)
        startActivity(intent)
    }
    fun loguear(v: View) {
            val listaUsuarios = ArrayUsuario.getInstance().myArrayList
            if(listaUsuarios!=null){
                val email = findViewById<EditText>(R.id.emailLogin).text.toString()
                val password = findViewById<EditText>(R.id.passLogin).text.toString()
                val usuario = listaUsuarios.find { Usuario ->
                    Usuario.email.equals(email) && Usuario.password.equals(password)
                }
                if(usuario!=null){
                    val lugar = buscarLugar()
                    val intent = Intent(this, InterfazUsuario::class.java)
                    intent.putExtra("email", usuario.email.toString())
                    intent.putExtra("nombreLugar", lugar?.nombre.toString())

                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show()
            }
    }

    private fun buscarLugar(): Lugar?{
        val email = findViewById<EditText>(R.id.emailLogin).text.toString()
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val lugar = listaLugares.find { Lugar ->
            Lugar.correoUsuario.equals(email)
        }
        return lugar
    }

}

