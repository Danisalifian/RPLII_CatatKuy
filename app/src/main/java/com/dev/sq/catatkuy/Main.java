package com.dev.sq.catatkuy;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Main extends AppCompatActivity {
//    private TextView tName,tEmail,tUid;
//    private Button btnLogout;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private CatatanFragment catatanFragment;
    private PengingatFragment pengingatFragment;
    private PengaturanFragment pengaturanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        catatanFragment = new CatatanFragment();
        pengaturanFragment = new PengaturanFragment();
        pengingatFragment = new PengingatFragment();

        setFragment(catatanFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_catatan :
                        setFragment(catatanFragment);
                        return true;
                    case R.id.nav_pengingat :
                        setFragment(pengingatFragment);
                        return true;
                    case R.id.nav_pengaturan :
                        setFragment(pengaturanFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

//        btnLogout = (Button) findViewById(R.id.btnLogout);
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signout();
//                startActivity(new Intent(Main.this,SingIn.class));
//            }
//        });
//
//        loadUserInformation();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

//    private void signout(){
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // back to login page
//                        startActivity(new Intent(Main.this,SingIn.class));
//                    }
//                });
//    }

//    private void loadUserInformation() {
//        tName = (TextView) findViewById(R.id.name);
//        tEmail =(TextView) findViewById(R.id.email);
//        tUid = (TextView) findViewById(R.id.uid);
//        tEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        tName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//        tUid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//    }
}
