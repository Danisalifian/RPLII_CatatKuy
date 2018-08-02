package com.dev.sq.catatkuy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.sq.catatkuy.Model.Catatan;

import java.util.List;

public class CatatanList extends ArrayAdapter<Catatan> {
    private Activity context;
    List<Catatan> catatans;

    public CatatanList(Activity context, List<Catatan> catatans) {
        super(context, R.layout.layout_catatan_list, catatans);
        this.context = context;
        this.catatans = catatans;
    }

    @Override
    public int getCount() {
        return catatans.size();
    }

    @Override
    public Catatan getItem(int pos) {
        return catatans.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_catatan_list, null);
        }

        TextView textViewJudul = convertView.findViewById(R.id.textViewJudul);
        TextView textViewKonten = convertView.findViewById(R.id.textViewKonten);

        Catatan catatan = catatans.get(position);
        textViewJudul.setText(catatan.getCatatanJudul());
        textViewKonten.setText(catatan.getCatatanKonten());

        return convertView;
    }
}
