package flutter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhon.app.R;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class NativeView implements PlatformView, MethodChannel.MethodCallHandler {

    private final TextView nativeTextView;
    String nString = "我是来自Android的原生button";
    String myContent;

    @SuppressLint("ResourceAsColor")
    public NativeView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        Button myNativeView = new Button(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,10,10,10);
        myNativeView.setText(nString);
        myNativeView.setAllCaps(false);
//        myNativeView.setPadding(10,10,10,10);
        myNativeView.setBackgroundResource(R.drawable.ic_dialog);
        myNativeView.setLayoutParams(lp);
        this.nativeTextView = myNativeView;

        //该方法有一点需要注意的是，原生组件初始化的参数并不会随着setState重复赋值，也就是说这种是init参数。
        if (params.containsKey("myContent")) {
            myContent = (String) params.get("myContent");
            myNativeView.setText(nString + "\n flutter端对象 :" +myContent);
        }

        //如何更改已经实例化的原生组件的状态，可以通过MethodCall来实现
        MethodChannel channel = new MethodChannel(messenger,"plugins.nightfarmer.top/nativeview_" +id);
        channel.setMethodCallHandler(this);

    }

    @Override
    public View getView() {
        return nativeTextView;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        if ("setText".equals(methodCall.method)) {
            String text = (String) methodCall.arguments;
            nativeTextView.setText(nString + "\n init Flutter:\n"+ myContent +"\nonMethodCall实现:\n"  +text);
            result.success(null);
        }
    }
}
