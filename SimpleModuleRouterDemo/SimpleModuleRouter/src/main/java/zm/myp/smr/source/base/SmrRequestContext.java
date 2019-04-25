package zm.myp.smr.source.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrRequestContext implements Serializable {

    private Object requester;
    private String url;
    private Map<String,Object> params;

    public SmrRequestContext(Object requester, String url, Map<String, Object> params) {
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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


    /**
     * 回调
     * @param data
     * @throws Exception
     */
    public void responseCall(Object data) throws Exception {


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
