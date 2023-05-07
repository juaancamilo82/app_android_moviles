package co.edu.eam.unilocal.Controller

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.VectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.DrawableCompat
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R
class Info_Lugar_Activity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var nombreLugar: String
    private lateinit var crudController: CrudController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lugar)
        email = intent.getStringExtra("email").toString()
        nombreLugar = intent.getStringExtra("nombreLugar").toString()
        crudController = CrudController()
        cargarDatos()
    }

    private fun cargarDatos() {

        val lugar = crudController.searchLPlace(nombreLugar)
        val listaImagenes = lugar?.fotos
        val usuario = crudController.searchUser(email)

        val txtNombre = findViewById<TextView>(R.id.infoNombreLugar)
        val txtCategoria = findViewById<TextView>(R.id.infoCategoria)
        val txtHorario = findViewById<TextView>(R.id.infoHorario)
        val txtContacto = findViewById<TextView>(R.id.infoContacto)
        val txtDireccion = findViewById<TextView>(R.id.infoDirección)
        val txtReferencia = findViewById<TextView>(R.id.infoReferencia)

        txtNombre.setText(lugar?.nombre)
        txtCategoria.setText(lugar?.categoria)
        txtHorario.setText(lugar?.horario)
        txtContacto.setText(lugar?.telefono.toString())
        txtDireccion.setText(lugar?.direccion)
        txtReferencia.setText(lugar?.referencia)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutImgInfo)
        val scrollView = findViewById<HorizontalScrollView>(R.id.scrollIdInfo)

        if (listaImagenes != null) {
            if (usuario != null) {
                generarScroll(listaImagenes, linearLayout, scrollView, usuario, lugar)
            }
        }
        activarIconos(lugar,usuario)
    }
    private fun generarScroll(lista:List<ByteArray>,layout: LinearLayout, scrollView: HorizontalScrollView, usuario:Usuario, lugar: Lugar){

        val dpValue = 120 // Valor en dp
        val scale = resources.displayMetrics.density
        val pixelValue = (dpValue * scale + 0.5f).toInt()
        val spacingDp = 10
        val scale1 = resources.displayMetrics.density
        val spacingPx = (spacingDp * scale1 + 0.5f).toInt()

        for (i in 0 until lista.size) {
            val parentLayout = RelativeLayout(this)
            val parentLayoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            parentLayout.layoutParams = parentLayoutParams

            val imageView = ImageView(this)
            val layoutParams = RelativeLayout.LayoutParams(pixelValue, pixelValue)
            imageView.layoutParams = layoutParams
            layoutParams.setMargins(spacingPx, 0, 0, 0)
            imageView.setPadding(8, 8, 8, 8)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            val imageViewId = View.generateViewId()
            imageView.id = imageViewId

            parentLayout.addView(imageView)

            layout.addView(parentLayout)

            val byteArray = lista[i]
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            imageView.setImageBitmap(bitmap)

            scrollView.post {
                scrollView.scrollTo(0, parentLayout.bottom)
            }
        }
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