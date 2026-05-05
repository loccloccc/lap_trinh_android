package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.dao.CategoryDAO;
import com.example.myapplication.dao.ProductDAO;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    Button btnAdd;
    CategoryDAO dao;
    List<Category> list;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCategoryScreen();
    }


    public void showCategoryScreen() {
        setContentView(R.layout.activity_category);

        listView = findViewById(R.id.listCategory);
        btnAdd = findViewById(R.id.btnAddCategory);

        dao = new CategoryDAO(this);
        loadCategory();

        btnAdd.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thêm danh mục");

            View view = getLayoutInflater().inflate(R.layout.dialog_category, null);
            EditText edtName = view.findViewById(R.id.edtName);

            builder.setView(view);
            builder.setPositiveButton("Thêm", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                String name = edtName.getText().toString().trim();

                if (name.isEmpty()) {
                    edtName.setError("Không được để trống");
                    return;
                }

                dao.insert(new Category(name));
                loadCategory();
                dialog.dismiss();
            });
        });
    }

    private void loadCategory() {
        list = dao.getAll();
        adapter = new CategoryAdapter(this, list);
        listView.setAdapter(adapter);
    }


    public void showProductScreen(int categoryId) {
        setContentView(R.layout.activity_product);

        ListView listView = findViewById(R.id.listProduct);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnSearch = findViewById(R.id.btnSearch);

        ProductDAO productDAO = new ProductDAO(this);


        List<Product> currentList = productDAO.getByCategory(categoryId);
        ProductAdapter adapter = new ProductAdapter(this, currentList);
        listView.setAdapter(adapter);


        btnBack.setOnClickListener(v -> showCategoryScreen());

        // ================= ADD PRODUCT =================
        btnAddProduct.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thêm sản phẩm");

            View view = getLayoutInflater().inflate(R.layout.dialog_product, null);

            EditText edtName = view.findViewById(R.id.edtName);
            EditText edtMaterial = view.findViewById(R.id.edtMaterial);
            EditText edtOrigin = view.findViewById(R.id.edtOrigin);
            EditText edtPrice = view.findViewById(R.id.edtPrice);

            builder.setView(view);
            builder.setPositiveButton("Thêm", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {

                String name = edtName.getText().toString().trim();
                String material = edtMaterial.getText().toString().trim();
                String origin = edtOrigin.getText().toString().trim();
                String priceStr = edtPrice.getText().toString().trim();

                if (name.isEmpty()) {
                    edtName.setError("Không được để trống");
                    return;
                }
                if (material.isEmpty()) {
                    edtMaterial.setError("Không được để trống");
                    return;
                }
                if (origin.isEmpty()) {
                    edtOrigin.setError("Không được để trống");
                    return;
                }
                if (priceStr.isEmpty()) {
                    edtPrice.setError("Không được để trống");
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceStr);
                } catch (Exception e) {
                    edtPrice.setError("Giá phải là số");
                    return;
                }

                productDAO.insert(new Product(
                        name, material, origin, price, categoryId
                ));

                currentList.clear();
                currentList.addAll(productDAO.getByCategory(categoryId));
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            });
        });


        btnSearch.setOnClickListener(v -> {

            // 🔥 FIX: dùng searchByCategory
            currentList.clear();
            currentList.addAll(productDAO.searchByCategory(categoryId));

            adapter.notifyDataSetChanged();
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {
            Product p = currentList.get(position);

            Toast.makeText(this,
                    "Tên: " + p.getName() +
                            "\nGiá: " + p.getPrice(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}