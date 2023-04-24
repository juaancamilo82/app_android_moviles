package co.edu.eam.unilocal.Controller

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R

class Lugares_registrados_activity : AppCompatActivity() {

    private var posicionActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_registrados)
        cargarElementos(posicionActual)
    }

    private fun getSesion(): Sesion? {
        val emailSesion = intent.getStringExtra("email")
        val listaSesiones = ArraySesiones.getInstance().myArrayList
        val sesion = listaSesiones.find { Sesion ->
            Sesion.usuario.email.equals(emailSesion)
        }
        return sesion
    }

    private fun cargarElementos(indice: Int){

        val sesion = getSesion()
        val btnAutorizar = findViewById<ImageView>(R.id.btnAutorizar)
        val btnDenegar = findViewById<ImageView>(R.id.btnDenegar)
        val btnEliminar = findViewById<ImageView>(R.id.btnBorrarLugar)
        val size:Int?
        val btnSiguiente = findViewById<ImageView>(R.id.btnSiguiente)
        val btnAnterior = findViewById<ImageView>(R.id.btnAnterior)
        val nombreLugarTxt = findViewById<TextView>(R.id.nombreLugartxt)
        val nombreUsuarioTxt = findViewById<TextView>(R.id.nombreUsuariotxt)
        val imgUsuario = findViewById<ImageView>(R.id.imgUsuario)
        val img1 = findViewById<ImageView>(R.id.placeImg1)
        val img2 = findViewById<ImageView>(R.id.placeImg2)
        val img3 = findViewById<ImageView>(R.id.placeImg3)

        if(sesion?.usuario is Moderador){

            btnEliminar.setVisibility(View.GONE)
            btnAutorizar.setVisibility(View.VISIBLE)
            btnDenegar.setVisibility(View.VISIBLE)

            val listaLugares = ArrayLugares.getInstance().myArrayList
            size = listaLugares.size

                if(size==0){
                    img1.setVisibility(View.GONE)
                    img2.setVisibility(View.GONE)
                    img3.setVisibility(View.GONE)
                    btnSiguiente.setVisibility(View.GONE)
                    nombreLugarTxt.setVisibility(View.GONE)
                    btnAnterior.setVisibility(View.GONE)
                    btnAutorizar.setVisibility(View.GONE)
                    btnDenegar.setVisibility(View.GONE)
                }else{
                    val imgUri1 = listaLugares.get(indice)?.fotos?.get(0)
                    val imgUri2 = listaLugares.get(indice)?.fotos?.get(1)
                    val imgUri3 = listaLugares.get(indice)?.fotos?.get(2)
                    val nombreLugar = listaLugares.get(indice)?.nombre

                    img1.setImageURI(imgUri1)
                    img2.setImageURI(imgUri2)
                    img3.setImageURI(imgUri3)
                    nombreUsuarioTxt.setText(sesion?.usuario?.nombre)
                    nombreLugarTxt.setText(nombreLugar.toString())
                }
        }else{
            size=sesion?.usuario?.lugaresRegistrados?.size
            if(size==null){
                img1.setVisibility(View.GONE)
                img2.setVisibility(View.GONE)
                img3.setVisibility(View.GONE)
                btnSiguiente.setVisibility(View.GONE)
                nombreLugarTxt.setVisibility(View.GONE)
                btnAnterior.setVisibility(View.GONE)
                btnAutorizar.setVisibility(View.GONE)
                btnDenegar.setVisibility(View.GONE)
            }else{
                val imgUri1 = sesion?.usuario?.lugaresRegistrados?.get(indice)?.fotos?.get(0)
                val imgUri2 = sesion?.usuario?.lugaresRegistrados?.get(indice)?.fotos?.get(1)
                val imgUri3 = sesion?.usuario?.lugaresRegistrados?.get(indice)?.fotos?.get(2)
                val nombreLugar = sesion?.usuario?.lugaresRegistrados?.get(indice)?.nombre

                img1.setImageURI(imgUri1)
                img2.setImageURI(imgUri2)
                img3.setImageURI(imgUri3)
                nombreUsuarioTxt.setText(sesion?.usuario?.nombre)
                nombreLugarTxt.setText(nombreLugar.toString())

                btnAutorizar.setVisibility(View.GONE)
                btnDenegar.setVisibility(View.GONE)
            }
        }
    }

    fun lugarSiguiente(V: View) {

        val sesion = getSesion()
        val size: Int?

        if(sesion?.usuario is Moderador){
            val listaLugares = ArrayLugares.getInstance().myArrayList
             size = listaLugares.size
        }else{
            val lugaresRegistrados = sesion?.usuario?.lugaresRegistrados
             size = lugaresRegistrados?.size
        }

        if (size != null && size > 1) {
            posicionActual = (posicionActual + 1) % size
            cargarElementos(posicionActual)
        }
    }

    fun lugarAnterior(V: View) {
        val sesion = getSesion()
        val size: Int?

        if(sesion?.usuario is Moderador){
            val listaLugares = ArrayLugares.getInstance().myArrayList
            size = listaLugares.size
        }else{
            val lugaresRegistrados = sesion?.usuario?.lugaresRegistrados
            size = lugaresRegistrados?.size
        }
        if (size != null && size > 1) {
            posicionActual = (posicionActual - 1 + size) % size
            cargarElementos(posicionActual)
        }
    }
    private fun getLugar(): Lugar? {
            val nombre = findViewById<TextView>(R.id.nombreLugartxt)
            val listaLugares = ArrayLugares.getInstance().myArrayList
            val lugar = listaLugares.find { Lugar ->
                Lugar.nombre.equals(nombre)
            }
            return if(lugar!=null){
                return lugar
            }else{
                null
            }
    }

    private fun getUsuario(): Usuario? {
        val email = intent.getStringExtra("email")
        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email)
        }
        if (usuario == null || email == null) {
            return null
        } else {
            return usuario
        }
    }
     fun eliminarLugar(V: View) {

        var lugar = getLugar()
        var usuario = getUsuario()
        val indice = usuario?.lugaresRegistrados?.indexOf(lugar)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
        builder.setPositiveButton("Sí") { dialog, which ->
            if (indice != -1) {
                if (indice != null) {
                    usuario?.lugaresRegistrados?.removeAt(indice)
                }
                ArrayLugares.getInstance().myArrayList.remove(lugar)
            }

        }
        builder.setNegativeButton("No") { dialog, which ->
        }
        builder.show()
    }

     fun autorizarLugar(V: View){
        val indice = posicionActual
         val listaLugares = ArrayLugares.getInstance().myArrayList
         val lugaresAutorizados = ArrayLugaresAutorizados.getInstance().myArrayList

         lugaresAutorizados.add(listaLugares.get(indice))
         listaLugares.removeAt(indice)

         Toast.makeText(this, "Lugar autorizado con éxito", Toast.LENGTH_SHORT).show()

    }
    fun denegarLugar(V: View){
        Toast.makeText(this, "Lugar denegado con éxito", Toast.LENGTH_SHORT).show()
    }

    private fun abrirMain(){
        val sesion = getSesion()?.usuario
        val intent = Intent(this, MainActivity::class.java)
        val correo = sesion?.email
        intent.putExtra("email", correo)
        startActivity(intent)
    }

}





