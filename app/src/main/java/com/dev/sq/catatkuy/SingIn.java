package com.dev.sq.catatkuy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.sq.catatkuy.Common.Common;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class SingIn extends AppCompatActivity {
    private TextView tName,tEmail;
    private Button btnLogout,btnMain,btnLogin;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.SIGN_IN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(SingIn.this,new StringBuilder("Welcome ")
                        .append(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()),Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build());
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnMain = (Button) findViewById(R.id.btnMain);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Check if not sign in then navigatae to sign in page
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers).build(), Common.SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(SingIn.this,new StringBuilder("Welcome ")
                    .append(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()),Toast.LENGTH_SHORT).show();
            loadUserInformation();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingIn.this,Main.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).build(), Common.SIGN_IN_REQUEST_CODE);
            }
        });

    }

    private void signout(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void loadUserInformation() {
        tName = (TextView) findViewById(R.id.name);
        tEmail =(TextView) findViewById(R.id.email);
        tEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

    }
}
