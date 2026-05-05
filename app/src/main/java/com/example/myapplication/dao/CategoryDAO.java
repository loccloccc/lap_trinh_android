package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private SQLiteDatabase db;

    public CategoryDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }


    public long insert(Category c) {
        ContentValues values = new ContentValues();
        values.put("name", c.getName());
        return db.insert("Category", null, values);
    }


    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);

        if (cursor.moveToFirst()) {
            do {
                Category c = new Category(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
                list.add(c);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


    public int update(Category c) {
        ContentValues values = new ContentValues();
        values.put("name", c.getName());

        return db.update("Category", values,
                "id = ?", new String[]{String.valueOf(c.getId())});
    }


    public int delete(int id) {
        return db.delete("Category",
                "id = ?", new String[]{String.valueOf(id)});
    }
}