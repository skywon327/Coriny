package com.example.app_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_main);


        Button ii = (Button)findViewById(R.id.ii);
        ii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubActivity.this, MainActivity.class);//넘겨받을 화면은 this, 이동할 화면은 class
                startActivity(intent);
            }
        });
    }
}