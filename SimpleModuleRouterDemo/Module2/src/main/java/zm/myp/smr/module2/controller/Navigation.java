package zm.myp.smr.module2.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import zm.myp.smr.source.base.SmrRequestContext;

/**
 * Created by qianjian on 2019/4/25.
 */

public class Navigation {

    public  static  void navigateToMain(SmrRequestContext smrRequestContext){

        Activity activity=(Activity) smrRequestContext.getRequester();
        Intent intent=new Intent(activity, M2MainActivity.class);
        activity.startActivity(intent);

    }
}
