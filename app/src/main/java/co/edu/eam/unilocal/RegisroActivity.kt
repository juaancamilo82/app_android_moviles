package co.edu.eam.unilocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class RegisroActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private val validationHandler = Handler(Looper.getMainLooper())
    private var validationRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regisro)
        editTextEmail = findViewById(R.id.emailLR)
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validationRunnable?.let { validationHandler.removeCallbacks(it) }
                validationRunnable = Runnable {
                    val email = s.toString()
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(this@RegisroActivity, "Email válido", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@RegisroActivity, "Email inválido", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                validationHandler.postDelayed(validationRunnable!!, 500)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        editTextPassword = findViewById(R.id.passR)
        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Cancelar el Runnable anterior si aún no se ha ejecutado
                validationRunnable?.let { validationHandler.removeCallbacks(it) }
                validationRunnable = Runnable {
                    val password = s.toString()
                    if (isPasswordValid(password)) {
                        Toast.makeText(
                            this@RegisroActivity,
                            "Contraseña válida",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegisroActivity,
                            "La contraseña debe tener al menos 8 caracteres",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                validationHandler.postDelayed(validationRunnable!!, 500)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        // Liberar el handler y el runnable para evitar fugas de memoria
        validationHandler.removeCallbacksAndMessages(null)
        validationRunnable = null
    }
    fun irAlLogin(v: View) { //Función para abrir otra actividad
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    fun registrarUsuario(v: View) {
        val usuarios = ArrayUsuario.getInstance().myArrayList
        val nombre: TextInputEditText = findViewById(R.id.nombres)
        val email: TextInputEditText = findViewById(R.id.emailLR)
        val password: TextInputEditText = findViewById(R.id.passR)
        val confirmP: TextInputEditText = findViewById(R.id.confirmPass)
        if (nombre.text.toString().isNotEmpty() && email.text.toString()
                .isNotEmpty() && password.text.toString().isNotEmpty() && confirmP.text.toString()
                .isNotEmpty()
        ) {
            if (password.text.toString().equals(confirmP.text.toString())) {
                if (isEmailValid(email.text.toString())) {
                    if (isPasswordValid(password.text.toString())) {
                        if(isEmpty(email.text.toString())){
                            val nuevoUsuario = Usuario(
                                nombre.text.toString(),
                                email.text.toString(),
                                password.text.toString(),
                                null
                            )
                            usuarios.add(nuevoUsuario)
                            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(
                                this@RegisroActivity,
                                "Ya existe un usuario registrado con el email ingresado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisroActivity,
                            "La contraseña debe tener más de 8 caracteres",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@RegisroActivity,
                        "Correo electrónico no válido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun isEmpty(email:String):Boolean{
        val listaUsuarios = ArrayUsuario.getInstance().myArrayList
        val usuario = listaUsuarios.find { Usuario ->
            Usuario.email.equals(email)
        }
        return usuario==null
    }
}