package com.example.myapplication4;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyListActivity extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private ArrayList<HashMap<String,String>> listitem;
    private SimpleAdapter listItemAdapter;
    private static final String TAG = "MyListActivity";

    private Handler handler;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==9){
                    Log.i(TAG, "handleMessage: "+ msg.obj);
                    listitem = (ArrayList<HashMap<String,String>>)msg.obj;
                    Log.i(TAG, "handleMessage: "+listitem.size());
                    adapter=new MyAdapter(MyListActivity.this,R.layout.list_item,listitem);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        initListView();
        setListAdapter(listItemAdapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);



        Thread t = new Thread(()->{
            ArrayList<HashMap<String,String>> retlist = new ArrayList<HashMap<String,String>>();
            try {
                Log.i(TAG, "run: working");
                Document doc = Jsoup.connect("https://chl.cn/?jinri").get();
                Elements tables =doc.getElementsByTag("table") ;
                Element table1=tables.first();
                Elements rows = table1.getElementsByTag("tr");
                rows.remove(0);
                for(Element row : rows){
                    Elements tds = row.getElementsByTag("td");
                    Element td1 = tds.first();
                    Element td2= tds.get(4);
                    Log.i(TAG, "run: 币种："+td1.text()+"  价格:"+td2.text());
                    String str1 = td1.text().trim();
                    String str2 = td2.text().trim();
                    HashMap<String,String>map = new HashMap<String,String>();
                    map.put("ItemTitle", str1);
                    map.put("Price", str2);
                    retlist.add(map);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Message msg = handler.obtainMessage(9,retlist);
            handler.sendMessage(msg);
        });
        t.start();
    }

    private void initListView(){
        listitem = new ArrayList<HashMap<String, String>>();
        for (int i = 0;i <  10;i++){
            HashMap<String,String>map = new HashMap<String,String>();
            map.put("ItemTitle", "Rate: " + i);
            map.put("ItemDetail", "Detail: "  + i);
            listitem.add(map);
        }

        listItemAdapter = new SimpleAdapter(this,
                listitem,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String,String> map=(HashMap<String, String>)getListView().getItemAtPosition(i);
        String title=map.get("ItemTitle");
        String Price=map.get("Price");
        Log.i(TAG, "onItemClick: "+title);
        Log.i(TAG, "onItemClick: "+Price);
        Intent opencountActivity = new Intent(this,CountActivity.class);
        opencountActivity.putExtra("title",title);
        opencountActivity.putExtra("Price",Price);
        startActivityForResult(opencountActivity,5);

        //删除单击选用的数据项
        // adapter.remove(map);

    }
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        Log.i(TAG, "onItemLongClick: 长按");
        HashMap<String,String> map=(HashMap<String, String>)getListView().getItemAtPosition(i);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("你真的要删除ta吗？").setPositiveButton("是的~",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Log.i(TAG, "onClick: 对话框事件处理");
                adapter.remove(map);
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
    }


