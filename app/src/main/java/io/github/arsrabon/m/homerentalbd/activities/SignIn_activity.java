package io.github.arsrabon.m.homerentalbd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.arsrabon.m.homerentalbd.R;

public class SignIn_activity extends AppCompatActivity {

    Button btn_SignUp;
    Button btn_SignIn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    EditText et_userName;
    EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_SignUp = (Button) findViewById(R.id.btn_signup);
        btn_SignIn = (Button) findViewById(R.id.btn_Signin);

        et_userName = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn_activity.this,SignUp_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_userName.getText().toString().length() > 0 && et_password.getText().toString().length() >= 6){
                    String email = et_userName.getText().toString().trim();
                    String password = et_password.getText().toString().trim();

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignIn_activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(SignIn_activity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "success");
                                Intent intent = new Intent(SignIn_activity.this,DefaultActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else {
                    Toast.makeText(SignIn_activity.this, "Email Or Password Can't be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
