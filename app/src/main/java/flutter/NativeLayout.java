package flutter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhon.app.R;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class NativeLayout implements PlatformView, MethodChannel.MethodCallHandler {

    private final View nativeLayout;
    private TextView textView;
    String nString = "我是来自Android的原生 layout";
    String myContent;

    public NativeLayout(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_in_flutter, null,true);
        textView = view.findViewById(R.id.tv_layoyt_flutter);
        textView.setText(" 来自  NativeLayout，布局文件中的原生 textview");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,10,10,10);
        view.setLayoutParams(lp);
        this.nativeLayout = view;

        //该方法有一点需要注意的是，原生组件初始化的参数并不会随着setState重复赋值，也就是说这种是init参数。
        if (params.containsKey("layout")) {
            myContent = (String) params.get("layout");
            textView.setText(nString + "\n flutter端对象 :" +myContent);
        }
        //如何更改已经实例化的原生组件的状态，可以通过MethodCall来实现
        MethodChannel channel = new MethodChannel(messenger,"plugins.nightfarmer.top/nativelayout_" +id);
        channel.setMethodCallHandler(this);
    }
    @Override
    public View getView() {
        return nativeLayout;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        if ("setLayoutText".equals(methodCall.method)) {
            String text = (String) methodCall.arguments;
            textView.setText(nString + "\n init Flutter:\n"+ myContent +"\nonMethodCall实现:\n"  +text);
            result.success(null);
        }
    }
}
