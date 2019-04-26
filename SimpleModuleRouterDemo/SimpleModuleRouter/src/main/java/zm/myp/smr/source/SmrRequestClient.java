package zm.myp.smr.source;

import android.util.Log;

import java.util.Map;

import zm.myp.smr.source.base.SmrRequestContext;
import zm.myp.smr.source.base.SmrResponseCallBack;
import zm.myp.smr.source.base.SmrRouterCache;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrRequestClient {


    public final static void call(Object requester, String url) {

        call(requester,url,null,null);
    }

    public final static void call(Object requester, String url,SmrParamstBody params) {

        call(requester,url,params,null);
    }
    public final static void call(Object requester, String url, SmrResponseCallBack smrResponse) {

        call(requester,url,null,smrResponse);
    }

    public final static void call(Object requester, String url, SmrParamstBody params, SmrResponseCallBack smrResponse) {

        SmrRequestContext smrRequestContext = new SmrRequestContext(requester, url,params);
        if(smrResponse!=null){
            SmrRouterCache.saveCallBack(url,smrResponse);
        }
        try {
            SmrApplication.getGlobalSmrApp().callFunction(smrRequestContext);
        } catch (Exception e) {
            Log.v("smr1","error->"+e.getMessage());
        }
    }
}
