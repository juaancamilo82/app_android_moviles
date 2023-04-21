package co.edu.eam.unilocal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.databinding.ActivityMainBinding
import org.w3c.dom.Text

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
                R.id.nav_home, R.id.editarPerfil, R.id.cerrarSesion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
    fun verificarSesion(menu:Menu, menuLateral:NavigationView){

        val cerrarSesionItem = menu.findItem(R.id.cerrarSesion)
        val LogOrHome =  menu.findItem(R.id.nav_home)
        val editOrRegister = menu.findItem(R.id.editarPerfil)
        val nombreUsuario = menuLateral.getHeaderView(0).findViewById<TextView>(R.id.txtNombreUsuario)
        val usuario = buscarUsuario()

        if(usuario!=null){
            nombreUsuario.setText(usuario.nombre.toString())
            LogOrHome?.setTitle("Home")
            editOrRegister?.setTitle("Editar perfil")

        }else{
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


    private fun buscarUsuario(): Usuario? {
        val email = intent.getStringExtra("email")
        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email)
        }
        if (usuario == null || email == null) {
            return null
        } else {
            return usuario
        }
    }

    private fun buscarLugar(): Lugar? {
        val nombreLugar = intent.getStringExtra("nombreLugar")
        val listaLugares = ArrayLugares.getInstance().myArrayList
        val lugar = listaLugares.find { Lugar ->
            Lugar.nombre.equals(nombreLugar)
        }
        return lugar
    }

    private fun cargarDatosUsuario() {
        val usuario = buscarUsuario()
        //val nombreVista: TextView = findViewById(R.id.nombreUsuario)
        //nombreVista.text = usuario?.nombre.toString()
    }

    fun cargarDatosLugares() {
        val lugar = buscarLugar()
        // val nombreVista: TextView = findViewById(R.id.nombreLugarText)
        //nombreVista.text = lugar?.nombre.toString()
    }

    fun irAlRegistro() {
        val intent = Intent(this, RegisroActivity::class.java)
        startActivity(intent)
    }

    fun irAlLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    fun abrirVentanaRegistroLugar(v: View) {
        val usuario = buscarUsuario()
        if (usuario != null) {
            val intent = Intent(this, CrearLugar::class.java)
            val correo = usuario?.email.toString()
            if (usuario != null) {
                intent.putExtra("email", correo)
                startActivity(intent)
            }
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}