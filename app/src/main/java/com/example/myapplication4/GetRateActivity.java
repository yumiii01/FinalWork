package com.example.myapplication4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GetRateActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_rate);
        GridView listView = findViewById(R.id.gridview);
        ProgressBar bar = findViewById(R.id.progressBar);
        // 准备数据项
        List<String> mylist = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++) {
            //mylist.add("Item " + i);
        }

        ListAdapter adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1,mylist);


        //setView.setEmptyView(findViewById(R.id.nodata));
        handler = new Handler(Looper.myLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg)
            {
                if(msg.what == 7)
                {
                    Bundle bundle = (Bundle) msg.obj;
                    ArrayList<String> list2 = bundle.getStringArrayList("mylist");
                    ListAdapter adapter = new ArrayAdapter<String>(GetRateActivity.this, android.R.layout.simple_list_item_1,list2);
                    listView.setAdapter(adapter);

                    //重新设置可见与不可见，gone不占用任何屏幕空间，invisible仍占用空间
                    listView.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(new MyTask(handler));
        t.start();
    }
}
