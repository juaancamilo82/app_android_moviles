package co.edu.eam.unilocal.View.home

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.media.Image
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.edu.eam.unilocal.Controller.CrudController
import co.edu.eam.unilocal.Controller.Info_Lugar_Activity
import co.edu.eam.unilocal.Model.ArrayLugares
import co.edu.eam.unilocal.Model.ArrayLugaresAutorizados
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentHomeBinding
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    private var posicionActual = 0
    private lateinit var crudController: CrudController
    private lateinit var email: String
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        crudController = CrudController()
        email = arguments?.getString("email").toString()

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        root = rootView

        cargarElementos()

        return rootView

    }
    private fun cargarElementos() {

        val listaLugares = ArrayLugaresAutorizados.getInstance().myArrayList
        val size = listaLugares.size

        if (size == 0) {
            Toast.makeText(requireContext(), size.toString(), Toast.LENGTH_SHORT)
                .show()
        } else {
            val linearLayout = root.findViewById<LinearLayout>(R.id.linearLayoutImg)
            val scrollView = root.findViewById<HorizontalScrollView>(R.id.scrollId)

            val dpValue = 300 // Valor en dp
            val scale = resources.displayMetrics.density
            val pixelValue = (dpValue * scale + 0.5f).toInt()

            val spacingDp = 20

            val scale1 = resources.displayMetrics.density
            val spacingPx = (spacingDp * scale1 + 0.5f).toInt()


            for (i in 0 until size) {
                val parentLayout = RelativeLayout(requireContext())
                val parentLayoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                parentLayout.layoutParams = parentLayoutParams

                val imageView = ImageView(requireContext())
                val layoutParams = RelativeLayout.LayoutParams(pixelValue, pixelValue)
                imageView.layoutParams = layoutParams
                layoutParams.setMargins(spacingPx, 0, 0, 0)
                imageView.setPadding(8, 8, 8, 8)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                val imageViewId = View.generateViewId()
                imageView.id = imageViewId

                val textView = TextView(requireContext())
                val textLayoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT) // Alineación a la izquierda
                textLayoutParams.addRule(RelativeLayout.BELOW, imageViewId) // Posicionar debajo de la imagen
                textLayoutParams.setMargins(spacingPx, 0, 0, 0) // Margen izquierdo igual al margen de la imagen

                // Establecer estilo en negrita y tamaño de fuente
                textView.setTypeface(null, Typeface.BOLD)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

                // Establecer texto para el TextView

                val nombreLugar = listaLugares.get(i).nombre
                textView.text = nombreLugar

                parentLayout.addView(imageView)
                parentLayout.addView(textView, textLayoutParams)

                linearLayout.addView(parentLayout)

                val byteArray = listaLugares[i].fotos[0]
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                imageView.setImageBitmap(bitmap)
                imageView.setOnClickListener {
                    abrirInfoLugar(nombreLugar)
                }

                scrollView.post {
                    scrollView.scrollTo(0, parentLayout.bottom)
                }
            }
        }
    }

    private fun abrirInfoLugar(nombre:String) {
        val intent = Intent(requireActivity(), Info_Lugar_Activity::class.java)
        intent.putExtra("nombreLugar", nombre)
        intent.putExtra("email", email)
        startActivity(intent)

    }

}

