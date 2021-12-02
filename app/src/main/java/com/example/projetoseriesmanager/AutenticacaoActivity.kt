package com.example.projetoseriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoseriesmanager.databinding.ActivityAutenticacaoBinding

class AutenticacaoActivity : AppCompatActivity() {
    private val activityAutenticacaoBinding: ActivityAutenticacaoBinding by lazy {
        ActivityAutenticacaoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityAutenticacaoBinding.root)
        supportActionBar?.subtitle = "Autenticação"

        with(activityAutenticacaoBinding) {
            cadastrarUsuarioBt.setOnClickListener {
                startActivity(
                    Intent(
                        this@AutenticacaoActivity,
                        CadastrarUsuarioActivity::class.java
                    )
                )
            }

            recuperarSenhaBt.setOnClickListener {
                startActivity(Intent(this@AutenticacaoActivity, RecuperarSenhaActivity::class.java))
            }

            entrarBt.setOnClickListener {
                val email = emailEt.text.toString()
                val senha = senhaEt.text.toString()
                AuthFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@AutenticacaoActivity,
                            "Usuário autenticado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        iniciarMainSerieActivity()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@AutenticacaoActivity,
                            "Usuário/senha incorretos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AuthFirebase.firebaseAuth.currentUser != null) {
            iniciarMainSerieActivity()
        }
    }

    private fun iniciarMainSerieActivity() {
        startActivity(Intent(this@AutenticacaoActivity, SerieMainActivity::class.java))
        finish()
    }
}