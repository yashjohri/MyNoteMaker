package com.example.mynotemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="Notes.db";
    private static final String TABLE_NAME="Note_Table";
    private static final String COL_1="ID";
    private static final String COL_2="NAME";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public void insert(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, name);

        //db.delete(TABLE_NAME, "NAME=?", new String[] {name});
        db.insert(TABLE_NAME, null, values);
    }

    public void update(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, name);

        int x=db.update(TABLE_NAME, values, "NAME=?", new String[] {name});
        if(x==0){
            insert(name);
        }
    }

    public void delete(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME=?", new String[] {name});
    }

    public ArrayList<String> getList(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("Select * from "+TABLE_NAME, null);

        ArrayList<String> l=new ArrayList<>();
        while(c.moveToNext()){
            l.add( c.getString(1) );
        }
        return l;
    }
}
