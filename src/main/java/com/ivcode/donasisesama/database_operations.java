package com.ivcode.donasisesama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Muhammad Irvan on 15/04/2016.
 */
public class database_operations extends SQLiteOpenHelper {
    private static final String database_name = "db_donasi";
    private static final String table_name1 = "tb_donasi";
    private static final String table_name2 = "tb_penyaluran";

    private static final String table_name1_field1 = "nud";
    private static final String table_name1_field2 = "donatur";
    private static final String table_name1_field3 = "tanggal";
    private static final String table_name1_field4 = "nominal";

    private static final String table_name2_field1 = "id_penyaluran";
    private static final String table_name2_field2 = "tujuan";
    private static final String table_name2_field3 = "nama";
    private static final String table_name2_field4 = "tanggal";
    private static final String table_name2_field5 = "nominal";


    public database_operations(Context context) {
        super(context, database_name, null, 1);
        try {
            SQLiteDatabase db = this.getWritableDatabase();
        }catch (Exception e){
            Toast.makeText(context, "Error : " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql1 = "CREATE TABLE " + table_name1 + " (" + table_name1_field1 + " TEXT PRIMARY KEY, " +
                table_name1_field2 + " TEXT, " + table_name1_field3 + " TEXT, " + table_name1_field4 + " TEXT);";
        final String sql2 = "CREATE TABLE " + table_name2 + " (" + table_name2_field1 + " TEXT PRIMARY KEY, " +
                table_name2_field2 + " TEXT, " + table_name2_field3 + " TEXT, " + table_name2_field4 + " TEXT, " +
                table_name2_field5 + " TEXT);";

        db.execSQL(sql1);
        db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name1);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);

        onCreate(db);
    }

    public boolean insert_data_donasi(String nud, String donatur, String tanggal, String nominal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(table_name1_field1, nud);
        cv.put(table_name1_field2, donatur);
        cv.put(table_name1_field3, tanggal);
        cv.put(table_name1_field4, nominal);

        long insert = db.insert(table_name1, null, cv);

        if(insert == -1)
            return false;
        else
            return true;
    }
    public boolean insert_data_penyaluran(String id, String tujuan, String nama, String tanggal, String nominal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(table_name2_field1, id);
        cv.put(table_name2_field2, tujuan);
        cv.put(table_name2_field3, nama);
        cv.put(table_name2_field4, tanggal);
        cv.put(table_name2_field5, nominal);

        long insert = db.insert(table_name2, null, cv);

        if(insert == -1)
            return false;
        else
            return true;
    }

    public Cursor get_data_pemasukan(){
        SQLiteDatabase db = this.getWritableDatabase();
        final String sql = "SELECT * FROM " + table_name1;
        Cursor intruksi = db.rawQuery(sql, null);

        return intruksi;
    }
    public Cursor get_data_pemasukan(String search){
        SQLiteDatabase db = this.getWritableDatabase();
        final String sql = "SELECT * FROM " + table_name1 + " WHERE " + table_name1_field1 + " LIKE '%" + search + "%'" +
                " or "+ table_name1_field2 + " LIKE '%" + search + "%'" +
                " or "+ table_name1_field3 + " LIKE '%" + search + "%'" +
                " or "+ table_name1_field4 + " LIKE '%" + search + "%'";
        Cursor intruksi = db.rawQuery(sql, null);

        return intruksi;
    }
    public Cursor get_data_penyaluran(){
        SQLiteDatabase db = this.getWritableDatabase();
        final String sql = "SELECT * FROM " + table_name2;
        Cursor intruksi = db.rawQuery(sql, null);

        return intruksi;
    }
    public Cursor get_data_penyaluran(String search){
        SQLiteDatabase db = this.getWritableDatabase();
        final String sql = "SELECT * FROM " + table_name2 + " WHERE " + table_name2_field1 + " LIKE '%" + search + "%'" +
                " or "+ table_name2_field2 + " LIKE '%" + search + "%'" +
                " or "+ table_name2_field3 + " LIKE '%" + search + "%'" +
                " or "+ table_name2_field4 + " LIKE '%" + search + "%'" +
                " or "+ table_name2_field5 + " LIKE '%" + search + "%'";
        Cursor intruksi = db.rawQuery(sql, null);

        return intruksi;
    }

    public boolean set_edit_donasi(String nud, String donatur, String tanggal, String nominal){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table_name1_field1, nud);
        cv.put(table_name1_field2, donatur);
        cv.put(table_name1_field3, tanggal);
        cv.put(table_name1_field4, nominal);

        db.update(table_name1, cv, table_name1_field1 + " = ?", new String[]{nud});

        return true;
    }
    public boolean set_edit_penyaluran(String id, String tujuan, String nama, String tanggal, String nominal){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table_name2_field1, id);
        cv.put(table_name2_field2, tujuan);
        cv.put(table_name2_field3, nama);
        cv.put(table_name2_field4, tanggal);
        cv.put(table_name2_field5, nominal);

        db.update(table_name2, cv, table_name2_field1 + " = ?", new String[]{id});

        return true;
    }
    public Integer delete_donasi(String nud){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(table_name1, table_name1_field1 + " = ?", new String[]{nud});
    }
    public Integer delete_penyaluran(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(table_name2, table_name2_field1 + " = ?", new String[]{id});
    }

}
