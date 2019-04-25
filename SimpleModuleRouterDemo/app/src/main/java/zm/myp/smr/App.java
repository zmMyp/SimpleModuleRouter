package zm.myp.smr;

import android.app.Application;

import zm.myp.smr.source.SmrApplication;

/**
 * Created by qianjian on 2019/4/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //启动加载组件
        SmrApplication.loadModules(this);
    }
}
