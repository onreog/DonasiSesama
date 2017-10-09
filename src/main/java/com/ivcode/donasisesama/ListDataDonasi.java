package com.ivcode.donasisesama;

/**
 * Created by Muhammad Irvan on 19/04/2016.
 */
public class ListDataDonasi {
    private String Nud = null;
    private String Nama = null;
    private String Tanggal = null;
    private String Nominal = null;
    private String NominalReal = null;

    public void setNud(String nud){
        this.Nud = nud;
    }
    public void setNama(String nama){
        this.Nama = nama;
    }
    public void setTanggal(String tanggal){
        this.Tanggal = tanggal;
    }
    public void setNominal(String nominal){
        this.Nominal = nominal;
    }
    public void setNominalReal(String nominal){
        this.NominalReal = nominal;
    }

    public String getNud(){
        return this.Nud;
    }
    public String getNama(){
        return this.Nama;
    }
    public String getTanggal(){
        return this.Tanggal;
    }
    public String getNominal(){
        return this.Nominal;
    }
    public String getNominalReal(){
        return this.NominalReal;
    }
}
