package arouterdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.azhon.app.MainActivity;

@Interceptor(priority = 7)
public class TestFlutterInterceptor implements IInterceptor {
    Context mContext;
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (ARouterConfig.activity.APP_ACTIVITY_FLUTTER.equals(postcard.getPath())){
            // 这里的弹窗仅做举例，代码写法不具有可参考价值
            final AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.getThis());
            ab.setCancelable(false);
            ab.setTitle("温馨提醒");
            ab.setMessage("想要跳转到FlutterActivity么？(触发了\"/inter/flutter\"拦截器，拦截了本次跳转)");
            ab.setNegativeButton("继续", (dialog, which) -> callback.onContinue(postcard));
            ab.setNeutralButton("算了", (dialog, which) -> callback.onInterrupt(null));
            ab.setPositiveButton("加参数", (dialog, which) -> {
                postcard.withString("extra", "我是在拦截器中附加的参数");
                callback.onContinue(postcard);
            });

            MainLooper.runOnUiThread(() -> ab.create().show());
        } else if (ARouterConfig.activity.APP_ACTIVITY_INTERCEPTOR.equals(postcard.getPath())){
            // 这里的弹窗仅做举例，代码写法不具有可参考价值
            final AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.getThis());
            ab.setCancelable(false);
            ab.setTitle("温馨提醒");
            ab.setMessage("想要跳转到下一个Activity么？(触发了\"/inter/activity\"拦截器，拦截了本次跳转)");
            ab.setNegativeButton("继续", (dialog, which) -> callback.onContinue(postcard));
            ab.setNeutralButton("算了", (dialog, which) -> callback.onInterrupt(null));
            ab.setPositiveButton("加参数", (dialog, which) -> {
                postcard.withString("extra", "我是在拦截器中附加的参数");
                callback.onContinue(postcard);
            });

            MainLooper.runOnUiThread(() -> ab.create().show());
        }
        else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        mContext = context;
        Log.e("testService", TestFlutterInterceptor.class.getName() + " has init.");
    }
}
