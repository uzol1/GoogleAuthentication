package com.ujjwal.googleauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    Button signin;
    private int myl,requestcode=1000;
    FirebaseAuth auth;
    GoogleSignInClient signInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin= (Button)findViewById(R.id.btnsign);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin= signInClient.getSignInIntent();
                startActivityForResult(signin,requestcode);
            }



        });
        auth= FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();

        signInClient=GoogleSignIn.getClient(this,gso);



    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = auth.getCurrentUser();
        if(currentuser!=null){
            Intent Profile = new Intent(MainActivity.this,Profile.class);
            startActivity(Profile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestcode){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account= task.getResult(ApiException.class);
                FirebaseSignin(account);

            }catch (ApiException e){
                Toast.makeText(this,"could not signin ", Toast.LENGTH_SHORT).show();

            }
        }
    }



    public void FirebaseSignin(final GoogleSignInAccount account){

        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user= auth.getCurrentUser();
                            Intent Profile= new Intent(MainActivity.this,Profile.class);
                            startActivity(Profile);
                            Toast.makeText(MainActivity.this, "signin successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

