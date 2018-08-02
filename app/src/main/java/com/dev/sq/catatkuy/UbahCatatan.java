package com.dev.sq.catatkuy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UbahCatatan extends AppCompatActivity {
    public static final String TAG = "UbahCatatan";
    private Toolbar mToolbar;
    private EditText eJudul,eKonten;
    private Button btnSimpan;
    private String noteID ="no";

    private DatabaseReference sCatatanDB;
    private FirebaseAuth mAuth;

    private boolean ketemu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_catatan);



        Intent intent = getIntent();

        try {
            noteID = getIntent().getStringExtra(CatatanFragment.NODE_ID);

            if (!noteID.trim().equals("")){
                ketemu = true;
            } else {
                ketemu = false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        Log.d(TAG, "Key Diterima : " + getIntent().getStringExtra(CatatanFragment.NODE_ID));

        mToolbar = (Toolbar) findViewById(R.id.ubah_catatan_toolbar);
        eJudul = (EditText) findViewById(R.id.judul);
        eKonten = (EditText) findViewById(R.id.konten);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        sCatatanDB = FirebaseDatabase.getInstance().getReference("Catatan").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mToolbar.setTitle("Ubah Catatan");
//        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        eJudul.setText(intent.getStringExtra(CatatanFragment.JUDUL));
        eKonten.setText(intent.getStringExtra(CatatanFragment.KONTEN));

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
        putData();
    }

    private void putData() {
        if (ketemu) {
            sCatatanDB.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("judul") && dataSnapshot.hasChild("konten")) {
                        String judul = dataSnapshot.child("judul").getValue().toString();
                        String konten = dataSnapshot.child("konten").getValue().toString();

                        eJudul.setText(judul);
                        eKonten.setText(konten);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
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
        //Perbarui catatan
        final Map updateMap = new HashMap();
        updateMap.put("judul", judul);
        updateMap.put("konten", konten);
        //updateMap.put("timestamp",ServerValue.TIMESTAMP);

        sCatatanDB.child(noteID).updateChildren(updateMap);
        Toast.makeText(this,"Catatan diperbarui",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(UbahCatatan.this, Main.class));
        finish();

//        if (mAuth.getCurrentUser() != null){
//            if (ketemu){
//
//
//            }
//            else {
//                //Simpan Catatan
//                final DatabaseReference newCatatanRef = sCatatanDB.push();
//
//                final Map catatanMap = new HashMap();
//                catatanMap.put("judul", judul);
//                catatanMap.put("konten", konten);
//                //catatanMap.put("waktu", ServerValue.TIMESTAMP);
//
//                Thread mainThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        newCatatanRef.setValue(catatanMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(UbahCatatan.this, "Catatan telah disimpan"
//                                            , Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(UbahCatatan.this, Main.class));
//                                } else {
//                                    Toast.makeText(UbahCatatan.this, "Error" + task.getException().getMessage()
//                                            , Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                });
//                mainThread.start();
//            }
//        } else {
//            Toast.makeText(this,"User is not signed",Toast.LENGTH_SHORT).show();
//        }
    }
}
