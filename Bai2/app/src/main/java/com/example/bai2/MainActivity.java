package com.example.bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText username , password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username=(EditText)findViewById(R.id.editTextUserName);
        password=(EditText) findViewById(R.id.editTextPass);
        button = (Button) findViewById(R.id.LogIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1 = username.getText().toString();
                String pass = password.getText().toString();
                if (username1.equals("B24DTCN133") && pass.equals("B24DTCN133")){
                    Toast.makeText(MainActivity.this,"Log in complete" , Toast.LENGTH_SHORT).show();

                    Intent intent  = new Intent();
                    intent.setClass(MainActivity.this,Home.class);
                    intent.putExtra("user_name" , username1);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this,"Log in fail" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}