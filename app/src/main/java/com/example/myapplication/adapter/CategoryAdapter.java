package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.CategoryDAO;
import com.example.myapplication.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    Activity activity;
    List<Category> list;
    CategoryDAO dao;

    public CategoryAdapter(Activity activity, List<Category> list) {
        this.activity = activity;
        this.list = list;
        this.dao = new CategoryDAO(activity);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    static class ViewHolder {
        TextView txtName;
        Button btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.item_category, parent, false);

            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category c = list.get(position);
        holder.txtName.setText(c.getName());


        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn xóa?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dao.delete(c.getId());
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity, "Đã xóa", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });


        holder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Sửa danh mục");

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_category, null);

            EditText edtName = view.findViewById(R.id.edtName);
            edtName.setText(c.getName());

            builder.setView(view);
            builder.setPositiveButton("Lưu", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                String name = edtName.getText().toString().trim();

                if (name.isEmpty()) {
                    edtName.setError("Không được để trống");
                    return;
                }

                c.setName(name);
                dao.update(c);
                notifyDataSetChanged();
                dialog.dismiss();
            });
        });


        convertView.setOnClickListener(v -> {
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).showProductScreen(c.getId());
            }
        });

        return convertView;
    }
}