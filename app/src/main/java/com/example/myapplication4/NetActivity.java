package com.example.myapplication4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;

public class NetActivity extends AppCompatActivity implements Runnable {

    private static final String TAG="Net";
    Handler handler;
    //选Android那个
    TextView show;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1);
        show = findViewById(R.id.htlm_text);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    String str = (String)msg.obj;
                    Log.i(TAG, "handleMessage: 收到srt="+str);//日志文件
                    Toast.makeText(NetActivity.this, "str", Toast.LENGTH_SHORT).show();
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };

        Thread t2=new Thread((new Runnable() {
            @Override
            public void run() {
            }
        }));
        t2.start();
        Thread t3=new Thread(()->{
        });
        t3.start();
    }
    public void onclick(View htlm_out){
        Log.i(TAG, "onclick: ");
        Thread t = new Thread(this);
        Log.i(TAG, "onCreate: 启动子线程");
        t.start();
    }
    public void run(){
        Log.i(TAG, "线程已运行");
        //可以访问网络
        String ResStr ="";
        try{
            Log.i(TAG, "正在工作......");// 延时，模拟操作耗时
            //Thread.sleep(5000);
            //URL url = new URL("https://chl.cn/?jinri");
            //HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //InputStream in = http.getInputStream();

            //String html=inputStream2String(in);
            //Log.i(TAG, "run: html="+html);
            Document doc = Jsoup.connect("https://chl.cn/?jinri").get();
            Log.i(TAG,"title="+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.first();
            //Log.i(TAG,"run:table="+table1);
            //查找行tr
            Elements rows = table1.getElementsByTag("tr");
            for(Element row: rows){
                Elements tds = row.getElementsByTag("td");
                Element td1 = tds.first();
                Element td2= tds.get(4);
                Log.i(TAG, "run: 币种："+td1.text()+"  价格:"+td2.text());
                //Log.i(TAG, "run: 币种2："+td1.text()+"价格2:"+td2.text());
                ResStr+=("run: 币种："+td1.text()+"  价格:"+td2.text()+"\n");                //拆分单元格td
            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();//发送数据给主线程
        msg.what=7;
        msg.obj=ResStr;
        handler.sendMessage(msg);
    }
    private String inputStream2String(InputStream inputStream)
        throws IOException{
    final int bufferSize=1024;
    final char[] buffer = new char[bufferSize];
    final StringBuffer out = new StringBuffer();
    Reader in = new InputStreamReader(inputStream,"gb2312");
         while(true){
             int rsz = in.read(buffer,0,buffer.length);
             if(rsz<0)
                 break;
             out.append(buffer,0,rsz);
             }
             return out.toString();
    }
}