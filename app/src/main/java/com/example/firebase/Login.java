package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText login, contraseña;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();

        login=findViewById(R.id.login);
        contraseña=findViewById(R.id.contraseña);
        btn_login=findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser=login.getText().toString().trim();
                String passUser=contraseña.getText().toString().trim();

                if(emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(Login.this, "Ingresa los datos correctamente", Toast.LENGTH_SHORT).show();

                }else{
                    loginUser(emailUser, passUser);
                }
            }

            private void loginUser(String emailUser, String passUser) {
                mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(Login.this, Profile.class));
                            Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });


        }

    public void Registrarse(View view){
        startActivity(new Intent(Login.this, MainActivity.class));

    }
    }
