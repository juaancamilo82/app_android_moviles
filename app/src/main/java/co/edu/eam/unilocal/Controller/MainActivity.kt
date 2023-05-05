package co.edu.eam.unilocal.Controller

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.Model.*
import co.edu.eam.unilocal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.edu.eam.unilocal.View.RegistroLugar.RegistroLugarFragment
import co.edu.eam.unilocal.View.home.HomeFragment
import co.edu.eam.unilocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: String
    private lateinit var crudController: CrudController
    private lateinit var homeFragment: HomeFragment
    private lateinit var registroLugarFragment: RegistroLugarFragment
    private var posicionActual = 0
    lateinit var navegation: BottomNavigationView

    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        // Lógica para manejar los elementos seleccionados en el menú inferior
        when (item.itemId) {
            R.id.agregarLugarFrag -> {
                /* if (crudController.searchSesion(email) == null) {
                    irAlLogin()
                } else {*/

                val slideInAnim = if (isHomeFragmentVisible()) {
                    R.animator.slide_in
                } else {
                    R.animator.slide_in_reverse
                }
                val slideOutAnim = if (isHomeFragmentVisible()) {
                    R.animator.slide_out
                } else {
                    R.animator.slide_out_reverse
                }

                supportFragmentManager.commit {
                    setCustomAnimations(slideInAnim, slideOutAnim)
                    replace<RegistroLugarFragment>(R.id.frameContainer)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            //}

            R.id.HomeFrag -> {
                if (crudController.searchSesion(email) == null) {
                    val fragment = HomeFragment()

                    val slideInAnim = if (isHomeFragmentVisible()) {
                        R.animator.slide_in
                    } else {
                        R.animator.slide_in_reverse
                    }
                    val slideOutAnim = if (isHomeFragmentVisible()) {
                        R.animator.slide_out
                    } else {
                        R.animator.slide_out_reverse
                    }


                   supportFragmentManager.commit {
                        setCustomAnimations(slideInAnim, slideOutAnim)
                        replace(R.id.frameContainer, fragment)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }


                    return@OnNavigationItemSelectedListener true
                } else {
                    val fragment = HomeFragment()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    fragment.arguments = bundle

                    val slideInAnim = if (isHomeFragmentVisible()) {
                        R.animator.slide_in
                    } else {
                        R.animator.slide_in_reverse
                    }
                    val slideOutAnim = if (isHomeFragmentVisible()) {
                        R.animator.slide_out
                    } else {
                        R.animator.slide_out_reverse
                    }

                    supportFragmentManager.commit {
                        setCustomAnimations(slideInAnim, slideOutAnim)
                        replace(R.id.frameContainer, fragment)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }

                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        false
    }

    private fun isHomeFragmentVisible(): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frameContainer)
        return currentFragment is HomeFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = intent.getStringExtra("email").toString()
        crudController = CrudController()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.editarPerfil, R.id.lugaresRegistrados, R.id.cerrarSesion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navegation = findViewById(R.id.navMenuInf)
        navegation.setOnNavigationItemSelectedListener(mOnNavMenu)

       if (crudController.searchSesion(email) == null) {
            val fragment = HomeFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in_reverse,
                R.animator.slide_out_reverse)
            transaction.replace(R.id.frameContainer, fragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("replacement")
            transaction.commit()
        } else {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("email", email)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in_reverse,
                R.animator.slide_out_reverse)
            transaction.replace(R.id.frameContainer, fragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("replacement")
            transaction.commit()
        }


            homeFragment = HomeFragment()
            registroLugarFragment = RegistroLugarFragment()

            verificarSesion(navView.menu, navView)

        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main2_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun verificarSesion(menu: Menu, menuLateral: NavigationView) {

        val imagenPerfil = menuLateral.getHeaderView(0).findViewById<ImageView>(R.id.fotoPerfilIdLateral)
        val cerrarSesionItem = menu.findItem(R.id.cerrarSesion)
        val editOrRegister = menu.findItem(R.id.editarPerfilItem)
        val lugaresRegistrados = menu.findItem(R.id.lugaresRegistrados)
        val nombreUsuario =
            menuLateral.getHeaderView(0).findViewById<TextView>(R.id.txtNombreUsuario)
        val sesion = crudController.searchSesion(email)

        if (sesion != null) {

            if (sesion.usuario is Moderador) {

                lugaresRegistrados.setTitle("Lugares por autorizar")
                nombreUsuario.setText(sesion.usuario.nombre.toString())
                cerrarSesionItem.setOnMenuItemClickListener {
                    cerrarSesion()
                    true
                }
                lugaresRegistrados.setOnMenuItemClickListener {
                    val correoSesion = sesion?.usuario?.email
                    val intent = Intent(this, Lugares_registrados_activity::class.java)
                    intent.putExtra("email", correoSesion)
                    startActivity(intent)
                    true
                }

            } else {
                imagenPerfil.setImageURI(sesion.usuario.fotoPerfil)
                nombreUsuario.setText(sesion.usuario.nombre.toString())
                cerrarSesionItem.setOnMenuItemClickListener {
                    cerrarSesion()
                    true
                }
                lugaresRegistrados.setOnMenuItemClickListener {
                    val correoSesion = sesion.usuario.email
                    val intent = Intent(this, Lugares_registrados_activity::class.java)
                    intent.putExtra("email", correoSesion)
                    startActivity(intent)
                    true
                }
            }

        } else {
            lugaresRegistrados.setVisible(false)
            nombreUsuario?.setText("Inicia sesión para acceder a todas las funciones")
            editOrRegister?.setTitle("Iniciar sesión")
            editOrRegister?.setOnMenuItemClickListener {
                irAlLogin()
                true
            }
            editOrRegister?.setIcon(R.drawable.baseline_login_24)
            cerrarSesionItem?.setTitle("Registrarse")
            cerrarSesionItem.setOnMenuItemClickListener {
                irAlRegistro()
                true
            }
            cerrarSesionItem.setIcon(R.drawable.baseline_post_add_24)
        }
    }


    private fun cerrarSesion() {
        val sesion = crudController.searchSesion(email)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
        builder.setPositiveButton("Sí") { dialog, which ->
            ArraySesiones.getInstance().myArrayList.remove(sesion)
            reiniciarActivity()
        }
        builder.setNegativeButton("No") { dialog, which ->
        }
        builder.show()
    }

    private fun reiniciarActivity() {
        finish()
        startActivity(intent)
    }

    fun irAlRegistro() {
        val intent = Intent(this, RegisroActivity::class.java)
        startActivity(intent)
    }

    fun irAlLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}


