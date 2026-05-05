package com.example.helloword;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
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



    // khai bao account , pass , button
    EditText editTextAccount , editTextPass;
    Button LoggIn;

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

        // Mappting giua java va XML
        editTextAccount=(EditText) findViewById(R.id.editAccount);
        editTextPass = (EditText) findViewById(R.id.editPass);
        LoggIn = (Button) findViewById(R.id.button);
        LoggIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextAccount.getText().toString();
                String pass = editTextPass.getText().toString();

                if (userName.equals("admin")&&pass.equals("12345")){
                    Toast.makeText(MainActivity.this,"log in thanh cong" , Toast.LENGTH_SHORT).show();
                    // khai niem intel : nhiem vu dua acti nay sang acti khac
                    Intent intent   = new Intent();
                    intent.setClass(MainActivity.this, wellcome.class);
                    intent.putExtra("user_name",userName);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this,"log in that bai" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}