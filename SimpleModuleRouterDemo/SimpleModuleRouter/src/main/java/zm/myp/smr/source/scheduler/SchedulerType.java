package zm.myp.smr.source.scheduler;

import java.io.Serializable;

/**
 * Created by qianjian on 2019/4/29.
 */

public enum SchedulerType implements Serializable{
    MAIN_THREAD,// 主线程
    THREAD  // 子线程
}
