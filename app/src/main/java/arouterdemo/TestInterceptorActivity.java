package arouterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.azhon.app.R;

@Route(path = ARouterConfig.activity.APP_ACTIVITY_INTERCEPTOR)
public class TestInterceptorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);

        String extra = getIntent().getStringExtra("extra");
        if (!TextUtils.isEmpty(extra)) {
            ((TextView)findViewById(R.id.tv_des)).setText(extra);
        }
    }
}
