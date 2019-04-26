package zm.myp.smr.m1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import zm.myp.smr.m1.controller.M1MainActivity;
import zm.myp.smr.source.base.SmrRequestContext;

/**
 * Created by qianjian on 2019/4/25.
 */

public class Navigation {

    public  static  void navigateToMain(SmrRequestContext smrRequestContext){

        Activity activity=(Activity) smrRequestContext.getRequester();
        Intent intent=new Intent(activity, M1MainActivity.class);
        intent.putExtra("smrRequestContext",smrRequestContext);
        activity.startActivity(intent);

    }
}
