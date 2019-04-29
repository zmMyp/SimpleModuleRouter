package zm.myp.smr.source.scheduler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by qianjian on 2019/4/29.
 */

public class MainThreadScheduler extends Schedulers {

    private Handler handler;

    public MainThreadScheduler() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void dispatch(Runnable runnable) {

        handler.post(runnable);
    }
}
