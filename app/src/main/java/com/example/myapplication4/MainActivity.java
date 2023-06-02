package com.example.myapplication4;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView out;
    EditText x;
    EditText y;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = findViewById(R.id.textView);
        out.setText("BMI是");
        x = findViewById(R.id.editTextTextPersonName);
        y = findViewById(R.id.editTextTextPersonName2);
        Log.i("tag", "message");
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }
    public void onClick(View V){
        Log.i("main","onClick msg....");
        String str1 = x.getText().toString();
        String str2 = y.getText().toString();
        double d1 = Double.parseDouble(str1);
        double d2 = Double.parseDouble(str2);
        double a1 = d1/(d2*d2);
        String a2 = String.format("%.2f",a1);
        double a3 = Double.parseDouble(a2);
        if (a3<=18.5)
            out.setText("您的BMI是："+a3+"您的体重偏低，请注意健康饮食。");
        else if(a3>=23.9)
            out.setText("您的BMI是："+a3+"您的体重偏高，请注意加强运动。");
        else
            out.setText("您的BMI是："+a3+"您的体重正常，请继续保持。");
    }
}
