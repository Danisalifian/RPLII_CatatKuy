package com.dev.sq.catatkuy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatatanFragment extends Fragment {
    private ImageButton btnBuat;


    public CatatanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_catatan, container, false);

        btnBuat = (ImageButton) v.findViewById(R.id.btnTambah);

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buatcatatan = new Intent(getActivity(),BuatCatatan.class);
                startActivity(buatcatatan);
            }
        });

        return v;
    }

}
