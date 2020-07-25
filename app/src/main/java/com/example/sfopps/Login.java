package com.example.sfopps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btn_ir_crearCuenta, iniciar_sesion;

    private SharedPreferences prefs;
    private boolean login;
    private EditText correo, contraseña;



    private String email = "";
    private String clave = "";
   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = (EditText) findViewById(R.id.et_correo);
        contraseña = (EditText) findViewById(R.id.et_contrasena);

        mAuth = FirebaseAuth.getInstance();

        btn_ir_crearCuenta = findViewById(R.id.btn_ir_crearCuenta);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);

        btn_ir_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = correo.getText().toString();
                clave = contraseña.getText().toString();

                if (!email.isEmpty() && !clave.isEmpty()) {
                    estudiantelogin();
                } else {
                    Toast.makeText(Login.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void estudiantelogin() {

        mAuth.signInWithEmailAndPassword(email,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Bienvendio :" + email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Principal.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesión. Compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}