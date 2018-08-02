package com.dev.sq.catatkuy;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.dev.sq.catatkuy.Model.Catatan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public static final String JUDUL = "com.dev.sq.catatkuy.judul";
    public static final String KONTEN = "com.dev.sq.catatkuy.konten";
    public static final String NODE_ID = "com.dev.sq.catatkuy.node_id";
    public static final String TAG = "CatatanFragment";
    private ImageButton btnBuat;
    private ArrayAdapter<String> adapter;

    private ListView listViewCatatans;
    List<Catatan> catatans;
    DatabaseReference databaseCatatans;

    ArrayList<String> listKonten = new ArrayList<>();
    ArrayList<String> listNode_ID = new ArrayList<>();

    public CatatanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_catatan, container, false);

        btnBuat = v.findViewById(R.id.btnTambah);
        listViewCatatans = v.findViewById(R.id.listview);

        databaseCatatans = FirebaseDatabase.getInstance().getReference("Catatan").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        catatans = new ArrayList<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>();
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                listViewCatatans.setAdapter(adapter);
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String judul = ds.child("judul").getValue(String.class);
                    String konten = ds.child("konten").getValue(String.class);
                    String node_id = ds.getKey();
                    list.add(judul);
                    listKonten.add(konten);
                    listNode_ID.add(node_id);
                    Log.d(TAG, "Key : "+node_id);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseCatatans.addListenerForSingleValueEvent(eventListener);

        listViewCatatans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listViewCatatans.getItemAtPosition(position);

                Intent intent = new Intent(getContext(),UbahCatatan.class);

                intent.putExtra(JUDUL, itemValue);
                intent.putExtra(KONTEN, listKonten.get(itemPosition));
                intent.putExtra(NODE_ID, listNode_ID.get(itemPosition));

                startActivity(intent);
            }
        });

        listViewCatatans.setLongClickable(true);
        listViewCatatans.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        getContext());
                alert.setTitle("Pemberitahuan!!");
                alert.setMessage("Apakah kamu yakin ingin menghapus ?");
                Log.d(TAG, "Key Hapus : "+listNode_ID.get(position));
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        FirebaseDatabase.getInstance().getReference("Catatan").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(listNode_ID.get(position)).removeValue();

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayList<String> list = new ArrayList<>();
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                                listViewCatatans.setAdapter(adapter);
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String judul = ds.child("judul").getValue(String.class);
                                    String konten = ds.child("konten").getValue(String.class);
                                    String node_id = ds.getKey();
                                    list.add(judul);
                                    listKonten.add(konten);
                                    listNode_ID.add(node_id);
                                    Log.d(TAG, "Key : "+node_id);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        };
                        databaseCatatans.addListenerForSingleValueEvent(eventListener);

                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
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

    @Override
    public void onResume() {
        super.onResume();
    }
}