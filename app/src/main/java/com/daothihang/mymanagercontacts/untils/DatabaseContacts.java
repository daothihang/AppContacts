package com.daothihang.mymanagercontacts.untils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;

import com.daothihang.mymanagercontacts.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseContacts {
    private static final String PATH = Environment.getDataDirectory().getPath()
            + "/data/com.daothihang.mymanagercontacts/databases/";

    private static final String DB_NAME = "DataContacts.db";

    private static final String TABLE_USERS = "tbl_users";

    private static final String ID_USER = "id_user";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String AVARTAR = "avartar";

    private Context context;
    private SQLiteDatabase database;

    public DatabaseContacts(Context context) {
        this.context = context;
        copyFileToDevice();
        File f = new File(PATH);
        if (f.exists()) {
            f.delete();
        } else {
            f.delete();
        }
    }

    private void copyFileToDevice() {
        File file = new File(PATH + DB_NAME);
        if (!file.exists()) {
            File parent = file.getParentFile();
            parent.mkdirs();
            try {
                InputStream inputStream = context.getAssets().open(DB_NAME);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {
        database.close();
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> arrNhom = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.query(TABLE_USERS, null, null, null, null, null, null);
        cursor.moveToFirst();

        int indexID = cursor.getColumnIndex(ID_USER);
        int indexName = cursor.getColumnIndex(NAME);
        int indexPhone = cursor.getColumnIndex(PHONE);
        int indexAddress = cursor.getColumnIndex(ADDRESS);
        int indexAvartar = cursor.getColumnIndex(AVARTAR);

        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indexID);
            String name = cursor.getString(indexName);
            String phone = cursor.getString(indexPhone);
            String address = cursor.getString(indexAddress);
            String avartar = cursor.getString(indexAvartar);
            arrNhom.add(new User(id, name, phone, address, avartar));
            cursor.moveToNext();

        }
        closeDatabase();

        return arrNhom;
    }

    public void insertUsers(String name, String phone, String address, String avartar) {
        openDatabase();
        database.execSQL("INSERT INTO "
                + TABLE_USERS
                + "(" + NAME + "," + PHONE + "," + ADDRESS + "," + AVARTAR + ")"
                + " VALUES ('" + name + "','" + phone + "','" + address + "','" + avartar + "');");
        closeDatabase();
    }

    public void deleteUsers(String id) {
        openDatabase();
        database.execSQL("DELETE FROM "
                + TABLE_USERS
                + " WHERE " + ID_USER + "='" + id + "';");
        closeDatabase();
    }

    public void updateUsers(String name, String phone, String address, String avartar, String id) {
        openDatabase();
        database.execSQL("UPDATE "
                + TABLE_USERS + " SET " + NAME + "='" + name + "'," + PHONE + "='" + phone + "'," + ADDRESS + "='" + address + "'," + AVARTAR + "='" + avartar + "'  WHERE " + ID_USER + "='" + id + "';");
        closeDatabase();
    }

}
