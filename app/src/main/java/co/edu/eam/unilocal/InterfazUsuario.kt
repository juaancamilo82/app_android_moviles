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
    private fun cargarDatosUsuario(){
        val usuario = buscarUsuario()
        val nombreVista: TextView = findViewById(R.id.nombreUsuario)
        nombreVista.text = usuario?.nombre.toString()
    }
}