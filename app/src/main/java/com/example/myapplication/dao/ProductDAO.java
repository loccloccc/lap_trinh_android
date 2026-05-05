package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private SQLiteDatabase db;

    public ProductDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }


    public long insert(Product p) {
        ContentValues values = new ContentValues();
        values.put("name", p.getName());
        values.put("material", p.getMaterial());
        values.put("origin", p.getOrigin());
        values.put("price", p.getPrice());
        values.put("categoryId", p.getCategoryId());

        return db.insert("Product", null, values);
    }


    public int update(Product p) {
        ContentValues values = new ContentValues();
        values.put("name", p.getName());
        values.put("material", p.getMaterial());
        values.put("origin", p.getOrigin());
        values.put("price", p.getPrice());

        return db.update("Product", values,
                "id = ?", new String[]{String.valueOf(p.getId())});
    }


    public int delete(int id) {
        return db.delete("Product",
                "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Product> getByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Product WHERE categoryId = ?",
                new String[]{String.valueOf(categoryId)}
        );

        while (cursor.moveToNext()) {
            list.add(new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
            ));
        }

        cursor.close();
        return list;
    }


    public List<Product> searchByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Product WHERE categoryId = ? AND origin = ? AND price BETWEEN ? AND ?",
                new String[]{String.valueOf(categoryId), "Hà Nội", "50000", "150000"}
        );

        while (cursor.moveToNext()) {
            list.add(new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
            ));
        }

        cursor.close();
        return list;
    }
}