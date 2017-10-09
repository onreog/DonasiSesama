package com.ivcode.donasisesama;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class lihat_penyaluran extends AppCompatActivity implements TextWatcher {
    EditText textCari;
    database_operations dop;
    ListView list;
    CustomListPenyaluranAdapter adapter;
    public lihat_penyaluran CustomListView = null;
    public ArrayList<ListDataPenyaluran> CustomListViewValuesArr = new ArrayList<ListDataPenyaluran>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_penyaluran);
        dop = new database_operations(this);
        CustomListView = this;
        Resources res = getResources();
        textCari = (EditText)findViewById(R.id.txtSearchPenyaluran);
        textCari.addTextChangedListener(this);
        list=(ListView)findViewById(R.id.list_penyaluran);
        adapter = new CustomListPenyaluranAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        setListPenyaluran();
    }
    public void cariPenyaluran(String teks){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        try{
            Cursor ambildata = dop.get_data_penyaluran(teks);
            CustomListViewValuesArr.clear();
            while(ambildata.moveToNext()){
                final ListDataPenyaluran sched = new ListDataPenyaluran();
                sched.setIdPenyaluran(ambildata.getString(0));
                sched.setTujuan(ambildata.getString(1));
                sched.setNama(ambildata.getString(2));
                sched.setTanggal(ambildata.getString(3));
                sched.setNominal("Rp. " + decimalFormat.format(Integer.valueOf(ambildata.getString(4))));
                sched.setNominalReal(ambildata.getString(4));
                CustomListViewValuesArr.add(sched);
            }
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        }catch (Exception e){

        }
    }
    public void setListPenyaluran(){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        try{
            Cursor ambildata = dop.get_data_penyaluran();
            CustomListViewValuesArr.clear();
            if(ambildata.getCount() > 0) {
                while (ambildata.moveToNext()) {
                    final ListDataPenyaluran sched = new ListDataPenyaluran();
                    sched.setIdPenyaluran(ambildata.getString(0));
                    sched.setTujuan(ambildata.getString(1));
                    sched.setNama(ambildata.getString(2));
                    sched.setTanggal(ambildata.getString(3));
                    sched.setNominal("Rp. " + decimalFormat.format(Integer.valueOf(ambildata.getString(4))));
                    sched.setNominalReal(ambildata.getString(4));

                    CustomListViewValuesArr.add(sched);
                }
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }catch (Exception e){

        }
    }
    public void onItemClick(final int mPosition)
    {
        final ListDataPenyaluran tempValues = (ListDataPenyaluran) CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView, "" + tempValues.getTanggal(), Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(lihat_penyaluran.this);
        dialog.setContentView(R.layout.activity_ubah_penyaluran);
        dialog.setTitle("Opsi " + tempValues.getTujuan());

        final EditText txt_ubah_penyaluran_id = (EditText)dialog.findViewById(R.id.txt_ubah_penyaluran_id);
        final EditText txt_ubah_penyaluran_tujuan = (EditText)dialog.findViewById(R.id.txt_ubah_penyaluran_tujuan);
        final EditText txt_ubah_penyaluran_nama = (EditText)dialog.findViewById(R.id.txt_ubah_penyaluran_nama);
        final EditText txt_ubah_penyaluran_tanggal = (EditText)dialog.findViewById(R.id.txt_ubah_penyaluran_tanggal);
        final EditText txt_ubah_penyaluran_nominal = (EditText)dialog.findViewById(R.id.txt_ubah_penyaluran_nominal);

        txt_ubah_penyaluran_id.setText(tempValues.getIdPenyaluran());
        txt_ubah_penyaluran_tujuan.setText(tempValues.getTujuan());
        txt_ubah_penyaluran_nama.setText(tempValues.getNama());
        txt_ubah_penyaluran_tanggal.setText(tempValues.getTanggal());
        txt_ubah_penyaluran_nominal.setText(tempValues.getNominalReal());

        Button bt_ubah_ubah = (Button)dialog.findViewById(R.id.btn_ubah_penyaluran_ubah);
        bt_ubah_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String a = txt_ubah_penyaluran_id.getText().toString();
                    String b = txt_ubah_penyaluran_tujuan.getText().toString();
                    String c = txt_ubah_penyaluran_nama.getText().toString();
                    String d = txt_ubah_penyaluran_tanggal.getText().toString();
                    String e = txt_ubah_penyaluran_nominal.getText().toString();

                    boolean action_edit = dop.set_edit_penyaluran(a, b, c, d, e);
                    setListPenyaluran();
                    textCari.setText("");
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    Toast.makeText(lihat_penyaluran.this, "Data Berhasi Dirubah", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(lihat_penyaluran.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        Button bt_ubah_hapus = (Button)dialog.findViewById(R.id.btn_ubah_penyaluran_hapus);
        bt_ubah_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lihat_penyaluran.this);
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Apa Anda Yakin Akan menghapus Data Dengan ID : " + tempValues.getIdPenyaluran());
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {

                        try {
                            Integer action_delete = dop.delete_penyaluran(txt_ubah_penyaluran_id.getText().toString());
                            CustomListViewValuesArr.remove(mPosition);
                            adapter.notifyDataSetChanged();
                            list.setAdapter(adapter);
                            if(action_delete > 0){
                                Toast.makeText(lihat_penyaluran.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(lihat_penyaluran.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(lihat_penyaluran.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
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
                Intent a = new Intent(getBaseContext(), tambah_penyaluran.class);
                startActivity(a);
                finish();
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
        if(s.length() > 0 ){
            cariPenyaluran(s.toString());
        }else{
            setListPenyaluran();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
