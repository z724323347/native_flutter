package com.app.arouter.test;

public class ARouterConfig {

    /**
     * activity的arouter path
     */
    public static final class activity {
        public static final String ROUTER_HOME_ACTIVITY = "/app/mainActivity";
        public static final String ROUTER_JUMP_ACTIVITY = "/app/jumpActivity";
        public static final String APP_ACTIVITY_START = "/app/startActivity";
        public static final String APP_ACTIVITY_FLUTTER = "/app/toFlutterActivity";
    }

    /**
     * fragment path
     */
    public static final class fragment {
        //app
        public static final String app_test= "/app/fragment/testFragment";  //测试
    }

    /**
     * module path
     */
    public static final class module {
        //module
        public static final String app_test= "/module/activity/testActivity";  //测试
    }


}
