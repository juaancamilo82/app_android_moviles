package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText


class RegisroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regisro)
    }

    fun irAlLogin(v: View) { //Función para abrir otra actividad
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    fun registrarUsuario(v: View){
        val usuarios = ArrayUsuario.getInstance().myArrayList
        val nombre: TextInputEditText = findViewById(R.id.nombres)
        val email: TextInputEditText = findViewById(R.id.emailLR)
        val password: TextInputEditText = findViewById(R.id.passR)
        val confirmP: TextInputEditText = findViewById(R.id.confirmPass)
        if(nombre.text.toString().isNotEmpty()&&email.text.toString().isNotEmpty()&&password.text.toString().isNotEmpty()&&confirmP.text.toString().isNotEmpty()){
            if(password.text.toString().equals(confirmP.text.toString())){
                val nuevoUsuario = Usuario(nombre.text.toString(), email.text.toString(), password.text.toString())
                usuarios.add(nuevoUsuario)
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}