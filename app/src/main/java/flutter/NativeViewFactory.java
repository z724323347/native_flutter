package flutter;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class NativeViewFactory extends PlatformViewFactory {
    private final BinaryMessenger messenger;

    public NativeViewFactory(BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }

    @Override
    public PlatformView create(Context context,int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new NativeView(context, messenger, id, params);
    }
}
