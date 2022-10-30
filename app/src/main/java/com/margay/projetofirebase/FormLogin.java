package com.margay.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.common.base.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.margay.projetofirebase.enums.Msg;
import com.margay.projetofirebase.utils.Mensagem;

import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        this.initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Objects.nonNull(FirebaseAuth.getInstance().getCurrentUser())) {
            this.initApp();
        }
    }

    public void initComponents() {
        this.editTextLogin = findViewById(R.id.login);
        this.editTextPassword = findViewById(R.id.password);
        this.progressBar = findViewById(R.id.progressBar);

        this.findViewById(R.id.cmdLogin)
                .setOnClickListener(on -> {
                    String login = this.editTextLogin.getText().toString();
                    String password = this.editTextPassword.getText().toString();
                    if (Strings.isNullOrEmpty(login) || Strings.isNullOrEmpty(password)) {
                        Mensagem.messagem(on, Mensagem.getMessage(Msg.TODOS_CAMPOS));
                    } else {
                        auth(on, login, password);
                    }
                });

        this.findViewById(R.id.text_cadastro)
                .setOnClickListener(click -> {
                    this.startActivity(new Intent(this, FormCadastro.class));
                });
    }

    private void auth(View v, String login, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        this.progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(this::initApp, 3000);
                    } else {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (Exception e) {
                            Mensagem.messagem(v, e.getMessage());
                        }
                    }
                });
    }

    private void initApp() {
        this.startActivity(new Intent(this, MainScreen.class));
        this.finish();
    }
}