package com.example.netgear.homeyandroid;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "customAuthActivity";
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;
    private TextView textViewErrorMessage;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        textViewErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);

        buttonSignIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            ((TextView) findViewById(R.id.editTextEmail)).setText(
                    "User Email: " + user.getEmail());
        } else {
            ((TextView) findViewById(R.id.textViewErrorMessage)).setText(
                    "Error: sign in failed.");
        }
    }

    private void signInUser(){
        String email = editTextEmail.getText().toString().trim();

        Log.d("HOMEY:EMAIL", email);

        String password = editTextPassword.getText().toString().trim();

        Log.d("HOMEY:PASSWORD", password);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            //email is empty
            Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
            //STOP THE FUNCTION HERE
            return;
        }

        /*if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }*/

        //if validations are ok
        //we will first show a progressbar

        Log.d("HOMEY","ENTER STEP 1");

        progressDialog.setMessage("Signing you in....");
        progressDialog.show();

        Log.d("HOMEY","ENTER STEP 2");
        /*firebaseAuth.signInWithEmailAndPassword(email, password)*/
        Log.d("HOMEY:EMAIL", email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("HOMEY","ENTER STEP 3");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // ...
                    }
                });



    }

    @Override
    public void onClick(View view){
        if(view == buttonSignIn){
            signInUser();
        }
        if(view == textViewRegister){
            //INITIATE LOGIN
        }
    }
}
