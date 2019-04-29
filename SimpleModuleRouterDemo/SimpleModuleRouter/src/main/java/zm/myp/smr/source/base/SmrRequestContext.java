package zm.myp.smr.source.base;


import android.content.Context;

import java.io.Serializable;
import java.util.Map;

import zm.myp.smr.source.SmrApplication;
import zm.myp.smr.source.SmrParamstBody;
import zm.myp.smr.source.scheduler.SchedulerType;
import zm.myp.smr.source.scheduler.Schedulers;
import zm.myp.smr.source.scheduler.SchedulersResponseRunnable;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrRequestContext implements Serializable {

    private transient Object requester;
    private String url;
    private SmrParamstBody params;


    private SchedulerType requestCallOnType; // 请求功能执行的线程


    private SchedulerType responseCallOnType; // 回调功能执行的线程

    public SmrRequestContext() {

    }

    public SmrRequestContext(Object requester, String url, SmrParamstBody params) {
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

    public SchedulerType getRequestCallOnType() {
        return requestCallOnType;
    }

    public void setRequestCallOnType(SchedulerType requestCallOnType) {
        this.requestCallOnType = requestCallOnType;
    }

    public SchedulerType getResponseCallOnType() {
        return responseCallOnType;
    }

    public void setResponseCallOnType(SchedulerType respinseCallOnType) {
        this.responseCallOnType = respinseCallOnType;
    }


    public final SmrRequestContext requestOnThread(SchedulerType schedulerType) {

        this.setRequestCallOnType(schedulerType);

        return this;
    }

    public final SmrRequestContext responseOnThread(SchedulerType schedulerType) {

        this.setResponseCallOnType(schedulerType);

        return this;
    }

    public final SmrRequestContext call(Object requester, String url) {

        return call(requester, url, null, null);
    }

    public final SmrRequestContext call(Object requester, String url, SmrParamstBody params) {

        return call(requester, url, params, null);
    }

    public final SmrRequestContext call(Object requester, String url, SmrResponseCallBack smrResponse) {

        return call(requester, url, null, smrResponse);
    }

    public final SmrRequestContext call(Object requester, String url, SmrParamstBody params, SmrResponseCallBack smrResponse) {

        //SmrRequestContext smrRequestContext = new SmrRequestContext(requester, url, params);

        this.setRequester(requester);
        this.setUrl(url);
        this.setParams(params);

        if (smrResponse != null) {
            SmrRouterCache.saveCallBack(url, smrResponse);
        }
        try {
            SmrApplication.getGlobalSmrApp().callFunction(this);
        } catch (Exception e) {

        }

        return this;
    }

    /**
     * 回调
     *
     * @param data
     * @throws Exception
     */
    public void responseCall(Object data) {

        try {
            SmrResponseCallBack smrResponseCallBack = SmrRouterCache.getCallBack(url);

            if (smrResponseCallBack != null) {
                SchedulersResponseRunnable schedulersResponseRunnable = new SchedulersResponseRunnable(smrResponseCallBack, data);

                if (this.getResponseCallOnType() == null) {
                    Schedulers.getMainThread().dispatch(schedulersResponseRunnable);
                    return;
                }

                if (this.getResponseCallOnType() == SchedulerType.THREAD) {
                    Schedulers.getThread().dispatch(schedulersResponseRunnable);
                } else {
                    Schedulers.getMainThread().dispatch(schedulersResponseRunnable);
                }

            }
            SmrRouterCache.removeCallBack(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
