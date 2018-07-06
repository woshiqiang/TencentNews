package com.android.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<News> listNews;

    public MyAdapter(Context mContext, ArrayList<News> list) {
        this.mContext = mContext;
        this.listNews = list;
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_main_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(listNews.get(position).title);

        holder.tv_description.setText(listNews.get(position).description);
        holder.tv_date.setText(listNews.get(position).pubDate);

        holder.tv_link.setText(Html.fromHtml("<u>"+listNews.get(position).link+"</u>"));
        holder.tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(listNews.get(position).link);
                intent.setData(content_url);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder {

        public TextView tv_title;
        public TextView tv_link;
        public TextView author;
        public TextView tv_date;
        public TextView tv_description;

        public ViewHolder(View v) {
            this.tv_title = v.findViewById(R.id.tv_title);
            this.tv_description = v.findViewById(R.id.tv_description);
            this.tv_date = v.findViewById(R.id.tv_date);
            this.tv_link = v.findViewById(R.id.tv_link);
        }


    }
}