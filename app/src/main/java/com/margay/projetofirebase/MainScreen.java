package com.margay.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainScreen extends AppCompatActivity {

    private String id;
    private String email;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView textName;
    private TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

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
                this.textName.setText(value.getString("name"));
                this.textEmail.setText(email);
            }
        });
    }

    private void initComponents() {
        this.textName = findViewById(R.id.mainTextName);
        this.textEmail = findViewById(R.id.mainTextEmail);
    }
}