package aac;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.util.Log;

public class AacLiveData extends LiveData implements LifecycleObserver {

    private  static  final String TAG = "AacLiveData";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.e(TAG, "onActive");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.e(TAG, "onInactive");
    }

    @Override
    protected void setValue(Object value) {
        super.setValue(value);
        Log.e(TAG, "setValue");
    }
}
