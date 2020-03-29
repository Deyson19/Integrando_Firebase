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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextMail;
    EditText mEditTextPassword;
    Button mButtonLogin;
    Button mButtonResetPassword;

    String mail = "";
    String password = "";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEditTextMail = findViewById(R.id.edtTextMail);
        mEditTextPassword = findViewById(R.id.edtTextPwd);
        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonResetPassword = findViewById(R.id.btnSendToResetPassword);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = mEditTextMail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!mail.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(LoginActivity.this,"Debes rellenar los campos",Toast.LENGTH_LONG).show();
                }
            }
        });

        mButtonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(){
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"No se ha podido iniciar sesion \n Debes comprobar tus datos",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
