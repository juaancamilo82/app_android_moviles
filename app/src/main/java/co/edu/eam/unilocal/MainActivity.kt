package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun buscarUsuario(): Usuario? {
        val email = intent.getStringExtra("email")
        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email)
        }
        if(usuario==null || email==null){
            return null
        }else{
            return usuario
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
        //val nombreVista: TextView = findViewById(R.id.nombreUsuario)
        //nombreVista.text = usuario?.nombre.toString()
    }

    fun cargarDatosLugares(){
        val lugar = buscarLugar()
        // val nombreVista: TextView = findViewById(R.id.nombreLugarText)
        //nombreVista.text = lugar?.nombre.toString()
    }
    fun abrirVentanaRegistroLugar(v: View){
        val usuario = buscarUsuario()
        if(usuario!=null){
            val intent = Intent(this, CrearLugar::class.java)
            val correo = usuario?.email.toString()
            if(usuario!=null){
                intent.putExtra("email", correo)
                startActivity(intent)
            }
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}