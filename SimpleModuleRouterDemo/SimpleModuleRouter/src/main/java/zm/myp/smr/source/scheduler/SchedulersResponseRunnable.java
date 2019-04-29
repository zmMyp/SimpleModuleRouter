package zm.myp.smr.source.scheduler;

import zm.myp.smr.source.base.SmrResponseCallBack;

/**
 * Created by qianjian on 2019/4/29.
 */

public class SchedulersResponseRunnable implements Runnable {

    private SmrResponseCallBack smrResponseCallBack;
    private Object object;

    public SchedulersResponseRunnable(SmrResponseCallBack smrResponseCallBack, Object object) {

        this.smrResponseCallBack = smrResponseCallBack;
        this.object = object;
    }

    @Override
    public void run() {
        try {
            smrResponseCallBack.response(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
