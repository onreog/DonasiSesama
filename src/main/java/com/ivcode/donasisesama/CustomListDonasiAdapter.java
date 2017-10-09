package com.ivcode.donasisesama;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammad Irvan on 19/04/2016.
 */
public class CustomListDonasiAdapter extends BaseAdapter implements OnClickListener{
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListDataDonasi tempValues = null;
    int i=0;
    public CustomListDonasiAdapter(Activity a, ArrayList d, Resources resLocal){
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder{
        public TextView itemNud;
        public TextView itemNama;
        public TextView itemTanggal;
        public TextView itemNominal;

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
            vi = inflater.inflate(R.layout.custom_list, null);
            holder = new ViewHolder();
            holder.itemNud = (TextView)vi.findViewById(R.id.txt_item_nud_value);
            holder.itemNama = (TextView)vi.findViewById(R.id.txt_item_nama);
            holder.itemTanggal = (TextView)vi.findViewById(R.id.txt_item_tanggal);
            holder.itemNominal = (TextView)vi.findViewById(R.id.txt_item_nominal);
            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.itemNama.setText("Data Kosong");
        }else{
            tempValues=null;
            tempValues = (ListDataDonasi) data.get(position);
            holder.itemNud.setText(tempValues.getNud());
            holder.itemNama.setText(tempValues.getNama());
            holder.itemTanggal.setText(tempValues.getTanggal());
            holder.itemNominal.setText(tempValues.getNominal());
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }
    private class OnItemClickListener implements OnClickListener{
        private int mPosition;
        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            lihat_donasi sct = (lihat_donasi)activity;
            sct.onItemClick(mPosition);
        }
    }


}
