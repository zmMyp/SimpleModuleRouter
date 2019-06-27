package zm.myp.smr.m1.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zm.myp.smr.m1.R;
import zm.myp.smr.source.SmrParamsBody;
import zm.myp.smr.source.SmrRequestClient;
import zm.myp.smr.source.base.SmrRequestContext;

public class M1MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvm1;
    Button btnm1,btnm2;
    SmrRequestContext smrRequestContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1_activity_main);

        tvm1=(TextView) findViewById(R.id.tvm1);
        btnm1=(Button) findViewById(R.id.btnm1);
        btnm2=(Button) findViewById(R.id.btnm2);

        btnm1.setOnClickListener(this);
        btnm2.setOnClickListener(this);

        getData();
    }

    private void getData(){

        smrRequestContext=(SmrRequestContext)getIntent().getSerializableExtra("smrRequestContext");

        tvm1.setText("当前处于Module1的页面，主壳传来的数据是:"+"\r\n"+smrRequestContext.getParams().get("p1").toString());

    }

    @Override
    public void onClick(View v) {

        if(v==btnm1){

            //返回数据给主壳
            smrRequestContext.responseCall(new SmrParamsBody().set("m1","我是module1返回的数据"));
            finish();
        }

        if(v==btnm2){

            //不带回调
            SmrRequestClient.build().call(this, "kb51://module2/fun1",
                    new SmrParamsBody().set("p1", "我是Module1传来的数据"));
        }
    }
}
