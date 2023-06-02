package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CountActivity extends AppCompatActivity {
    private static final String TAG = "Count";
    private TextView T;
    private EditText count;
    float theprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        Intent intent = getIntent();
        String title=intent.getStringExtra("title");
        String price=intent.getStringExtra("Price");
        Log.i(TAG, "onCreate: title"+title);
        Log.i(TAG, "onCreate: p"+price);
        T=findViewById(R.id.theTitle);
        theprice=Float.parseFloat(price);
        count=findViewById(R.id.ratecount);
        T.setText(title);

    }
    public void doit(View btn){
        try {String rmb=count.getText().toString();
            float RMB=Float.parseFloat(rmb);
            float result=RMB*theprice;
            T.setText(String.valueOf(result));} catch (NumberFormatException ex) {
            Toast.makeText(this, "请输入正确金额后重试", Toast.LENGTH_SHORT).show();;
        }

    }
}

