package com.integrandofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText mEditTextEmail;
    Button mButtonResetPassword;

    String email = "";
    FirebaseAuth mAuth;

    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        mEditTextEmail = findViewById(R.id.editTextEmail);
        mButtonResetPassword = findViewById(R.id.btnResetPassword);

        mButtonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setMessage("Espera un momento mientras enviamos tu correo");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
                email = mEditTextEmail.getText().toString();

                if (!email.isEmpty()){
                    resetPassword();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"Por favor ingresa un correo válido",Toast.LENGTH_LONG).show();
                }

                mDialog.dismiss();
            }
        });
    }

    private void resetPassword(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Se ha enviado un correo para reestablecer tu contraseña \n Revisa tu Email",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"No hemos podido enviar el correo \n Para reestablecer tu contraseña",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
