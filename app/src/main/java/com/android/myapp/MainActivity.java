package com.android.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.hbck.myapp.XmlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listView.setAdapter(new MyAdapter(MainActivity.this, (ArrayList<News>) msg.obj));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("MainActivity", "start---------");
            try {
                //先创建出了一个URL对象，urlPath：是我们访问接口地址
                URL url = new URL("http://news.qq.com/newsgn/rss_newsgn.xml");

                //URL链接对象，通过URL对象打开一个connection链接对像
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //设置urlConnection对象链接超时
                urlConnection.setConnectTimeout(5000);
                //设置urlConnection对象获取数据超时
                urlConnection.setReadTimeout(5000);
                //设置本次urlConnection请求方式
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Charsert", "gb2312"); //设置请求编码
                //调用urlConnection的链接方法，线程等待，等待的是服务器所给我们返回的结果集
                urlConnection.connect();
                //获取本次网络请求的状态码
                int code = urlConnection.getResponseCode();
                //如果本次返回的状态吗是200（成功）
                if (code == 200) {
                    //调用urlConnection.getInputStream得到本次请求所返回的结果流
                    InputStream inputStream = urlConnection.getInputStream();
                    ArrayList<String> list= new ArrayList<>();
                    list.add("title");
                    list.add("link");
                    list.add("author");
                    list.add("pubDate");
                    list.add("description");
                    List<Object> item = XmlUtil.parse(inputStream, News.class, list, list, "item");
                    Log.d("MainActivity", "item:" + item);
                    Message msg = new Message();
                    msg.what=1;
                    msg.obj = item;
                    handler.sendMessage(msg);
                    inputStream.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    };

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }



}
