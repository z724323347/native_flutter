package aac;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;

import com.azhon.app.R;

public class AacActivity extends Activity implements LifecycleRegistryOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aac_activity_layout);

        AACLifecycleObserver aacTv = findViewById(R.id.aacTv);
        aacTv.setLifecycle(getLifecycle());
        getLifecycle().addObserver(aacTv);
        aacTv.setLifeCycleEnable(true);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
