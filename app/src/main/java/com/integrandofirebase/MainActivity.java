package com.integrandofirebase;

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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextName;
    EditText mEditTextMail;
    EditText mEditTextPassword;
    Button  mBuuttonRegister , mBuuttonLogin;

    //VARIABLES QUE VAMOS A PASAR DATOS
    String name = "";
    String mail = "";
    String password = "";


    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextName = findViewById(R.id.edtTextName);
        mEditTextMail = findViewById(R.id.edtTextMail);
        mEditTextPassword = findViewById(R.id.edtTextPwd);
        mBuuttonRegister = findViewById(R.id.btnRegister);
        mBuuttonLogin = findViewById(R.id.btnLogin);

        mBuuttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEditTextName.getText().toString();
                mail = mEditTextMail.getText().toString();
                password = mEditTextPassword.getText().toString();


                if (!name.isEmpty() && !mail.isEmpty() && !password.isEmpty()){

                    if (password.length() >=6){
                        registerUser();

                    }else{
                        Toast.makeText(MainActivity.this,"Tu contraseña debe tener al menos 6 caracteres",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Debes llenar los campos",Toast.LENGTH_LONG).show();
                }
            }
        });

        mBuuttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object>map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",mail);
                    map.put("password",password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"No se pudiero crear los datos",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this,"No se han podido registar el usuario",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            Intent intent=new Intent(this,ProfileActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
