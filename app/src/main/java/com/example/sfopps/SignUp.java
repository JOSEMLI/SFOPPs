package com.example.sfopps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    Button btn_ir_inicioSesion, crear_cuenta;


    private EditText usu, tel, pass, correo;


    private String nombre = "";
    private String telefono = "";
    private String password = "";
    private String email = "";

    FirebaseAuth mAuth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        usu = findViewById(R.id.et_nombreusu);
        tel = findViewById(R.id.et_telefono_usu);
        pass = findViewById(R.id.et_contrasena);
        correo = findViewById(R.id.et_correo);


        btn_ir_inicioSesion = findViewById(R.id.btn_ir_inicioSesion);
        crear_cuenta = findViewById(R.id.crear_cuenta);

        btn_ir_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = usu.getText().toString();
                telefono = tel.getText().toString();
                password = pass.getText().toString();
                email = correo.getText().toString();

                if (!nombre.isEmpty() && !telefono.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                    if (password.length() >= 6) {
                        registrarestudiante();
                        Toast.makeText(SignUp.this, "se registro a " + nombre, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, "La contraseña debe tener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Rellenar los campos vacios !!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void registrarestudiante(){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("usuario", nombre);
                    map.put("telefono", telefono);
                    map.put("clave", password);
                    map.put("correo", email);

                    String id = mAuth.getCurrentUser().getUid();
                    db.child("PRUEBA").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Se registro Correctamente", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp.this, Login.class));
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Error al registrar", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(SignUp.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}