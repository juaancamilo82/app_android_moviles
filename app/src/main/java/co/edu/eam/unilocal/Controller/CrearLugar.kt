package co.edu.eam.unilocal.Controller

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import co.edu.eam.unilocal.Model.ArrayLugares
import co.edu.eam.unilocal.Model.ArrayUsuario
import co.edu.eam.unilocal.Model.Lugar
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.Model.Usuario


class CrearLugar : AppCompatActivity() {

    private lateinit var selectedImageUri: Uri
    private lateinit var selectedImageUri2: Uri
    private lateinit var selectedImageUri3: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_lugar)

        seleccionarImagen1()
        seleccionarImagen2()
        seleccionarImagen3()

        }
    fun seleccionarImagen1() {
        val btnSubirFoto = findViewById<ImageView>(R.id.btnSubFoto1)
        btnSubirFoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectImageLauncher.launch(intent)
        }
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                // guardar la URI de la imagen seleccionada en tu variable selectedImageUri
                selectedImageUri = imageUri
                // cargar la imagen desde la URI y establecerla en tu ImageView
                val imageView = findViewById<ImageView>(R.id.btnSubFoto1)
                imageView.setImageURI(imageUri)
            }
        }
    }

    fun seleccionarImagen2() {
        val btnSubirFoto = findViewById<ImageView>(R.id.btnSubFoto2)
        btnSubirFoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectImageLauncher2.launch(intent)
        }
    }

    private val selectImageLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                // guardar la URI de la imagen seleccionada en tu variable selectedImageUri
                selectedImageUri2 = imageUri
                // cargar la imagen desde la URI y establecerla en tu ImageView
                val imageView = findViewById<ImageView>(R.id.btnSubFoto2)
                imageView.setImageURI(imageUri)
            }
        }
    }

    fun seleccionarImagen3() {
        val btnSubirFoto = findViewById<ImageView>(R.id.btnSubFoto3)
        btnSubirFoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectImageLauncher3.launch(intent)
        }
    }

    private val selectImageLauncher3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                // guardar la URI de la imagen seleccionada en tu variable selectedImageUri
                selectedImageUri3 = imageUri
                // cargar la imagen desde la URI y establecerla en tu ImageView
                val imageView = findViewById<ImageView>(R.id.btnSubFoto3)
                imageView.setImageURI(imageUri)
            }
        }
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
         && numeroTelefono.text.toString().isNotEmpty()) {


         if(selectedImageUri!=null && selectedImageUri2!=null && selectedImageUri3!=null){

             val usuario = buscarUsuario()
             val correoUsuario = usuario?.email.toString()

             val image1 = selectedImageUri
             val image2 = selectedImageUri2
             val image3 = selectedImageUri3

             val listaImagenes = ArrayList(listOf(image1, image2, image3))
             val nuevoLugar = Lugar(
                 nombre.text.toString(), categoria.text.toString(),
                 direccion.text.toString(), referencia.text.toString(), horario.text.toString(),
                 telStr.toLong(), correoUsuario,
                 false,listaImagenes
             )
             lugares.add(nuevoLugar)
             editarLugaresRegistrados(nuevoLugar)
             Toast.makeText(this, "Lugar registrado con éxito", Toast.LENGTH_SHORT)
                 .show()
             abrirMain()
         }else{
             Toast.makeText(this, "Debes subir las fotos del lugar", Toast.LENGTH_SHORT)
                 .show()
         }

         }else{
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
         return if(usuario!=null){
             usuario
         }else{
             null
         }
     }
    private fun editarLugaresRegistrados(lugar: Lugar) {
        val usuario = buscarUsuario()
        usuario?.agregarLugarRegistrado(lugar)
    }
    private fun abrirMain(){
        val usuario = buscarUsuario()
        val intent = Intent(this, MainActivity::class.java)
        val correo = usuario?.email.toString()
        intent.putExtra("email", correo)
        startActivity(intent)
    }
}