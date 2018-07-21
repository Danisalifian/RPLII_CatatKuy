package com.dev.sq.catatkuy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity {
    private TextView tName,tEmail,tUid;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
                startActivity(new Intent(Main.this,SingIn.class));
            }
        });

        loadUserInformation();
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
        tUid = (TextView) findViewById(R.id.uid);
        tEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        tUid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }
}
