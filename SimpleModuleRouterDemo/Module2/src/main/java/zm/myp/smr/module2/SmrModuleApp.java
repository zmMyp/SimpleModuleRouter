package zm.myp.smr.module2;

import zm.myp.smr.module2.controller.Navigation;
import zm.myp.smr.source.SmrApplication;
import zm.myp.smr.source.SmrModule;
import zm.myp.smr.source.base.SmrHandler;
import zm.myp.smr.source.base.SmrRequestContext;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrModuleApp implements SmrModule {


    @Override
    public void register(SmrApplication app) {

        app.register("kb51://module2/fun1", new SmrHandler() {
            @Override
            public void handle(SmrRequestContext smrRequestContext) throws Exception {
                Navigation.navigateToMain(smrRequestContext);

            }
        });

    }
}
