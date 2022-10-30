package com.margay.projetofirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class TelaPrincipal extends AppCompatActivity {

    private String id;
    private String email;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView editTextName;
    private TextView editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        this.initComponents();

        Objects.requireNonNull(this.getSupportActionBar()).hide();

        findViewById(R.id.cmdLogoff).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            this.startActivity(new Intent(this, FormLogin.class));
            this.finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            id = currentUser.getUid();
            email = currentUser.getEmail();
        }

        DocumentReference reference = db.collection("users").document(id);
        reference.addSnapshotListener((value, error) -> {
            if (value != null) {
                this.editTextName.setText(value.getString("name"));
                this.editTextEmail.setText(email);
            }
        });
    }

    private void initComponents() {
        this.editTextName = findViewById(R.id.mainTextName);
        this.editTextEmail = findViewById(R.id.mainTextEmail);
    }
}