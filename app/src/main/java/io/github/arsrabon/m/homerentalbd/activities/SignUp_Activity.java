package io.github.arsrabon.m.homerentalbd.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignUp_Activity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    EditText edit_Username;
    EditText edit_Email;
    EditText edit_Password;
    EditText edit_ConfPassword;

    Button btn_SignUp;
    Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        edit_Username = (EditText) findViewById(R.id.edit_newUsername);
        edit_Email = (EditText) findViewById(R.id.edit_newUserEmail);
        edit_Password = (EditText) findViewById(R.id.edit_newUserPassword);
        edit_ConfPassword = (EditText) findViewById(R.id.edit_newUserconfPassword);

        btn_SignUp = (Button) findViewById(R.id.btn_signUp);
        btn_skip = (Button) findViewById(R.id.btn_goback);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edit_Username.getText().toString();
                final String email = edit_Email.getText().toString();
                final String password = edit_Password.getText().toString();
                String confPassword = edit_ConfPassword.getText().toString();
                if (username.length() > 6 &&
                        email.length() > 15 &&
                        password.length() > 6 &&
                        confPassword.equals(password)) {

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(SignUp_Activity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(SignUp_Activity.this,EditUserProfile.class);
                                firebaseUser = firebaseAuth.getCurrentUser();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}
