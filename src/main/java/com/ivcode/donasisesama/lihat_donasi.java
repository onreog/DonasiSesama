package com.ivcode.donasisesama;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class lihat_donasi extends AppCompatActivity implements TextWatcher{
    EditText textCari;
    database_operations dop;
    ListView list;
    CustomListDonasiAdapter adapter;
    public lihat_donasi CustomListView = null;
    public ArrayList<ListDataDonasi> CustomListViewValuesArr = new ArrayList<ListDataDonasi>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_donasi);
        dop = new database_operations(this);
        CustomListView = this;
        Resources res = getResources();
        textCari = (EditText)findViewById(R.id.txtSearchDonasi);
        textCari.addTextChangedListener(this);
        list=(ListView)findViewById(R.id.list_donasi);
        adapter = new CustomListDonasiAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        setListDonasi();
    }
    public void cariDonasi(String teks){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        try{
            Cursor ambildata = dop.get_data_pemasukan(teks);
            CustomListViewValuesArr.clear();
            while(ambildata.moveToNext()){
                final ListDataDonasi sched = new ListDataDonasi();
                sched.setNud(ambildata.getString(0));
                sched.setNama(ambildata.getString(1));
                sched.setTanggal(ambildata.getString(2));
                sched.setNominal("Rp. " + decimalFormat.format(Integer.valueOf(ambildata.getString(3))));
                sched.setNominalReal(ambildata.getString(3));
                CustomListViewValuesArr.add(sched);
            }
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        }catch (Exception e){

        }
    }
    public void setListDonasi(){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        try{
            Cursor ambildata = dop.get_data_pemasukan();
            CustomListViewValuesArr.clear();
            if(ambildata.getCount() > 0) {
                while (ambildata.moveToNext()) {
                    final ListDataDonasi sched = new ListDataDonasi();
                    sched.setNud(ambildata.getString(0));
                    sched.setNama(ambildata.getString(1));
                    sched.setTanggal(ambildata.getString(2));
                    sched.setNominal("Rp. " + decimalFormat.format(Integer.valueOf(ambildata.getString(3))));
                    sched.setNominalReal(ambildata.getString(3));

                    CustomListViewValuesArr.add(sched);
                }
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_search:
                if(textCari.getVisibility() == View.GONE){
                    textCari.setVisibility(View.VISIBLE);
                }else{
                    textCari.setVisibility(View.GONE);
                    textCari.setText("");
                }
                return true;
            case R.id.menu_add_new:
                Intent a = new Intent(getBaseContext(), tambah_donasi.class);
                startActivity(a);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onItemClick(final int mPosition)
    {
        final ListDataDonasi tempValues = (ListDataDonasi)CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView, "" + tempValues.getTanggal(), Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(lihat_donasi.this);
        dialog.setContentView(R.layout.activity_ubah_donasi);
        dialog.setTitle("Opsi " + tempValues.getNama());
        final EditText txt_ubah_nud = (EditText)dialog.findViewById(R.id.txt_ubah_nud);
        final EditText txt_ubah_donatur = (EditText)dialog.findViewById(R.id.txt_ubah_donatur);
        final EditText txt_ubah_tanggal = (EditText)dialog.findViewById(R.id.txt_ubah_tanggal);
        final EditText txt_ubah_nominal = (EditText)dialog.findViewById(R.id.txt_ubah_nominal);

        txt_ubah_nud.setText(tempValues.getNud());
        txt_ubah_donatur.setText(tempValues.getNama());
        txt_ubah_tanggal.setText(tempValues.getTanggal());
        txt_ubah_nominal.setText(tempValues.getNominalReal());

        Button bt_ubah_ubah = (Button)dialog.findViewById(R.id.btn_ubah_ubah);
        bt_ubah_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String a = txt_ubah_nud.getText().toString();
                    String b = txt_ubah_donatur.getText().toString();
                    String c = txt_ubah_tanggal.getText().toString();
                    String d = txt_ubah_nominal.getText().toString();
                    boolean action_edit = dop.set_edit_donasi(a, b, c, d);
                    setListDonasi();
                    textCari.setText("");
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    Toast.makeText(lihat_donasi.this, "Data Berhasi Dirubah", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(lihat_donasi.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        Button bt_ubah_hapus = (Button)dialog.findViewById(R.id.btn_ubah_hapus);
        bt_ubah_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lihat_donasi.this);
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Apa Anda Yakin Akan menghapus Data Dengan NUD : " + tempValues.getNud());
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {

                        try {
                            Integer action_delete = dop.delete_donasi(txt_ubah_nud.getText().toString());
                            CustomListViewValuesArr.remove(mPosition);
                            adapter.notifyDataSetChanged();
                            list.setAdapter(adapter);
                            if(action_delete > 0){
                                Toast.makeText(lihat_donasi.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(lihat_donasi.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(lihat_donasi.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        //dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 0 ){
            cariDonasi(s.toString());
        }else{
            setListDonasi();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
