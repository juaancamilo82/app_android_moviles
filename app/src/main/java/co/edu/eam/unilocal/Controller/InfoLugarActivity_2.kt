package co.edu.eam.unilocal.Controller

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R

private lateinit var nombreLugar: String
private lateinit var crudController: CrudController
private lateinit var email: String

class infoLugarActivity_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lugar2)
        nombreLugar = intent.getStringExtra("nombreLugar").toString()
        email = intent.getStringExtra("email").toString()
        crudController = CrudController()
        cargarDatos()
    }

    private fun cargarDatos() {

        val lugar = crudController.searchLPlace(nombreLugar)

        val listaImagenes = lugar?.fotos
        val usuario = crudController.searchUser(email) as Moderador

        val txtNombre = findViewById<TextView>(R.id.infoNombreLugar1)
        val txtCategoria = findViewById<TextView>(R.id.infoCategoria1)
        val txtHorario = findViewById<TextView>(R.id.infoHorario1)
        val txtContacto = findViewById<TextView>(R.id.infoContacto1)
        val txtDireccion = findViewById<TextView>(R.id.infoDirección1)
        val txtReferencia = findViewById<TextView>(R.id.infoReferencia1)

        txtNombre.setText(lugar?.nombre)
        txtCategoria.setText(lugar?.categoria)
        txtHorario.setText(lugar?.horario)
        txtContacto.setText(lugar?.telefono.toString())
        txtDireccion.setText(lugar?.direccion)
        txtReferencia.setText(lugar?.referencia)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutImgInfo1)
        val scrollView = findViewById<HorizontalScrollView>(R.id.scrollIdInfo1)

        if (listaImagenes != null) {
                generarScroll(listaImagenes, linearLayout, scrollView, lugar, usuario)
        }
    }
    private fun generarScroll(lista:List<ByteArray>, layout: LinearLayout, scrollView: HorizontalScrollView,  lugar: Lugar, usuario: Moderador){

        val btnAutorizar = findViewById<Button>(R.id.btnAutorizarLugar)
        val btnRechazar = findViewById<Button>(R.id.btnRechazarLugar)

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

            if(lugar.estaAutorizado==false){

                btnAutorizar.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmación")
                    builder.setMessage("¿Estás seguro de que deseas aprobar este lugar? no hay vuelta atrás")
                    builder.setPositiveButton("Sí") { dialog, which ->
                        autorizarLugar(lugar, usuario)
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                    }
                    builder.show()
                }

                btnRechazar.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmación")
                    builder.setMessage("¿Estás seguro de que deseas rechazar este lugar? no hay vuelta atrás")
                    builder.setPositiveButton("Sí") { dialog, which ->
                        rechazarLugar(lugar, usuario)
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                    }
                    builder.show()
                }

            }else{
                btnRechazar.visibility=GONE
                btnAutorizar.visibility=GONE
            }
    }

    private fun autorizarLugar(lugar: Lugar, moderador: Moderador){
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val indice = listaLugares.indexOf(lugar)
        listaLugares.get(indice).estaAutorizado=true
        moderador.agregarLugarAListaAprobados(lugar)
        Toast.makeText(this, "Lugar autorizado con éxito", Toast.LENGTH_SHORT).show()
    }
    private fun rechazarLugar(lugar: Lugar, moderador: Moderador){
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val indice = listaLugares.indexOf(lugar)
        listaLugares.get(indice).estaAutorizado=null
        moderador.agregarLugarAListaReprobados(lugar)
        Toast.makeText(this, "Lugar rechazado con éxito", Toast.LENGTH_SHORT).show()
    }
}