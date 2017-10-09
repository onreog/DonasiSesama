package com.ivcode.donasisesama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class tambah_donasi extends AppCompatActivity {
    Button bt_setdatepicker;
    EditText textTanggal, textNud, textDonatur, textNominal;
    database_operations dop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_donasi);
        dop = new database_operations(this);



        textTanggal = (EditText)findViewById(R.id.txtTanggal);
        textNud = (EditText)findViewById(R.id.txt_donasi_nud);
        textDonatur = (EditText)findViewById(R.id.txt_donasi_donatur);
        textNominal = (EditText)findViewById(R.id.txt_donasi_nominal);
        setNud();
        bt_setdatepicker = (Button)findViewById(R.id.btn_setDatePicker);

        bt_setdatepicker.setOnClickListener(new View.OnClickListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = dayOfMonth + "-" + monthOfYear + "-" + year;
                    textTanggal.setText(my_date);
                }
            };
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                Dialog dialogDate = new DatePickerDialog(tambah_donasi.this, mDatesetListener, calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH), calender
                        .get(Calendar.DAY_OF_MONTH));
                dialogDate.show();
            }
        });
    }
    public void setNud(){
        int randomNumber = (int)(Math.random() * 100000) + 0;
        textNud.setText("N" + String.valueOf(randomNumber));
    }
    public void kosongkan(){
        textNud.setText("");
        textDonatur.setText("");
        textTanggal.setText("");
        textNominal.setText("");

        setNud();
        textDonatur.requestFocus();
    }
    public void simpan_data_donasi(){
        if(textNud.getText().length() > 0){
            if(textDonatur.getText().length() > 0){
                if(textTanggal.getText().length() > 0){
                    if(textNominal.getText().length() > 0){
                        String fil1 = textNud.getText().toString();
                        String fil2 = textDonatur.getText().toString();
                        String fil3 = textTanggal.getText().toString();
                        String fil4 = textNominal.getText().toString();
                        boolean simpan_donasi = dop.insert_data_donasi(fil1, fil2, fil3, fil4);
                        if(simpan_donasi){
                            Toast.makeText(getBaseContext(), "Donasi Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getBaseContext(), "Donasi Gagal Disimpan", Toast.LENGTH_SHORT).show();
                        }
                        kosongkan();
                    }else {
                        tampilToast("Nominal Masih Kosong..");
                        textNominal.requestFocus();
                    }
                }else {
                    tampilToast("Tanggal Masih Kosong..");
                    textTanggal.requestFocus();
                }
            }else {
                tampilToast("Donatur Masih Kosong..");
                textDonatur.requestFocus();
            }
        }else {
            tampilToast("Nud Masih Kosong..");
            textNud.requestFocus();
        }
    }
    public void tampilToast(String pesan){
        Toast.makeText(getBaseContext(), pesan, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_save_text:
                simpan_data_donasi();
                return true;
            case R.id.menu_reset_text:
                kosongkan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
