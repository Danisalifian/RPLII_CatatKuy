package com.dev.sq.catatkuy.Model;

public class Users {
    private String Email;
    private String Nama;

    public Users(String email, String nama) {
        Email = email;
        Nama = nama;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }
}


