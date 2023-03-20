package com.example.myapplication4;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

 public class SorceActivity extends AppCompatActivity {
    TextView countA,countB;

    int cB=0; int cA=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorce);
        countA=findViewById(R.id.scoreA);
        countB=findViewById(R.id.scoreB);
    }
    public void A(View btnA){
        if(btnA.getId()==R.id.btn_A3){
            cA+=3;
        } else if (btnA.getId()==R.id.btn_A2) {
            cA+=2;
        }else cA+=1;
        countA.setText(String.valueOf(cA));
    }
    public void B(View btnB){
        if(btnB.getId()==R.id.btn_B3){
            cB+=3;
        } else if (btnB.getId()==R.id.btn_B2) {
            cB+=2;
        }else cB+=1;
        countB.setText(String.valueOf(cB));}
    public void reset(View r){
        cA=0;
        cB=0;
        countA.setText("0");
        countB.setText("0");
    }
}
