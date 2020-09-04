package suriyon.cs.ubru.contactnavbottom.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.contactnavbottom.model.Contact;

public class ContactDAO extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "contact_db";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "contact";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_MOBILE = "mobile";
    private Context context;
    public ContactDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_MOBILE + " TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    public boolean insert(Contact contact){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("mobile", contact.getMobile());
        long row = db.insert(TABLE_NAME, null, values);
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean update(Contact contact){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("mobile", contact.getMobile());
        int row = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean delete(int id){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int row = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }
    public List<Contact> select(){
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);

                contacts.add(new Contact(id, name, mobile));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }
    public List<Contact> selectByName(String contactName){
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " LIKE ?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + contactName + "%"});
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);

                contacts.add(new Contact(id, name, mobile));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }
    public Contact selectById(int id){
        Contact contact = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String mobile = cursor.getString(2);

            contact = new Contact(id, name, mobile);
        }
        cursor.close();
        db.close();
        return contact;
    }
}
