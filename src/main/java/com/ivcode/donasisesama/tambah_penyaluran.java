package com.ivcode.donasisesama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;

public class tambah_penyaluran extends AppCompatActivity implements TextWatcher {
    EditText textId, textTujuan, textNama, textTanggal, textNominal;
    TextView nominal_avaible;
    Button bt_setDate;
    database_operations dop;

    int nominal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyaluran);
        dop = new database_operations(this);

        textId = (EditText)findViewById(R.id.txt_penyaluran_id);
        textTujuan = (EditText)findViewById(R.id.txt_penyaluran_tujuan);
        textNama = (EditText)findViewById(R.id.txt_penyaluran_nama);
        textTanggal = (EditText)findViewById(R.id.txt_penyaluran_tanggal);
        textNominal = (EditText)findViewById(R.id.txt_penyaluran_nominal);
        bt_setDate = (Button)findViewById(R.id.btn_setDatePickerPenyaluran);

        bt_setDate.setOnClickListener(new View.OnClickListener() {
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
                Dialog dialogDate = new DatePickerDialog(tambah_penyaluran.this, mDatesetListener, calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH), calender
                        .get(Calendar.DAY_OF_MONTH));
                dialogDate.show();
            }
        });
        nominal_avaible = (TextView)findViewById(R.id.lb_nominal_tersedia);
        textNominal.addTextChangedListener(this);
        nominal = 0;
        setID();
        setSisa();
    }
    public void kosongkan(){
        textId.setText("");
        textTujuan.setText("");
        textNama.setText("");
        textTanggal.setText("");
        textNominal.setText("");

        setID();
        setSisa();
        textTujuan.requestFocus();

    }
    public void hitung(int value){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        int hasil = nominal - value;
        if(hasil>0){
            nominal_avaible.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            nominal_avaible.setTextColor(getResources().getColor(R.color.colorWrong));
        }
        nominal_avaible.setText("Tersedia : Rp. " + decimalFormat.format(hasil));


    }
    public void setSisa(){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        Cursor instruksi = dop.get_data_pemasukan();
        Cursor instruksi1 = dop.get_data_penyaluran();
        int jumlah = 0, jumlah1 = 0;
        try {
            if (instruksi.getCount() > 0) {
                while (instruksi.moveToNext()) {
                    //nominal_avaible.setText(instruksi.getString(3) + "\n");
                    jumlah = jumlah + Integer.valueOf(instruksi.getString(3));
                }
                while (instruksi1.moveToNext()) {
                    //nominal_avaible.setText(instruksi.getString(3) + "\n");
                    jumlah1 = jumlah1 + Integer.valueOf(instruksi1.getString(4));
                }
            }
            nominal = jumlah - jumlah1;

            nominal_avaible.setText("Tersedia : Rp. " + decimalFormat.format(nominal));
        }catch (Exception e){
            Toast.makeText(getBaseContext(), "Pesan : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //String pattern = "###,###.###";
        //DecimalFormat decimalFormat = new DecimalFormat(pattern);
        //nominal_avaible.setText("Rp. " + decimalFormat.format(nominal));
    }
    public void setID(){
        int randomNumber = (int)(Math.random() * 100000) + 0;
        textId.setText("ID" + String.valueOf(randomNumber));
    }
    public void tampilToast(String pesan){
        Toast.makeText(getBaseContext(), pesan, Toast.LENGTH_SHORT).show();
    }
    public void simpan_penyaluran(){
        if(textId.getText().length() > 0){
            if(textTujuan.getText().length() > 0){
                if(textNama.getText().length() > 0){
                    if(textTanggal.getText().length() > 0){
                        if(textNominal.getText().length() > 0) {
                            int hitung = nominal - Integer.valueOf(textNominal.getText().toString());
                            if(hitung >= 0) {
                                String fil1 = textId.getText().toString();
                                String fil2 = textTujuan.getText().toString();
                                String fil3 = textNama.getText().toString();
                                String fil4 = textTanggal.getText().toString();
                                String fil5 = textNominal.getText().toString();
                                boolean simpan_donasi = dop.insert_data_penyaluran(fil1, fil2, fil3, fil4, fil5);
                                if (simpan_donasi) {
                                    Toast.makeText(getBaseContext(), "Donasi Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "Donasi Gagal Disimpan", Toast.LENGTH_SHORT).show();
                                }
                                kosongkan();
                            }else {
                                tampilToast("Nominal Yang Anda Berikan Terlalu Besar..");
                                textNominal.requestFocus();
                            }
                        }else{
                            tampilToast("Nominal Masih Kosong..");
                            textNominal.requestFocus();
                        }
                    }else {
                        tampilToast("Tanggal Masih Kosong..");
                        textTanggal.requestFocus();
                    }
                }else {
                    tampilToast("Nama Masih Kosong..");
                    textNama.requestFocus();
                }
            }else {
                tampilToast("Tujuan Masih Kosong..");
                textTujuan.requestFocus();
            }
        }else {
            tampilToast("ID Masih Kosong..");
            textId.requestFocus();
        }
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
                simpan_penyaluran();
                return true;
            case R.id.menu_reset_text:
                kosongkan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //textNama.setText(s.toString());
        if(s.length() > 0) {
            hitung(Integer.valueOf(s.toString()));
        }else {
            hitung(0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
