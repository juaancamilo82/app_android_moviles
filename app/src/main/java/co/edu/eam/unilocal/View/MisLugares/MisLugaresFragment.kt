package co.edu.eam.unilocal.View.MisLugares

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.edu.eam.unilocal.Controller.CrudController
import co.edu.eam.unilocal.Controller.Info_Lugar_Activity
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.View.RegistroLugar.RegistroLugarFragment
import co.edu.eam.unilocal.View.home.HomeFragment


class MisLugaresFragment : Fragment() {

    private lateinit var root: View
    private lateinit var email: String

    companion object {
        fun newInstance(email: String): RegistroLugarFragment {
            val fragment = RegistroLugarFragment()
            val args = Bundle()
            args.putString("email", email)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var crudController: CrudController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = arguments?.getString("email").toString()
        val rootView = inflater.inflate(R.layout.fragment_mis_lugares, container, false)
        root = rootView
        crudController = CrudController()
        cargarDatos(email)
        return root
    }

    private fun cargarDatos(email: String) {

        val usuario = crudController.searchUser(email)

        val linearLayoutLugaresRegistrados = root.findViewById<LinearLayout>(R.id.linearLayoutLugaresRegistrados)
        val linearLayoutLugaresFavoritos = root.findViewById<LinearLayout>(R.id.linearLayoutLugaresFavoritos)
        val linearLayoutLugaresGuardados = root.findViewById<LinearLayout>(R.id.linearLayoutLugaresGuardados)

        val scrollRegistrados = root.findViewById<HorizontalScrollView>(R.id.scrollLugaresRegistrados)
        val scrollFavoritoss = root.findViewById<HorizontalScrollView>(R.id.scrollLugaresFavoritos)
        val scrollGuardados = root.findViewById<HorizontalScrollView>(R.id.scrollLugaresGuardados)

        val textView1 = root.findViewById<TextView>(R.id.textViewLugaresRegistrados)
        val textView2 = root.findViewById<TextView>(R.id.textViewLugaresFavoritos)
        val textView3 = root.findViewById<TextView>(R.id.textViewLugaresGuardados)

        if (usuario is Moderador) {

            val listaLugaresPendientes = ArrayLugares.getInstance().myArrayList
            val listaAprobados = usuario.lugaresAprobados
            val listaReprobados = usuario.lugaresReprobados

            textView1.setText("Lugares pendientes por autorizar")
            textView2.setText("Lugares autorizados por ti")
            textView3.setText("Lugares reprobados por ti")

            generarScroll(listaLugaresPendientes, linearLayoutLugaresRegistrados, scrollRegistrados)

            if (listaAprobados != null) {
                generarScroll(listaAprobados, linearLayoutLugaresFavoritos, scrollFavoritoss)
            }
            if (listaReprobados != null) {
                generarScroll(listaReprobados, linearLayoutLugaresGuardados, scrollGuardados)
            }

        } else {

            val lugaresRegistrados = usuario?.lugaresRegistrados
            val lugaresFavoritos = usuario?.lugaresFavoritos
            val lugaresGuardados = usuario?.lugaresGuardados

            if (lugaresRegistrados != null) {
                generarScroll(lugaresRegistrados, linearLayoutLugaresRegistrados, scrollRegistrados)
            }
            if (lugaresFavoritos != null) {
                generarScroll(lugaresFavoritos, linearLayoutLugaresFavoritos, scrollFavoritoss)
            }
            if (lugaresGuardados != null) {
                generarScroll(lugaresGuardados, linearLayoutLugaresGuardados, scrollGuardados)
            }
        }
    }

    private fun generarScroll(lista: ArrayList<Lugar>, layout: LinearLayout, scrollView: HorizontalScrollView){

        val dpValue = 150 // Valor en dp
        val scale = resources.displayMetrics.density
        val pixelValue = (dpValue * scale + 0.5f).toInt()

        val spacingDp = 20

        val scale1 = resources.displayMetrics.density
        val spacingPx = (spacingDp * scale1 + 0.5f).toInt()

            for (i in 0 until lista.size) {
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
                textLayoutParams.addRule(
                    RelativeLayout.BELOW,
                    imageViewId
                ) // Posicionar debajo de la imagen
                textLayoutParams.setMargins(
                    spacingPx,
                    0,
                    0,
                    0
                ) // Margen izquierdo igual al margen de la imagen

                // Establecer estilo en negrita y tamaño de fuente
                textView.setTypeface(null, Typeface.NORMAL)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                // Establecer texto para el TextView

                val nombreLugar = lista.get(i).nombre
                textView.text = nombreLugar

                parentLayout.addView(imageView)
                parentLayout.addView(textView, textLayoutParams)

                layout.addView(parentLayout)

                val byteArray = lista[i].fotos[0]
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

    private fun abrirInfoLugar(nombre:String) {
        val intent = Intent(requireActivity(), Info_Lugar_Activity::class.java)
        intent.putExtra("nombreLugar", nombre)
        intent.putExtra("email", email)
        startActivity(intent)
    }

}