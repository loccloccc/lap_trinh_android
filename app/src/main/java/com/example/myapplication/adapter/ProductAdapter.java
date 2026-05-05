package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.*;
import android.widget.*;

import com.example.myapplication.R;
import com.example.myapplication.dao.ProductDAO;
import com.example.myapplication.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    Activity activity;
    List<Product> list;
    ProductDAO dao;

    public ProductAdapter(Activity activity, List<Product> list) {
        this.activity = activity;
        this.list = list;
        this.dao = new ProductDAO(activity);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    static class ViewHolder {
        TextView txtName;
        Button btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.item_product, parent, false);

            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product p = list.get(position);
        holder.txtName.setText(p.getName() + " - " + p.getPrice());


        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Xóa sản phẩm?")
                    .setPositiveButton("OK", (d, w) -> {
                        dao.delete(p.getId());
                        list.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });


        holder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Sửa sản phẩm");

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_product, null);

            EditText edtName = view.findViewById(R.id.edtName);
            EditText edtMaterial = view.findViewById(R.id.edtMaterial);
            EditText edtOrigin = view.findViewById(R.id.edtOrigin);
            EditText edtPrice = view.findViewById(R.id.edtPrice);


            edtName.setText(p.getName());
            edtMaterial.setText(p.getMaterial());
            edtOrigin.setText(p.getOrigin());
            edtPrice.setText(String.valueOf(p.getPrice()));

            builder.setView(view);
            builder.setPositiveButton("Lưu", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {

                String name = edtName.getText().toString().trim();
                String material = edtMaterial.getText().toString().trim();
                String origin = edtOrigin.getText().toString().trim();
                String priceStr = edtPrice.getText().toString().trim();

                // VALIDATE
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


                p.setName(name);
                p.setMaterial(material);
                p.setOrigin(origin);
                p.setPrice(price);

                dao.update(p);
                notifyDataSetChanged();
                dialog.dismiss();
            });
        });

        return convertView;
    }
}