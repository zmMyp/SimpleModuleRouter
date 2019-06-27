package zm.myp.smr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zm.myp.smr.source.SmrParamsBody;
import zm.myp.smr.source.SmrRequestClient;
import zm.myp.smr.source.base.SmrResponseCallBack;
import zm.myp.smr.source.scheduler.SchedulerType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);
        tv1 = (TextView) findViewById(R.id.tv);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == button) {

            //请求地址
            String url = "kb51://module1/fun1";

            // 拼接参数
            SmrParamsBody smrParamsBody = new SmrParamsBody()
                    .set("p1", "我是主壳传来的数据")
                    .set("p2", "ssss");

            // 发起请求module1的功能，带有线程切换
            SmrRequestClient.build().
                    requestOnThread(SchedulerType.MAIN_THREAD).
                    responseOnThread(SchedulerType.MAIN_THREAD).
                    call(this, url, smrParamsBody, new SmrResponseCallBack() {
                        @Override
                        public void response(Object data)  {
                            Log.v("smr1", "responsethread" + Thread.currentThread().getName());
                            tv1.setText("当前处于主壳页面,module1返回的数据是:" + ((SmrParamsBody) data).get("m1"));
                        }
                    });

        }


    }


}
