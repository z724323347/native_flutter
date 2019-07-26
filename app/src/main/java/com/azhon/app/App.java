package com.azhon.app;

import android.app.Application;

import io.flutter.app.FlutterApplication;
import io.flutter.view.FlutterMain;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlutterMain.startInitialization(this);
    }
}
