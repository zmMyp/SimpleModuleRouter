package zm.myp.smr.source.scheduler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by qianjian on 2019/4/29.
 */

public abstract  class Schedulers {

    static Schedulers MAIN_THREAD;
    static Schedulers THREAD;
    static {
        MAIN_THREAD = new MainThreadScheduler();
        THREAD = new ThreadScheduler();
    }

    public static  Schedulers getMainThread(){
        return  MAIN_THREAD;
    }

    public static  Schedulers getThread(){
        return THREAD;
    }

    public abstract void dispatch(Runnable runnable);

}
