package com.dev.sq.catatkuy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class BuatCatatan extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText eJudul,eKonten;
    private Button btnSimpan;

    private DatabaseReference sCatatanDB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_catatan);

        mToolbar = (Toolbar) findViewById(R.id.buat_catatan_toolbar);
        eJudul = (EditText) findViewById(R.id.judul);
        eKonten = (EditText) findViewById(R.id.konten);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        mAuth = FirebaseAuth.getInstance();
        sCatatanDB = FirebaseDatabase.getInstance().getReference().child("Catatan").child(mAuth.getCurrentUser().getUid());

        mToolbar.setTitle("Buat Catatan");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = eJudul.getText().toString().trim();
                String konten = eKonten.getText().toString().trim();

                if(!TextUtils.isEmpty(judul) && !TextUtils.isEmpty(konten)){
                    simpan_catatan(judul,konten);
                } else {
                    Snackbar.make(btnSimpan, "Judul/Konten masih kosong", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            //end this activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan_catatan(String judul, String konten){
        if (mAuth.getCurrentUser() != null){

            final DatabaseReference newCatatanRef = sCatatanDB.push();

            final Map catatanMap = new HashMap();
            catatanMap.put("judul",judul);
            catatanMap.put("konten",konten);
            catatanMap.put("waktu", ServerValue.TIMESTAMP);

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    newCatatanRef.setValue(catatanMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(BuatCatatan.this,"Catatan telah disimpan"
                                        , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BuatCatatan.this,Main.class));
                            } else {
                                Toast.makeText(BuatCatatan.this,"Error" + task.getException().getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            mainThread.start();
        } else {
            Toast.makeText(this,"User is not signed",Toast.LENGTH_SHORT).show();
        }
    }
}
