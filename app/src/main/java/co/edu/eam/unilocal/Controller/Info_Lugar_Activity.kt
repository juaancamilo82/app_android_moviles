package co.edu.eam.unilocal.Controller

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import co.edu.eam.unilocal.Model.ArrayLugaresAutorizados
import co.edu.eam.unilocal.Model.Lugar
import co.edu.eam.unilocal.Model.Usuario
import co.edu.eam.unilocal.R
class Info_Lugar_Activity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var nombreLugar: String
    private lateinit var crudController: CrudController
    private var posicionActual = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lugar)
        email = intent.getStringExtra("email").toString()
        nombreLugar = intent.getStringExtra("nombreLugar").toString()
        posicionActual = intent.getIntExtra("indice", 0)
        crudController = CrudController()
        cargarDatos()
    }

    private fun cargarDatos() {

        val lugar = crudController.searchAutoricedPlace(nombreLugar)
        val usuario = crudController.searchUser(email)
        val txtNombre = findViewById<TextView>(R.id.infoNombreLugar)
        val txtCategoria = findViewById<TextView>(R.id.infoCategoria)
        val txtHorario = findViewById<TextView>(R.id.infoHorario)
        val txtContacto = findViewById<TextView>(R.id.infoContacto)
        val txtDireccion = findViewById<TextView>(R.id.infoDirección)
        val img1 = findViewById<ImageView>(R.id.infoImg1)
        val img2 = findViewById<ImageView>(R.id.infoImg2)
        val img3 = findViewById<ImageView>(R.id.infoImg3)

        Toast.makeText(this, "Email: " + email, Toast.LENGTH_SHORT)
            .show()

        txtNombre.setText(lugar?.nombre)
        txtCategoria.setText(lugar?.categoria)
        txtHorario.setText(lugar?.horario)
        txtContacto.setText(lugar?.telefono.toString())
        txtDireccion.setText(lugar?.direccion)

        img1.setImageURI(lugar?.fotos?.get(0))
        img2.setImageURI(lugar?.fotos?.get(1))
        img3.setImageURI(lugar?.fotos?.get(2))

        activarIconos(lugar,usuario)

    }
    private fun activarIconos(lugar: Lugar?, usuario: Usuario?){

        val indiceFavoritos = usuario?.lugaresFavoritos?.indexOf(lugar)
        val indiceGuardados = usuario?.lugaresGuardados?.indexOf(lugar)

        val iconMeGusta = findViewById<ImageView>(R.id.iconMeGusta)
        val iconGuardar = findViewById<ImageView>(R.id.iconGuardar)
        val nombreLugar = lugar?.nombre.toString()

        val nombreLugarFavorito = if (indiceFavoritos != -1) {
            indiceFavoritos?.let { usuario.lugaresFavoritos?.get(it)?.nombre.toString() }
        } else {
            ""
        }
        val nombreLugarGuardado = if (indiceGuardados != -1) {
            indiceGuardados?.let { usuario.lugaresGuardados?.get(it)?.nombre.toString() }
        } else {
            ""
        }

        if (nombreLugar.equals(nombreLugarFavorito, ignoreCase = true)) {
            pintarIconoMeGusta(iconMeGusta)
        }

         if (nombreLugar.equals(nombreLugarGuardado, ignoreCase = true)) {
            pintarIconoGuardar(iconGuardar)
        }

        iconMeGusta.setOnClickListener {

            val currentColorFilter = iconMeGusta.colorFilter

            if(currentColorFilter == null){
                crudController.agregarLugarAfavoritos(lugar, usuario)
                pintarIconoMeGusta(iconMeGusta)
            }else{
                crudController.eliminarLugarFavorito(lugar, usuario)
                despintarIconoMeGusta(iconMeGusta)
            }
        }

        iconGuardar.setOnClickListener {

            val currentColorFilter = iconGuardar.colorFilter

            if(currentColorFilter == null){
                crudController.guardarLugar(lugar, usuario)
                pintarIconoGuardar(iconGuardar)
            }else{
                crudController.eliminarLugarGuardado(lugar, usuario)
                despintarIconoGuardar(iconGuardar)
            }
        }
    }
    private fun pintarIconoMeGusta(iconMeGusta: ImageView){
        iconMeGusta.setImageResource(R.drawable.baseline_favorite_24)
        iconMeGusta.setColorFilter(Color.RED)
    }
    private fun despintarIconoMeGusta(iconMeGusta: ImageView){
        iconMeGusta.setImageResource(R.drawable.baseline_favorite_border_24)
        iconMeGusta.clearColorFilter()
    }
    private fun pintarIconoGuardar(iconGuardar: ImageView){
        iconGuardar.setImageResource(R.drawable.baseline_bookmark_24)
        iconGuardar.setColorFilter(Color.GRAY)
    }
    private fun despintarIconoGuardar(iconGuardar: ImageView){
        iconGuardar.setImageResource(R.drawable.baseline_bookmark_border_24)
        iconGuardar.clearColorFilter()
    }
}