package com.lsm.android_demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsm.android_demo.R;


public class PeopleAdapter extends ArrayAdapter<String> {
    private  int resourceid;

    public PeopleAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        resourceid = resource;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view;
//        String data = getItem(position);
//        if (convertView == null){
//            view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
//        }else {//使用已经创建的view
//            view =convertView;
//        }
//
//        ImageView imageView = view.findViewById(R.id.list_item_img_id);
//        TextView textView = view.findViewById(R.id.list_item_text_id);
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        textView.setText(data);
//        return  view;
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        String data = getItem(position);
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img_view = view.findViewById(R.id.list_item_img_id);
            viewHolder.text_view = view.findViewById(R.id.list_item_text_id);
            view.setTag(viewHolder);//save
        }else {//使用已经创建的view
            view =convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.img_view.setImageResource(R.mipmap.ic_launcher);
        viewHolder.text_view.setText(data);
        return  view;
    }

    /**
     * 防止每次都findbyid查找
     */
    class ViewHolder {
        ImageView img_view;
        TextView  text_view;
    }

}
