package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CrearLugar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_lugar)
    }
 fun registrarLugar(v: View) {
     val lugares = ArrayLugares.getInstance().myArrayList

     val nombre = findViewById<EditText>(R.id.nombreLugar)
     val categoria = findViewById<EditText>(R.id.categoriaLugar)
     val direccion = findViewById<EditText>(R.id.direccionLugar)
     val referencia = findViewById<EditText>(R.id.referenciaLugar)
     val horario = findViewById<EditText>(R.id.horarioLugar)
     val numeroTelefono = findViewById<EditText>(R.id.numeroTelefonoLugar)

     val telStr = numeroTelefono.text.toString()

     if (nombre.text.toString().isNotEmpty()
         && categoria.text.toString().isNotEmpty()
         && direccion.text.toString().isNotEmpty()
         && referencia.text.toString().isNotEmpty()
         && horario.text.toString().isNotEmpty()
         && numeroTelefono.text.toString().isNotEmpty()
     ) {
         val usuario = buscarUsuario()
         val correoUsuario = usuario?.email.toString()
         val nuevoLugar = Lugar(
             nombre.text.toString(), categoria.text.toString(),
             direccion.text.toString(), referencia.text.toString(), horario.text.toString(),
             telStr.toLong(), correoUsuario
         )
         lugares.add(nuevoLugar)
         editarLugaresRegistrados(nuevoLugar)
         Toast.makeText(this, "Lugar registrado con éxito", Toast.LENGTH_SHORT)
             .show()
         abrirMain()
     } else {
         Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT)
             .show()
     }
 }
     private fun buscarUsuario(): Usuario? {
         val email = intent.getStringExtra("email")
         val listaUsuarios = ArrayUsuario.getInstance().myArrayList
         val usuario = listaUsuarios.find { Usuario ->
             Usuario.email.equals(email)
         }
         if(usuario!=null){
             return usuario
         }else{
             return null
         }
     }
    private fun editarLugaresRegistrados(lugar: Lugar) {
        val usuario = buscarUsuario()
        val listaLugares = usuario?.lugaresRegistrados
        listaLugares?.let {
                val indice = listaLugares.indexOf(lugar)
                if (indice != -1) {
                    listaLugares.set(indice+1, lugar)
                }else{
                    listaLugares.set(0, lugar)
                }
        }
    }
    private fun abrirMain(){
        val usuario = buscarUsuario()
        val intent = Intent(this, MainActivity::class.java)
        val correo = usuario?.email.toString()
        intent.putExtra("email", correo)
        startActivity(intent)
    }
}