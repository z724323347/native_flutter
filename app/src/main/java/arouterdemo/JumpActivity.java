package arouterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.azhon.app.R;

import java.util.List;
import java.util.Map;

@Route(path = ARouterConfig.activity.ROUTER_JUMP_ACTIVITY)
public class JumpActivity extends AppCompatActivity {

    @Autowired(desc = "姓名")
    String name = "jack";

    @Autowired
    TestSerializable ser;

    @Autowired
    TestObj obj;

    @Autowired
    List<TestObj> objList;

    @Autowired
    Map<String, List<TestObj>> map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);

        ARouter.getInstance().inject(this);

//        String params = String.format(
//                "name=%s,\n obj=%s , \n objList=%s, \n map=%s",
//                name,
//                obj,
//                objList,
//                map
//        );
        String params = String.format( "name=%s, \n ser=%s", name,ser.name);
        ((TextView)findViewById(R.id.tv_des)).setText(params);
    }
}
