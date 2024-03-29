package com.azhon.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.text.SimpleDateFormat;
import java.util.Date;

import arouterdemo.ARouterConfig;
import flutter.NativeLayoutFlutterPlugin;
import flutter.NativeViewFlutterPlugin;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

@Route(path = ARouterConfig.activity.APP_ACTIVITY_FLUTTER)
public class ToFlutterActivity extends FlutterActivity {

    private TextView time;

    private static String CHANNEL_NAVITE = "io/native.channel.method";

    private String extra;
    private String methodObj;
    private View layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        NativeViewFlutterPlugin.registerWith(this);
        NativeLayoutFlutterPlugin.registerWith(this);
        ViewGroup view =(ViewGroup)findViewById(android.R.id.content);


//        TextView child = new TextView(this);
//        child.setTextSize(20);
//        child.setTextColor(getResources().getColor(R.color.colorAccent));
//        // 获取当前的时间并转换为时间戳格式, 并设置给TextView
//        String currentTime = dateToStamp(System.currentTimeMillis());
//        child.setText(currentTime);
//        child.setPadding(0,300,0,0);
//        view.addView(child);

//        LinearLayout layout = new LinearLayout(this);
//        layout.setPadding(20,300,20,0);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        layout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        view.addView(layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.activity_in_flutter,null);
        time = layout.findViewById(R.id.tv_time);
//        time.setText(dateToStamp(System.currentTimeMillis()));
        new TimeThread().start();
        this.addContentView(layout,lp);

        extra = getIntent().getStringExtra("extra");
        if (extra == ""){
            extra = "没有参数";
        }

        Toast.makeText(this,extra,Toast.LENGTH_SHORT).show();

        new MethodChannel(getFlutterView(),CHANNEL_NAVITE).setMethodCallHandler((methodCall, result) -> {
//            Log.d("TAG",methodCall.arguments.toString());
            if (null != methodCall.arguments){
               methodObj = methodCall.arguments.toString();
            }
            Toast.makeText(this,methodObj,Toast.LENGTH_LONG).show();

            switch (methodCall.method){
                case "finish":
                    Intent intent = new Intent();
                    intent.putExtra("key", methodCall.arguments.toString());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                    result.success("ok");
                    break;

                case "init":
                    result.success(extra);
                    break;

                case "show":
                    Log.d("---------" ,"================");
                    layout.setVisibility(View.GONE);
                    break;

                default:
                    result.notImplemented();
                    break;
            }
//            if (methodCall.method.equals("finish")){
//
//                Intent intent = new Intent();
//                intent.putExtra("key", methodCall.arguments.toString());
//                setResult(Activity.RESULT_OK,intent);
//                finish();
//                result.success("ok");
//            }else if (methodCall.method.equals("init")){
//                result.success(extra);
//            }
//            else {
//                result.notImplemented();
//            }
        });
    }




    /**
     * 将时间戳转换为时间
     */
    public String dateToStamp(long s) {
        String res;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(s);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
        return res;
    }

    class TimeThread extends Thread{
        @Override
        public void run() {
           do {
               try {
                   Thread.sleep(1000);
                   Message msg = new Message();
                   msg.what = 1;
                   mHandler.sendMessage(msg);
               } catch (Exception e){
                   e.printStackTrace();
               }
           } while (true);
        }
    }

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);//时间显示格式
                    time.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };

}
