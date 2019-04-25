package zm.myp.smr.source;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Properties;

import zm.myp.smr.source.base.SmrHandler;
import zm.myp.smr.source.base.SmrRequestContext;
import zm.myp.smr.source.base.SmrRouterCache;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrApplication {

    private SmrRouterCache<SmrHandler>  smrHandlerSmrRouterCache;
    public   static Application globalApplication;
    private static SmrApplication globalSmrApp;

    private SmrApplication(){

        smrHandlerSmrRouterCache=new SmrRouterCache<SmrHandler>();

    }

    public static  SmrApplication getGlobalSmrApp(){

        if(globalSmrApp==null){
            globalSmrApp=new SmrApplication();
        }
        return  globalSmrApp;
    }


    /*启动应用（全局只启动一个）*/
    public static  void loadModules(Application application) {
        globalApplication=application;
        try {
            final String dir = "smr";
            AssetManager am = application.getAssets();
            String[] list = am.list(dir);

            for (String uri : list) {

                //通过配置文件加载组件
                try {
                    Properties prop = new Properties();
                    prop.load(am.open(dir + "/" + uri));
                    String modulePackageName = prop.getProperty("smr.module");

                    if (TextUtils.isEmpty(modulePackageName) == false) {
                        SmrModule smrModule = (SmrModule)Class.forName(modulePackageName).newInstance();
                        smrModule.register(getGlobalSmrApp());
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //添加监听
    public void register(String path, SmrHandler smrHandler) {
        smrHandlerSmrRouterCache.add(path, smrHandler);

    }


    //找出注册保存的功能，调用
    public boolean callFunction(SmrRequestContext smrRequestContext) throws Exception {
        SmrHandler handler = smrHandlerSmrRouterCache.matched(smrRequestContext);

        if (handler != null) {
            handler.handle(smrRequestContext);
            return true;

        } else {
            return false;
        }
    }
}
