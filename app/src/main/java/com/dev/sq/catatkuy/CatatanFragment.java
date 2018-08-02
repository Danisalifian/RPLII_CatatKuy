package com.dev.sq.catatkuy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatatanFragment extends Fragment {
    public static final String TAG = "CatatanFragment";
    private ImageButton btnBuat;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    public CatatanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_catatan, container, false);

        btnBuat = v.findViewById(R.id.btnTambah);
        listView = v.findViewById(R.id.listview);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference productsRef = rootRef.child("Catatan").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("judul").getValue(String.class);
                    list.add(name);
                    Log.d(TAG, "Judul : "+name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        productsRef.addListenerForSingleValueEvent(eventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(),UbahCatatan.class);
                startActivity(intent);
            }
        });

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