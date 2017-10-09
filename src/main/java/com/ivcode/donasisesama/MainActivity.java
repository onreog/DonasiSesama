package com.ivcode.donasisesama;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button bt_masukan_data, bt_lihat_data, bt_saldo, bt_keluar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_masukan_data = (Button)findViewById(R.id.btn_masukan_data);
        bt_lihat_data = (Button)findViewById(R.id.btn_lihat_data);
        bt_saldo = (Button)findViewById(R.id.btn_saldo);
        bt_keluar = (Button)findViewById(R.id.btn_keluar);
        bt_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getBaseContext(), saldo_akhir.class);
                startActivity(a);
            }
        });
        bt_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_masukan_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_menu_tambah);
                dialog.setCancelable(false);
                Button bt_tambah_donasi = (Button)dialog.findViewById(R.id.btn_tambah_donasi);
                Button bt_tambah_penyaluran = (Button)dialog.findViewById(R.id.btn_tambah_penyaluran);
                Button bt_tambah_batal = (Button)dialog.findViewById(R.id.btn_tambah_batal);
                bt_tambah_donasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(getBaseContext(), tambah_donasi.class);
                        startActivity(a);
                    }
                });
                bt_tambah_penyaluran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(getBaseContext(), tambah_penyaluran.class);
                        startActivity(a);
                    }
                });
                bt_tambah_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        bt_lihat_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_menu_lihat);
                dialog.setCancelable(false);
                Button bt_lihat_donasi = (Button)dialog.findViewById(R.id.btn_lihat_donasi);
                Button bt_lihat_penyaluran = (Button)dialog.findViewById(R.id.btn_lihat_penyaluran);
                Button bt_lihat_batal = (Button)dialog.findViewById(R.id.btn_lihat_batal);

                bt_lihat_donasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(getBaseContext(), lihat_donasi.class);
                        startActivity(a);
                    }
                });
                bt_lihat_penyaluran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(getBaseContext(), lihat_penyaluran.class);
                        startActivity(a);
                    }
                });
                bt_lihat_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
