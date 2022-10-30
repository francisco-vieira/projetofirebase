package com.margay.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.common.base.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.margay.projetofirebase.enums.Msg;
import com.margay.projetofirebase.utils.Mensagem;

import java.util.HashMap;
import java.util.Objects;

public class FormCadastro extends AppCompatActivity {

    private String id;
    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        Objects.requireNonNull(this.getSupportActionBar()).hide();
        this.initComponents();

        this.findViewById(R.id.cmdSave).setOnClickListener(view -> {
            String nome = editName.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editPassword.getText().toString();
            if (Strings.isNullOrEmpty(nome) || Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(senha)) {

                Mensagem.messagem(view, Mensagem.getMessage(Msg.TODOS_CAMPOS));
            } else {
                this.userRegister(view, nome, email, senha);
            }

        });
    }


    private void initComponents() {
        this.editName = findViewById(R.id.edit_nome);
        this.editEmail = findViewById(R.id.edit_email);
        this.editPassword = findViewById(R.id.edit_password);
    }

    private void insertData(String name) {
        id = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fb.collection("users").document(id);
        documentReference.set(new HashMap<String, Object>() {{
            put("name", name);
        }}).addOnSuccessListener(task -> {
            Log.d("db", Mensagem.getMessage(Msg.CADASTRO_SUCESSO));

        }).addOnFailureListener(fail -> {
            Log.d("db_error", Mensagem.getMessage(Msg.CADASTRO_ERRO).concat(" :").concat(Objects.requireNonNull(fail.getMessage())));
        });
    }

    private void userRegister(View view, String name, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Mensagem.messagem(view, Mensagem.getMessage(Msg.CADASTRO_SUCESSO));
                this.insertData(name);
            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (Exception e) {
                    Mensagem.messagem(view, e.getMessage());
                }
            }
        });
    }

}