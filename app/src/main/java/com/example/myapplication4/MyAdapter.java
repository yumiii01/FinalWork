package com.example.myapplication4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter{
    public MyAdapter(@NonNull Context context, int resoure, @NonNull ArrayList<HashMap<String,String>> list){
        super(context,resoure,list);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View converView, @NonNull ViewGroup parent){
        View itemView = converView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView price = (TextView) itemView.findViewById(R.id.itemDetail);

        title.setText("币种："+ map.get("ItemTitle"));
        price.setText("价格："+map.get("Price"));
        return  itemView;
    }




}

