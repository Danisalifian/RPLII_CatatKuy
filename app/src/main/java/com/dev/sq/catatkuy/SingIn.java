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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class SingIn extends AppCompatActivity {

    private SignInButton btnLogin;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.SIGN_IN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(SingIn.this,new StringBuilder("Welcome ")
                        .append(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString()),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SingIn.this,Main.class));
                finish();
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

        btnLogin = (SignInButton) findViewById(R.id.btnLogin);

        //Check if already sign in
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(SingIn.this,Main.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).build(), Common.SIGN_IN_REQUEST_CODE);
            }
        });

    }
}
