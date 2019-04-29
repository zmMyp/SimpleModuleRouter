package zm.myp.smr.source.scheduler;

import zm.myp.smr.source.base.SmrHandler;
import zm.myp.smr.source.base.SmrRequestContext;

/**
 * Created by qianjian on 2019/4/29.
 */

public class SchedulersRequestRunnable implements Runnable {

    private  SmrHandler smrHandler;
    private SmrRequestContext context;
    public SchedulersRequestRunnable(SmrHandler smrHandler, SmrRequestContext context){
        this.smrHandler=smrHandler;
        this.context=context;

    }
    @Override
    public void run() {

        try {
            smrHandler.handle(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
