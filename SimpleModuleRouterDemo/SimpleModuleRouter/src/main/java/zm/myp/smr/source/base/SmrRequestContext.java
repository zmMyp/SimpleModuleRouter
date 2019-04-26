package zm.myp.smr.source.base;



import android.content.Context;

import java.io.Serializable;
import java.util.Map;

import zm.myp.smr.source.SmrParamstBody;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrRequestContext implements Serializable {

    private transient Object requester;
    private String url;
    private SmrParamstBody params;

    public SmrRequestContext(Object requester, String url,SmrParamstBody params) {
        this.requester = requester;
        this.url = url;
        this.params = params;
    }

    public Object getRequester() {
        return requester;
    }

    public void setRequester(Object requester) {
        this.requester = requester;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SmrParamstBody getParams() {
        return params;
    }

    public void setParams(SmrParamstBody params) {
        this.params = params;
    }


    /**
     * 回调
     * @param data
     * @throws Exception
     */
    public void responseCall(Object data)  {


        try {
            SmrResponseCallBack smrResponseCallBack= SmrRouterCache.getCallBack(url);
            if (smrResponseCallBack != null) {
                smrResponseCallBack.response(data);
            }
            SmrRouterCache.removeCallBack(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
