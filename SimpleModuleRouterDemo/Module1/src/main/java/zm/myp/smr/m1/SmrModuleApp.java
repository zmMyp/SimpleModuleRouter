package zm.myp.smr.m1;

import zm.myp.smr.source.SmrModule;
import zm.myp.smr.source.SmrApplication;

import zm.myp.smr.source.base.SmrRequestContext;
import zm.myp.smr.source.base.SmrHandler;


/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrModuleApp implements SmrModule {


    @Override
    public void register(SmrApplication app) {

        app.register("kb51://module1/fun1", new SmrHandler() {
            @Override
            public void handle(SmrRequestContext smrRequestContext) throws Exception {

                //Log.v("smr1",smrRequestContext.getParams().get("p1").toString());

                //返回数据给调用方
                //smrRequestContext.responseCall(new SmrParamstBody().set("md1","模块1发回的数据"));

                Navigation.navigateToMain(smrRequestContext);

            }
        });

    }

}
