package zm.myp.smr.source;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import zm.myp.smr.source.base.SmrHandler;
import zm.myp.smr.source.base.SmrRequestContext;
import zm.myp.smr.source.base.SmrRouterCache;
import zm.myp.smr.source.scheduler.SchedulerType;
import zm.myp.smr.source.scheduler.Schedulers;
import zm.myp.smr.source.scheduler.SchedulersRequestRunnable;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrApplication {

    private SmrRouterCache<SmrHandler> smrHandlerSmrRouterCache;
    public static Application globalApplication;
    private static SmrApplication globalSmrApp;

    public List<SmrModule> smrModuleList = new ArrayList<>();

    private SmrApplication() {

        smrHandlerSmrRouterCache = new SmrRouterCache<SmrHandler>();

    }

    public static SmrApplication getGlobalSmrApp() {

        if (globalSmrApp == null) {
            globalSmrApp = new SmrApplication();
        }
        return globalSmrApp;
    }


    public static void loadModules(Application application) {
        globalApplication = application;
        try {
            //apt编译自动生成的类
            List<String> modules = (List<String>) Class.forName("zm.myp.smr.source.SmrApplication_Helper").getDeclaredMethod("loadModules").invoke(null);
            for (String modulepath : modules) {
                SmrModule smrModule = (SmrModule) Class.forName(modulepath).newInstance();
                smrModule.register(getGlobalSmrApp());
            }
        } catch (Exception e) {

        }
    }

    //添加监听
    public void register(String path, SmrHandler smrHandler) {
        smrHandlerSmrRouterCache.add(path, smrHandler);

    }


    //找出注册保存的功能，调用
    public boolean callFunction(SmrRequestContext smrRequestContext) throws Exception {
        SmrHandler handler = smrHandlerSmrRouterCache.matched(smrRequestContext);
        SchedulersRequestRunnable schedulersRunnable = new SchedulersRequestRunnable(handler, smrRequestContext);

        if (handler != null) {
            handler.handle(smrRequestContext);

            if (smrRequestContext.getRequestCallOnType() == null) {
                Schedulers.getMainThread().dispatch(schedulersRunnable);
                return true;
            }

            if (smrRequestContext.getRequestCallOnType() == SchedulerType.THREAD) {
                Schedulers.getThread().dispatch(schedulersRunnable);
            } else {
                Schedulers.getMainThread().dispatch(schedulersRunnable);
            }

            return true;

        } else {
            return false;
        }
    }
}
