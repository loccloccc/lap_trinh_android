package com.example.bai3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ListView listView;

    String[] categories = {"Đồ dùng", "Linh kiện"};

    String[] doDung = {"Bút", "Thước", "Vở", "Tẩy"};
    String[] linhKien = {"Điện trở", "Tụ điện", "IC", "LED"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);

        // Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Xử lý chọn Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, doDung);
                } else {
                    adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, linhKien);
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Click item -> Detail
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = adapter.getItem(position);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("data", item);
            startActivity(intent);
        });
    }
}