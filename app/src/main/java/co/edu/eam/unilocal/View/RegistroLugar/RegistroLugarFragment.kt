package co.edu.eam.unilocal.View.RegistroLugar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.ARG_PARAM1
import co.edu.eam.unilocal.ARG_PARAM2
import co.edu.eam.unilocal.Controller.CrudController
import co.edu.eam.unilocal.Controller.MainActivity
import co.edu.eam.unilocal.Model.ArrayLugares
import co.edu.eam.unilocal.Model.ArrayLugaresAutorizados
import co.edu.eam.unilocal.Model.Lugar
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.View.home.HomeFragment


class RegistroLugarFragment : Fragment() {

    private lateinit var selectedImageUri: Uri
    private lateinit var selectedImageUri2: Uri
    private  lateinit var selectedImageUri3: Uri
    private lateinit var email: String
    private lateinit var crudController: CrudController
    private lateinit var root: View

    companion object {
        fun newInstance(email: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("email", email)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        email =  arguments?.getString("email").toString()
        crudController = CrudController()

        val rootView = inflater.inflate(R.layout.fragment_registro_lugar, container, false)

        root=rootView

        registrarLugar()

        seleccionarImagen1()
        seleccionarImagen2()
        seleccionarImagen3()

        return rootView
    }

    fun seleccionarImagen1() {
        val btnSubirFoto = root.findViewById<ImageView>(R.id.btnSubFoto1)
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
                val imageView = root.findViewById<ImageView>(R.id.btnSubFoto1)
                imageView.setImageURI(imageUri)
            }
        }
    }

    fun seleccionarImagen2() {
        val btnSubirFoto = root.findViewById<ImageView>(R.id.btnSubFoto2)
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
                val imageView =root. findViewById<ImageView>(R.id.btnSubFoto2)
                imageView.setImageURI(imageUri)
            }
        }
    }
    fun seleccionarImagen3() {
        val btnSubirFoto = root.findViewById<ImageView>(R.id.btnSubFoto3)
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
                val imageView = root.findViewById<ImageView>(R.id.btnSubFoto3)
                imageView.setImageURI(imageUri)
            }
        }
    }

    fun registrarLugar() {

        val btnRegistrar = root.findViewById<Button>(R.id.btnRegistrarLugar)

        btnRegistrar.setOnClickListener {

            val lugares = ArrayLugares.getInstance().myArrayList

            val nombre = root.findViewById<EditText>(R.id.nombreLugar).text.toString()
            val categoria = root.findViewById<EditText>(R.id.categoriaLugar).text.toString()
            val direccion = root.findViewById<EditText>(R.id.direccionLugar).text.toString()
            val referencia = root.findViewById<EditText>(R.id.referenciaLugar).text.toString()
            val horario = root.findViewById<EditText>(R.id.horarioLugar).text.toString()
            val numeroTelefono = root.findViewById<EditText>(R.id.numeroTelefonoLugar).text.toString()

            if (nombre.isNotEmpty() && categoria.isNotEmpty() && direccion.isNotEmpty() && referencia.isNotEmpty()
                && horario.isNotEmpty() && numeroTelefono.isNotEmpty()) {

                if (estaDisponible(nombre)) {

                    val image1 = selectedImageUri
                    val image2 = selectedImageUri2
                    val image3 = selectedImageUri3

                    val listaImagenes = ArrayList(listOf(image1, image2, image3))

                    val nuevoLugar = Lugar(
                        nombre, categoria, direccion, referencia, horario, numeroTelefono,
                        email, listaImagenes
                    )

                    lugares.add(nuevoLugar)

                    val user = crudController.searchUser(email)
                    user?.agregarLugarRegistrado(nuevoLugar)

                    Toast.makeText(requireContext(), "Lugar registrado con éxito", Toast.LENGTH_SHORT)
                        .show()

                    limpiarCampos()

                } else {
                    Toast.makeText(requireContext(), "El lugar ya se encuentra registrado", Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(requireContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun estaDisponible(nombre: String):Boolean{
        val lugarRegistrado = crudController.searchLPlace(nombre)
        val lugarAutorizado = crudController.searchAutoricedPlace(nombre)
        return lugarRegistrado==null && lugarAutorizado==null
    }

    private fun limpiarCampos() {
        val nombreLugar = root.findViewById<EditText>(R.id.nombreLugar)
        val categoriaLugar = root.findViewById<EditText>(R.id.categoriaLugar)
        val direccionLugar = root.findViewById<EditText>(R.id.direccionLugar)
        val referenciaLugar = root.findViewById<EditText>(R.id.referenciaLugar)
        val horarioLugar = root.findViewById<EditText>(R.id.horarioLugar)
        val numeroTelefonoLugar = root.findViewById<EditText>(R.id.numeroTelefonoLugar)

        nombreLugar.setText("")
        categoriaLugar.setText("")
        direccionLugar.setText("")
        referenciaLugar.setText("")
        horarioLugar.setText("")
        numeroTelefonoLugar.setText("")
    }
}