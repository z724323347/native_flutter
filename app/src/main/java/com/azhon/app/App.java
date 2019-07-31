package com.azhon.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import io.flutter.app.FlutterApplication;
import io.flutter.view.FlutterMain;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Flutter 插件初始化
        FlutterMain.startInitialization(this);

        initARouter();


    }

    /**
     *  ARouter 初始化
     */
    private void initARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();
        ARouter.openDebug();
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }
}
