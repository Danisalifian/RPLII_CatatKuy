package com.dev.sq.catatkuy.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Catatan {
    private String id;
    private String judul;
    private String konten;

    public Catatan(){

    }

    public Catatan(String id, String judul, String konten) {
        this.judul = judul;
        this.konten = konten;
        this.id = id;
    }

    public void setCatatanJudul(String judul) {
        this.judul = judul;
    }

    public void setCatatanKonten(String konten) {
        this.konten = konten;
    }

    public String getCatatanJudul() {
        return judul;
    }

    public String getCatatanKonten() {
        return konten;
    }
}