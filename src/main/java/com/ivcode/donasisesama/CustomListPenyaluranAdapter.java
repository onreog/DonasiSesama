package com.ivcode.donasisesama;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammad Irvan on 19/04/2016.
 */
public class CustomListPenyaluranAdapter extends BaseAdapter implements OnClickListener {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListDataPenyaluran tempValues = null;
    int i=0;
    public CustomListPenyaluranAdapter(Activity a, ArrayList d, Resources resLocal){
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public static class ViewHolder{
        public TextView item_penyaluran_id;
        public TextView item_penyaluran_tujuan;
        public TextView item_penyaluran_nama;
        public TextView item_penyaluran_tanggal;
        public TextView item_penyaluran_nominal;

    }
    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if(convertView == null){
            vi = inflater.inflate(R.layout.custom_list_penyaluran, null);
            holder = new ViewHolder();
            holder.item_penyaluran_id = (TextView)vi.findViewById(R.id.txt_item_penyaluran_id_value);
            holder.item_penyaluran_tujuan = (TextView)vi.findViewById(R.id.txt_item_penyaluran_tujuan);
            holder.item_penyaluran_nama = (TextView)vi.findViewById(R.id.txt_item_penyaluran_nama_value);
            holder.item_penyaluran_tanggal = (TextView)vi.findViewById(R.id.txt_item_penyaluran_tanggal);
            holder.item_penyaluran_nominal = (TextView)vi.findViewById(R.id.txt_item_penyaluran_nominal);
            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.item_penyaluran_tujuan.setText("Data Kosong");
        }else{
            tempValues=null;
            tempValues = (ListDataPenyaluran) data.get(position);
            holder.item_penyaluran_id.setText(tempValues.getIdPenyaluran());
            holder.item_penyaluran_tujuan.setText(tempValues.getTujuan());
            holder.item_penyaluran_nama.setText(tempValues.getNama());
            holder.item_penyaluran_tanggal.setText(tempValues.getTanggal());
            holder.item_penyaluran_nominal.setText(tempValues.getNominal());
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {

    }
    private class OnItemClickListener implements OnClickListener{
        private int mPosition;
        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            lihat_penyaluran sct = (lihat_penyaluran)activity;
            sct.onItemClick(mPosition);
        }
    }
}
