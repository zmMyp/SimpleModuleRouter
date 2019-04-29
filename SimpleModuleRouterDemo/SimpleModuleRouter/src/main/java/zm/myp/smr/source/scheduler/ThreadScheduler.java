package zm.myp.smr.source.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qianjian on 2019/4/29.
 */

public class ThreadScheduler extends Schedulers {

    private ExecutorService service;


    public ThreadScheduler() {
        service = Executors.newCachedThreadPool();
    }


    @Override
    public void dispatch(Runnable runnable) {
        service.execute(runnable);
    }
}
