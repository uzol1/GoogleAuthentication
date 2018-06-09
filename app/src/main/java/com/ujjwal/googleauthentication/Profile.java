package com.ujjwal.googleauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    ImageView user_img;
    TextView username, useremail;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user_img=findViewById(R.id.usr_img);
        username=findViewById(R.id.username);
        useremail=findViewById(R.id.email);
        signout= findViewById(R.id.logout);

        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        useremail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        user_img.setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
        Picasso.with(getBaseContext())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(user_img);
           signout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   FirebaseAuth.getInstance().signOut();
                   finish();
               }
           });

    }

}
