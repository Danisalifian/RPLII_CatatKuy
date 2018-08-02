package com.dev.sq.catatkuy;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengaturanFragment extends Fragment {
    private TextView tEmail,tNama;
//            ,tUid;
    private Button btnLogout;

    public PengaturanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        tEmail = (TextView) v.findViewById(R.id.email);
        tNama = (TextView) v.findViewById(R.id.nama);
//        tUid = (TextView) v.findViewById(R.id.uid);

        btnLogout = (Button) v.findViewById(R.id.btnLogout);

        tEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        tNama.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//        tUid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        return v;
    }

    private void signout(){
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // back to login page
                        startActivity(new Intent(getActivity(),SingIn.class));
                    }
                });
    }

}
