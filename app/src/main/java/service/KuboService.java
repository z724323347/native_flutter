package service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.azhon.app.R;


public class KuboService extends Service {

    private static final String TAG = "KuboService";
    public static final int NOTICE_ID = 100;
    public static final String CHANNEL_ID = "KuboService";
    public static final String CHANNEL_NAME = "KuboService_Name";

//    public class MyBinder extends Binder {
//        public KuboService getService() {
//            return KuboService.this;
//        }
//    }
//
//    private MyBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"KuboService---->binder");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"KuboService---->onCreate被调用，启动前台service");
        //如果API大于18，需要弹出一个可见通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.bg_button);
            builder.setContentTitle("KuboService");
            builder.setContentText("KuboService is runing...");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                //修改安卓8.1以上系统报错
                 NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                         NotificationManager.IMPORTANCE_MIN);
                 notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
                 notificationChannel.setShowBadge(false);//是否显示角标
                 notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
                 NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                 manager.createNotificationChannel(notificationChannel);
                 builder.setChannelId(CHANNEL_ID);
            }

            startForeground(NOTICE_ID,builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this,CancelNoticeService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //android8.0以上通过startForegroundService启动service
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
        else {
            startForeground(NOTICE_ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止，当资源允许情况下，重启service
        // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 如果Service被杀死，干掉通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(NOTICE_ID);
        }
        Log.d(TAG,"KuboService---->onDestroy，前台service被杀死");
        // 重启自己
        Intent intent = new Intent(this,KuboService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        Log.d(TAG,"KuboService---->onDestroy，前台service--重启自己");
    }
}
