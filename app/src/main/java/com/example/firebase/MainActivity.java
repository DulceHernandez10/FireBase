package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText email;
    private EditText password;
    private Button mButtonRegister;

    //VARIABLE DE LOS DATOS QUE VAMOS A REGISTRAR

    private String name="";
    private String em="";
    private String pass="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_main);
        nombre=findViewById(R.id.nombre);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        mButtonRegister=findViewById(R.id.btnRegister);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                name=nombre.getText().toString();
                em=email.getText().toString();
                pass=password.getText().toString();

                if(!name.isEmpty() && !em.isEmpty() && !pass.isEmpty()) {
                    if(pass.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(MainActivity.this, "El password debe de tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(MainActivity.this, "Debe de completar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object>map=new HashMap<>();
                    map.put("name", name);
                    map.put("em", em);
                    map.put("pass", pass);

                    String id=mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, Profile.class));
                                finish();

                            }else{
                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void Regresar(View view){
       finish();

    }
}