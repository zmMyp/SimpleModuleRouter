package zm.myp.smr.source;

import android.util.Log;

import java.util.Map;

import zm.myp.smr.source.base.SmrRequestContext;
import zm.myp.smr.source.base.SmrResponseCallBack;
import zm.myp.smr.source.base.SmrRouterCache;
import zm.myp.smr.source.scheduler.SchedulerType;
import zm.myp.smr.source.scheduler.Schedulers;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrRequestClient {


    public static SmrRequestContext build() {
        return new SmrRequestContext();
    }

}
