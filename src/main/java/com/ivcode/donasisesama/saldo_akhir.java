package com.ivcode.donasisesama;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class saldo_akhir extends AppCompatActivity {
    database_operations dop;
    TextView lbJumlahDataDonasi, lbJumlahDataPenyaluran, lbTotalDonasi, lbToatalPenyaluran, lbTotal, lbKelayakan;
    Button bt_penyaluran, bt_donasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo_akhir);
        dop = new database_operations(this);
        lbJumlahDataDonasi = (TextView) findViewById(R.id.txt_saldo_jumlah);
        lbJumlahDataPenyaluran = (TextView) findViewById(R.id.txt_saldo_jumlah_penyaluran);
        lbTotal = (TextView) findViewById(R.id.txt_saldo_akhir);
        lbKelayakan = (TextView) findViewById(R.id.txt_saldo_kelayakan);

        lbTotalDonasi = (TextView) findViewById(R.id.txt_saldo_total_donasi);
        lbToatalPenyaluran = (TextView) findViewById(R.id.txt_saldo_total_penyaluran);


        hitung();
    }
    public void hitung(){
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        Cursor instruksi = dop.get_data_pemasukan();
        Cursor instruksi1 = dop.get_data_penyaluran();

        double kelayakan = 0.0, jumlah = 0.0, jumlah1 = 0.0;
        try {
            if (instruksi.getCount() > 0) {
                while (instruksi.moveToNext()) {
                    jumlah = jumlah + Integer.valueOf(instruksi.getString(3));
                }
                while (instruksi1.moveToNext()) {
                    jumlah1 = jumlah1 + Integer.valueOf(instruksi1.getString(4));
                }
            }
            if(jumlah == 0 && jumlah1 == 0){
                kelayakan = 0;
            }else {
                kelayakan = Double.valueOf(jumlah) / Double.valueOf(jumlah1);

            }

            if(kelayakan == 1){
                lbKelayakan.setText("(" + decimalFormat.format(kelayakan) + ") Impas");
            }else{
                if(kelayakan > 1){
                    lbKelayakan.setText("(" + decimalFormat.format(kelayakan) + ") Layak");
                }else{
                    lbKelayakan.setText("(" + decimalFormat.format(kelayakan) + ") Tidak Layak");
                }
            }

            double nominal = jumlah - jumlah1;
            lbTotalDonasi.setText("Rp. " + decimalFormat.format(jumlah));
            lbToatalPenyaluran.setText("Rp. " + decimalFormat.format(jumlah1));
            lbJumlahDataDonasi.setText(String.valueOf(instruksi.getCount()));
            lbJumlahDataPenyaluran.setText(String.valueOf(instruksi1.getCount()));
            lbTotal.setText("Rp. " + decimalFormat.format(nominal));




        }catch (Exception e){
            Toast.makeText(getBaseContext(), "Pesan : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //String pattern = "###,###.###";
        //DecimalFormat decimalFormat = new DecimalFormat(pattern);
        //nominal_avaible.setText("Rp. " + decimalFormat.format(nominal));
    }
}
