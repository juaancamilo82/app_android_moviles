package co.edu.eam.unilocal.Controller

import android.content.Intent
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
import co.edu.eam.unilocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.editarPerfil, R.id.lugaresRegistrados, R.id.cerrarSesion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        verificarSesion(navView.menu, navView)

        val size = getSesion()?.usuario?.lugaresRegistrados?.size
        Toast.makeText(this, size.toString(), Toast.LENGTH_SHORT)
            .show()

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

        val cerrarSesionItem = menu.findItem(R.id.cerrarSesion)
        val LogOrHome = menu.findItem(R.id.nav_home)
        val editOrRegister = menu.findItem(R.id.editarPerfil)
        val lugaresRegistrados = menu.findItem(R.id.lugaresRegistrados)
        val nombreUsuario =
            menuLateral.getHeaderView(0).findViewById<TextView>(R.id.txtNombreUsuario)
        val sesion = getSesion()

        if (sesion != null) {
            nombreUsuario.setText(sesion.usuario.nombre.toString())
            cerrarSesionItem.setOnMenuItemClickListener {
                cerrarSesion()
                true
            }
            lugaresRegistrados.setOnMenuItemClickListener {
                val correoSesion = getSesion()?.usuario?.email
                val intent = Intent(this, Lugares_registrados_activity::class.java)
                intent.putExtra("email", correoSesion)
                startActivity(intent)
                true
            }

        } else {
            lugaresRegistrados.setVisible(false)
            nombreUsuario?.setText("Inicia sesión para acceder a todas las funciones")
            LogOrHome?.setTitle("Iniciar sesión")
            LogOrHome?.setOnMenuItemClickListener {
                irAlLogin()
                true
            }
            LogOrHome?.setIcon(R.drawable.baseline_login_24)
            cerrarSesionItem?.setVisible(false)
            editOrRegister?.setTitle("Registrarse")
            editOrRegister.setOnMenuItemClickListener {
                irAlRegistro()
                true
            }
            editOrRegister.setIcon(R.drawable.baseline_post_add_24)
        }
    }

    private fun cerrarSesion() {
        var sesion = getSesion()
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


    private fun getSesion(): Sesion? {
        val emailSesion = intent.getStringExtra("email")
        val listaSesiones = ArraySesiones.getInstance().myArrayList
        val sesion = listaSesiones.find { Sesion ->
            Sesion.usuario.email.equals(emailSesion)
        }
        return sesion
    }
    private fun reiniciarActivity() {
        finish()
        startActivity(intent)
    }


    private fun buscarLugar(): Lugar? {
        val nombreLugar = intent.getStringExtra("nombreLugar")
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val lugar = listaLugares.find { Lugar ->
            Lugar.nombre.equals(nombreLugar)
        }
        return lugar
    }

    fun irAlRegistro() {
        val intent = Intent(this, RegisroActivity::class.java)
        startActivity(intent)
    }

    fun irAlLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun abrirVentanaRegistroLugar(v: View) {
        val sesion = getSesion()
        if (sesion != null) {
            val intent = Intent(this, CrearLugar::class.java)
            val correo: String = sesion.usuario.email.toString()
            intent.putExtra("email", correo)
            startActivity(intent)
        } else {
            irAlLogin()
        }
    }
}


