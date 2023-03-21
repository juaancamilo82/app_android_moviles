package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class RegisroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regisro)
    }
    fun irAlLogin(v: View){ //Función para abrir otra actividad
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}