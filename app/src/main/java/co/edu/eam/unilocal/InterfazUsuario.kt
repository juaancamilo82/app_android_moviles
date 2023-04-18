package co.edu.eam.unilocal

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class InterfazUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_usuario)
        cargarDatosUsuario()
        cargarDatosLugares()
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

    private fun buscarLugar(): Lugar?{
        val nombreLugar = intent.getStringExtra("nombreLugar")
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val lugar = listaLugares.find { Lugar ->
            Lugar.nombre.equals(nombreLugar)
        }
        return lugar
    }
    private fun cargarDatosUsuario(){
        val usuario = buscarUsuario()
        val nombreVista: TextView = findViewById(R.id.nombreUsuario)
        nombreVista.text = usuario?.nombre.toString()
    }

    fun cargarDatosLugares(){
        val lugar = buscarLugar()
        val nombreVista: TextView = findViewById(R.id.nombreLugarText)
        nombreVista.text = lugar?.nombre.toString()
    }

    fun abrirVentanaRegistroLugar(v: View){
        val intent = Intent(this, CrearLugar::class.java)
        val usuario = buscarUsuario()
        val correo = usuario?.email.toString()
        if(usuario!=null){
            intent.putExtra("email", correo)
            startActivity(intent)
        }
    }

}