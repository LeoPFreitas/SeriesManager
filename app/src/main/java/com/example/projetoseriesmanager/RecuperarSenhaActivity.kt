package com.example.projetoseriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoseriesmanager.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    private val activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding by lazy {
        ActivityRecuperarSenhaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityRecuperarSenhaBinding.root)
        supportActionBar?.subtitle = "Recuperar senha"

        with(activityRecuperarSenhaBinding) {
            enviarEmailBt.setOnClickListener {
                val email = emailEt.text.toString()
                if (email.isNotEmpty()) {
                    AuthFirebase.firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@RecuperarSenhaActivity,
                                "E-mail de recuperação enviado",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@RecuperarSenhaActivity,
                                "Falha na restauração da senha",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    android.widget.Toast.makeText(
                        this@RecuperarSenhaActivity,
                        "Campo de envio não pode estar em branco",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}