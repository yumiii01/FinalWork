package com.example.myapplication4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MyTask implements Runnable {
    private static final String TAG = "Net";
    private Handler handler;

    public MyTask(Handler handler) {
        this.handler = handler;
    }

    public void run() {
        Log.i(TAG, "run: 线程已启动");
        String ResStr = "";
        Bundle rebundle = new Bundle();
        ArrayList<String> relist = new ArrayList<>();
        try {
            Thread.sleep(5000);
            //Log.i(TAG, "run: ");
            //Thread.sleep(5000);
            //URL url = new URL("https://chl.cn/?jinri");
            //HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //InputStream in = http.getInputStream();
            //String html=inputStream2String(in);
            //Log.i(TAG, "run: html="+html);
            Document doc = Jsoup.connect("https://chl.cn/?jinri").get();
            Elements tables = doc.getElementsByTag("table");
            Element table1 = tables.first();
            Elements rows = table1.getElementsByTag("tr");
            rows.remove(0);
            for (Element row : rows) {

                Elements tds = row.getElementsByTag("td");
                Element td1 = tds.first();
                Element td2 = tds.get(4);
                String str1 = td1.text().trim();
                String str2 = td2.text().trim();
                Log.i(TAG, "run: 币种：" + td1.text() + "  价格:" + td2.text());
                //Log.i(TAG, "run: 币种2："+td1.text()+"价格2:"+td2.text());
                ResStr += ("run: 币种：" + td1.text() + "  价格:" + td2.text() + "\n");
                relist.add(str1 + " ==> " + str2);
                if (str1.contains("美元"))
                {
                    rebundle.putFloat("dollar", 100 / Float.parseFloat(str2));
                }
                else if (str1.contains("欧元"))
                {

                    rebundle.putFloat("euro", 100 / Float.parseFloat(str2));
                }
                else if (str1.contains("韩国元"))
                {
                    rebundle.putFloat("won", 100 / Float.parseFloat(str2));
                }
            }
        }
        catch (IOException ex) {

            throw new RuntimeException(ex);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        rebundle.putString("html", ResStr);
        rebundle.putStringArrayList("mylist", relist);
        Message msg = handler.obtainMessage();
        msg.what = 7;
        msg.obj = rebundle;
        handler.sendMessage(msg);


    }
}
