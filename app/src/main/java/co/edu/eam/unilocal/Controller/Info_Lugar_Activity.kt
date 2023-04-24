package co.edu.eam.unilocal.Controller

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import co.edu.eam.unilocal.Model.ArrayLugaresAutorizados
import co.edu.eam.unilocal.Model.ArrayUsuario
import co.edu.eam.unilocal.Model.Lugar
import co.edu.eam.unilocal.Model.Usuario
import co.edu.eam.unilocal.R

class Info_Lugar_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lugar)
        cargarDatos()
    }

    private fun getLugar(): Lugar?{
        val nombre = intent.getStringExtra("nombreLugar")
        val listaLugares = ArrayLugaresAutorizados.getInstance().myArrayList
        val lugar = listaLugares.find { LugaresAutorizados ->
            LugaresAutorizados.nombre.equals(nombre)
        }
        return if(lugar!=null){
            lugar
        }else{
            null
        }
    }



    private fun cargarDatos(){

        val lugar = getLugar()
        val usuario = buscarUsuario()
        val listaLugares = ArrayLugaresAutorizados.getInstance().myArrayList
        val txtNombre = findViewById<TextView>(R.id.infoNombreLugar)
        val txtCategoria = findViewById<TextView>(R.id.infoCategoria)
        val txtHorario = findViewById<TextView>(R.id.infoHorario)
        val txtContacto = findViewById<TextView>(R.id.infoContacto)
        val txtDireccion = findViewById<TextView>(R.id.infoDirección)
        val img1 = findViewById<ImageView>(R.id.infoImg1)
        val img2 = findViewById<ImageView>(R.id.infoImg2)
        val img3 = findViewById<ImageView>(R.id.infoImg3)
        val iconMeGusta = findViewById<ImageView>(R.id.iconMeGusta)
        val iconGuardar = findViewById<ImageView>(R.id.iconGuardar)

        txtNombre.setText(lugar?.nombre)
        txtCategoria.setText(lugar?.categoria)
        txtHorario.setText(lugar?.horario)
        txtContacto.setText(lugar?.telefono.toString())
        txtDireccion.setText(lugar?.direccion)

        img1.setImageURI(lugar?.fotos?.get(0))
        img2.setImageURI(lugar?.fotos?.get(1))
        img3.setImageURI(lugar?.fotos?.get(2))

            iconMeGusta.setOnClickListener {

                if  (iconMeGusta.drawable is VectorDrawable)  {
                    iconMeGusta.setOnClickListener {

                        iconMeGusta.drawable.setTint(Color.RED)

                        //iconMeGusta.setImageResource(R.drawable.baseline_favorite_24)
                        iconMeGusta.invalidate()

                        if (lugar != null) {
                            agregarAfavoritos(lugar)
                        }
                    }
                }else{
                    if (lugar != null) {
                        eliminarDeFavoritos(lugar)
                    }
                    iconMeGusta.setImageResource(R.drawable.baseline_favorite_border_24)
                    iconMeGusta.invalidate()
                }
            }
    }

    private fun buscarUsuario(): Usuario? {
        val email = intent.getStringExtra("email")
        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email)
        }
        return if(usuario!=null){
            usuario
        }else{
            null
        }
    }

    private fun agregarAfavoritos(lugar:Lugar){
        val usuario = buscarUsuario()
        usuario?.agregarLugarFavorito(lugar)
    }

    private fun eliminarDeFavoritos(lugar:Lugar){
        val usuario = buscarUsuario()
        val lugar = getLugar()
        usuario?.lugaresFavoritos?.remove(lugar)


    }
}